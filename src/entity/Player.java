package entity;

import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import objects.OBJ_Door;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,32,32);

        solidAreaDefaultX = solidArea.x;  //เก็บค่าเริ่มต้นของตำแหน่งพื้นที่ชน เพื่อใช้ในภายหลังหากจำเป็น
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize *23; //player position
        worldY = gp.tileSize *21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
    }
    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        up1 = setUp("/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2 = setUp("/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1 = setUp("/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2  = setUp("/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1 = setUp("/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2 = setUp("/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1 = setUp("/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2 = setUp("/player/boy_right_2",gp.tileSize,gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setUp("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
        attackUp2 = setUp("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
        attackDown1 = setUp("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
        attackDown2 = setUp("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
        attackLeft1 = setUp("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setUp("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setUp("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setUp("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
    }

    public void update() {

        if (attacking == true){
            attacking();
        }



            // Check if any key is pressed
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }



                // check tile collision
                collisionOn = false;
                gp.collisionChecker.checkTile(this);

                //CHECK OBJECT COLLISION
                int objIndex = gp.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);

                //CHECK NPC COLLISION
                int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //CHECK MONSTER COLLISION
                int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
                contactMonster(monsterIndex);

                //CHECK EVENT
                gp.eventHandler.checkEvent();

                if(collisionOn == false && keyH.enterPressed == false) {
                    switch(direction) {
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }

                if(keyH.enterPressed == true && attackCanceled == false){
                    gp.playMusic(7);
                    attacking = true;
//                    spriteCounter = 0;
                }
                attackCanceled = false;

                if(attacking == false){
                    spriteCounter++;
                    if (spriteCounter > 10) {
                        spriteNum = (spriteNum == 1) ? 2 : 1;
                        spriteCounter = 0;
                    }
                }
            }else{
//                standCounter++;
//                if (standCounter == 20) {
//                    spriteNum = 1;
//                    standCounter = 0;
//                }
            }


            // if collision is false, player can move


            gp.keyH.enterPressed = false;


//            pixelCounter += speed;
//            if(pixelCounter == 48){
//                moving = false;
//                pixelCounter = 0;
//            }
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking(){

        spriteCounter++;
        if(spriteCounter <=5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            //Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/F for the attackArea
            switch (direction){
                case "up": worldY -= attackArea.height;break;
                case "down" : worldY += attackArea.height;break;
                case "left" : worldX -= attackArea.width;break;
                case "right" : worldX += attackArea.width;break;
            }
            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //Check monster collision with the updated worldX,worldY and solidArea
            int monsterIndex = gp.collisionChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex);


            //After checking collision,restore the original data;
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
            
        }
        if(spriteCounter >=25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public  void pickUpObject(int index){
        if(index != 999) {

        }
    }

    public void contactMonster(int index){
        if(index != 999){

            if(invincible == false){
                gp.playSoundEffect(6);
                    life -= 1;
                invincible = true;
            }

        }
    }

    public void damageMonster(int index){
        if(index != 999){
            if(gp.monster[index].invincible == false){

                gp.playSoundEffect(5);
                gp.monster[index].life -= new OBJ_Sword_Normal(gp).attackValue;
                gp.monster[index].invincible = true;
                gp.monster[index].damageReaction();

                if(gp.monster[index].life <=0){
                    gp.monster[index].dying = true;
                }
            }
        }

    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
                gp.playSoundEffect(7);
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;


        switch (direction) {
            case "up":
                if(attacking == false){image = (spriteNum == 1) ? up1 : up2;}
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 2) ? attackUp1 : attackUp2;
                }
                break;
            case "down":
                if(attacking == false){image = (spriteNum == 1) ? down1 : down2;}
                if(attacking == true){image = (spriteNum == 2) ? attackDown1 : attackDown2;}
                break;
            case "left":
                if(attacking == false){image = (spriteNum == 1) ? left1 : left2;}
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 2) ? attackLeft1 : attackLeft2;
                }
                break;
            case "right":
                if(attacking == false){image = (spriteNum == 1) ? right1 : right2;}
                if(attacking == true){image = (spriteNum == 2) ? attackRight1 : attackRight2;}
                break;
        }
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); //ทำให้ Graphic จากลงเหลือ 30%
        }
        if (image != null) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);
        } else {
            System.err.println("Image is null for direction: " + direction);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));


//        g2.setFont(new Font("Arial",Font.PLAIN,26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter,10,400);
    }
}

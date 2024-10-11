package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;

    public int speed;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public BufferedImage attackUp1, attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
    public BufferedImage image,image2,image3;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public boolean collision = false;
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[] = new String[20];

    //STATE
    public int worldX,worldY;
    public String direction = "down";
    public boolean collisionOn = false;
    public int spriteNum = 1;
    public boolean invincible = false; //ทำให้เป็นอมตะชั่วคราว;
    int dialogueIndex = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;


    //COUNTER
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;


    //CHARACTER ATTRIBUTES
    public String name;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int level;
    public int ammo;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;

    //TYPE
    public int type; // 0 = player, 1 = npc ,2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;

    public Entity(GamePanel gp){
        this.gp =gp;
//        solidAreaDefaultX = solidArea.x;  //เก็บค่าเริ่มต้นของตำแหน่งพื้นที่ชน เพื่อใช้ในภายหลังหากจำเป็น
//        solidAreaDefaultY = solidArea.y;
    }

    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
//        solidArea.x = solidAreaDefaultX;
//        solidArea.y = solidAreaDefaultY;
    }
    public void setAction(){}
    public void damageReaction(){

    }
    public void use(Entity entitry){

    };
    public void update(){
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
        gp.collisionChecker.checkEntity(this,gp.npc);
        gp.collisionChecker.checkEntity(this,gp.monster);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
        }

        // if collision is false, player can move
        if(collisionOn == false) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack){
        if(gp.player.invincible == false){
            gp.playSoundEffect(6);
            int damage = attack - gp.player.defense;
            if(damage <= 0){
                damage = 0;
            }
            life -= damage;
            gp.player.life -=damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX  - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.worldY) {

            BufferedImage image = null;
            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }

            //Monster Healbar
            if(type == type_monster && hpBarOn == true){

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)hpBarValue,10);

                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }



                if(invincible == true){
                    hpBarOn = true;
                    hpBarCounter = 0;
                   changeAlpha(g2,0.4f);
                }
                if(dying == true){
                    dyingAnimation(g2);
                }
                g2.drawImage(image, screenX, screenY, null);
                changeAlpha(g2,1f);
                g2.setColor(Color.red);
                g2.drawRect(screenX + solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);

        }
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;

        int i = 5;
        if(dyingCounter <= 5){changeAlpha(g2,0f);}
        if(dyingCounter > i * 1 && dyingCounter <=i *2){changeAlpha(g2,1f);}
        if(dyingCounter > i*2 && dyingCounter <=i*3){changeAlpha(g2,0f);}
        if(dyingCounter > i*3 && dyingCounter <=i*4){changeAlpha(g2,1f);}
        if(dyingCounter > i*4 && dyingCounter <=i*5){changeAlpha(g2,0f);}
        if(dyingCounter > i*5 && dyingCounter <=i*6){changeAlpha(g2,1f);}
        if(dyingCounter > i*6 && dyingCounter <=i*7){changeAlpha(g2,0f);}
        if(dyingCounter > i*7 && dyingCounter <=i*8){changeAlpha(g2,1f);}
        if(dyingCounter > i*8){
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2,float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }

    public BufferedImage setUp(String imagePath,int width,int height) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            System.err.println("Image not found. Check resource path!");
        }
        return image;
    }

}

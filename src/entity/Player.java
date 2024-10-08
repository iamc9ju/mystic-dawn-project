package entity;

import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(1, 1, 46, 46);

        solidAreaDefaultX = solidArea.x;  //เก็บค่าเริ่มต้นของตำแหน่งพื้นที่ชน เพื่อใช้ในภายหลังหากจำเป็น
        solidAreaDefaultY = solidArea.y;
        //เปลี่ยนระยะการโจมตีได้ตรงนี้
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; //player position
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1 = setUp("/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2 = setUp("/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1 = setUp("/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2 = setUp("/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1 = setUp("/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2 = setUp("/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1 = setUp("/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2 = setUp("/player/boy_right_2",gp.tileSize,gp.tileSize);
    }
    public void getPlayerAttackImage() { //16x32
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

        if(attacking == true) {
            attacking();
        }
        // Check if any key is pressed
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
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

            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            gp.keyH.enterPressed = false;
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }

        // if collision is false, player can move

//            pixelCounter += speed;
//            if(pixelCounter == 48){
//                moving = false;
//                pixelCounter = 0;
//            }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1; //รูป attacking1
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2; //อีก20frame attack2

            //save the current worldX,worldY,solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's WorldX/Y for the attackArea
            switch(direction) {
                case "up":worldY -= attackArea.height; break;
                case "down":worldY += attackArea.height; break;
                case "left":worldX -= attackArea.width; break;
                case "right":worldX += attackArea.width; break;
            }
            //attack area becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex =gp.collisionChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex);

            //After checking collision, resorted the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int index) {
        if (index != 999) {

        }
    }
    public void damageMonster(int i) {
        if (i != 999) {
            if(gp.monster[i].invincible == false) {
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }
    public void contactMonster(int index) {
        if (index != 999) {
            if (invincible == false) {
                gp.playSoundEffect(6); //receive damage
                life -= 1;
                invincible = true;
            }
        }
    }

    public void interactNPC(int i) {
        if(gp.keyH.enterPressed == true) {
            if (i != 999) {
                gp.playSoundEffect(5); //hitmonster
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
            }
            else {
                    gp.playSoundEffect(7); //swing sword
                    attacking = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up":
                if(attacking == false) {
                    image = (spriteNum == 1) ? up1 : up2;
                }
                if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 1) ? attackUp1 : attackUp2;
                }
                break;
            case "down":
                if(attacking == false) {
                    image = (spriteNum == 1) ? down1 : down2;
                }
                if(attacking == true) {
                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                }
                break;
            case "left":
                if(attacking == false) {
                    image = (spriteNum == 1) ? left1 : left2;
                }
                if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                }
                break;
            case "right":
                if(attacking == false) {
                    image = (spriteNum == 1) ? right1 : right2;
                }
                if(attacking == true) {
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); //ทำให้ Graphic จากลงเหลือ 30%
        }
        if (image != null) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);
        } else {
            System.err.println("Image is null for direction: " + direction);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));


//        g2.setFont(new Font("Arial",Font.PLAIN,26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter,10,400);
    }
}

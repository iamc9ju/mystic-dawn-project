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
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(1,1,46,46);

        solidAreaDefaultX = solidArea.x;  //เก็บค่าเริ่มต้นของตำแหน่งพื้นที่ชน เพื่อใช้ในภายหลังหากจำเป็น
        solidAreaDefaultY = solidArea.y;


        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize *23; //player position
        worldY = gp.tileSize *21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1 = setUp("/player/john_up_1");
        up2 = setUp("/player/john_up_2");
        down1 = setUp("/player/john_down_1");
        down2  = setUp("/player/john_down_2");
        left1 = setUp("/player/john_left_1");
        left2 = setUp("/player/john_left_2");
        right1 = setUp("/player/john_right_1");
        right2 = setUp("/player/john_right_2");
    }

    public void update() {

        if(moving == false){
            // Check if any key is pressed
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }

                moving = true;

                // check tile collision
                collisionOn = false;
                gp.collisionChecker.checkTile(this);

                //CHECK OBJECT COLLISION
                int objIndex = gp.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);

                //CHECK NPC COLLISION
                int npcIndex = gp.collisionChecker.checkEntity(this,gp.npc);
                interactNPC(npcIndex);

                //CHECK EVENT
                gp.eventHandler.checkEvent();

                gp.keyH.enterPressed = false;
            }else{
                standCounter++;
                if(standCounter == 20){
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
        if(moving == true){
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
            pixelCounter += speed;
            if(pixelCounter == 48){
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public  void pickUpObject(int index){
        if(index != 999) {

        }
    }

    public void interactNPC(int i){
        if(i!= 999){
            if(keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void draw(Graphics2D g2) {
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
        if (image != null) {
            g2.drawImage(image, screenX, screenY, null);
            g2.setColor(Color.red);
            //g2.drawRect(screenX + solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);

        } else {
            System.err.println("Image is null for direction: " + direction);
        }
    }
}

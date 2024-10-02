package entity;

import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,32,32);

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
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/john_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/john_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/john_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/john_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/john_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/john_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/john_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/john_right_2.png"));
        } catch (IOException e) {
            System.err.println("Error loading player images: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException npe) {
            System.err.println("Image not found. Check resource path!");
        }
    }

    public void update() {
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

            // check tile collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this,true);
            pickUpObject(objIndex);

            // if collision is false, player can move
            if(!collisionOn) {
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
        }
    }

    public  void pickUpObject(int index){
        if(index != 999){
            String objectName = gp.obj[index].name;
            switch (objectName){
                case "Key":
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key !");
                    gp.playSoundEffect(1);
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSoundEffect(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("The door is opened !");
                    }else {
                        gp.ui.showMessage("You need a key !");
                    }

                    break;
                case "Boots":
                    gp.playSoundEffect(2);
                    speed +=2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("Speed up !");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSoundEffect(4);
                    break;
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
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            System.err.println("Image is null for direction: " + direction);
        }
    }
}

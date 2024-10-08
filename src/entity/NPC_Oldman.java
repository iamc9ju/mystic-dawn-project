package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_Oldman extends  Entity{

    public  NPC_Oldman(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;

        getNpcImage();
        setDialogue();
    }

    public void getNpcImage() {
        up1 = setUp("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setUp("/npc/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setUp("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2  = setUp("/npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setUp("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setUp("/npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setUp("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setUp("/npc/oldman_right_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "You come to this island to find \n the treasure?";
        dialogues[2] = "I used to be a great wizard \n but now...I'm a bit too old \n for takingnan adventure.";
        dialogues[3] = "Well, good luck on you.";
    }

    public void setAction(){

        actionLockCounter++;
        if (actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            }
            if(i >= 25 && i <= 50){
                direction = "down";
            }
            if(i>=50 && i <= 75){
                direction = "left";
            }
            if(i>=75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak(){
        super.speak();
    }


}

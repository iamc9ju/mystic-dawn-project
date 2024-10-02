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
    }

    public void getNpcImage() {
        up1 = setUp("/npc/oldman_up_1");
        up2 = setUp("/npc/oldman_up_2");
        down1 = setUp("/npc/oldman_down_1");
        down2  = setUp("/npc/oldman_down_2");
        left1 = setUp("/npc/oldman_left_1");
        left2 = setUp("/npc/oldman_left_2");
        right1 = setUp("/npc/oldman_right_1");
        right2 = setUp("/npc/oldman_right_2");
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


}

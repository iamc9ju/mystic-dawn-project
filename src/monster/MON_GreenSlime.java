package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 5;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage(){
        up1 = setUp("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        up2 = setUp("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        down1 = setUp("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        down2 = setUp("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        left1 = setUp("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        left2 = setUp("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        right1 = setUp("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        right2 = setUp("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
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
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}

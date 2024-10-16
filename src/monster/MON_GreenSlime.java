package monster;

import entity.Entity;
import main.GamePanel;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Rock;

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
//        projectile = new OBJ_Rock(gp);
//        collision = false;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        //
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
        //AI สุ่มเดิน
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

//        int i = new Random().nextInt(100) +1 ;
//        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30){
//            projectile.set(worldX,worldY,direction,true,this);
//            gp.projectileList.add(projectile);
//            shotAvailableCounter = 0;
//
//        }

    }
    public void damageReaction(){
        if(gp.player.direction == "down"){
            direction = "up";
        }
        if(gp.player.direction == "up"){
            direction = "down";
        }
        if(gp.player.direction == "left"){
            direction = "right";
        }
        if(gp.player.direction == "right"){
            direction = "left";
        }
    }

    public void checkDrop(){

        //CAST A DIE
        int i = new Random().nextInt(100)+1;


        //SET THE MONSTER DROP
        if(i< 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
    public void setRandomPosition() {
        int i = new Random().nextInt(4)+1;
        worldY = (37 + i) * gp.tileSize;
        i = new Random().nextInt(7)+1;
        worldX = (19 + i) * gp.tileSize;
    }
}

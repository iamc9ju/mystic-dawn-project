package monster;

import entity.Entity;
import main.GamePanel;
import objects.*;

import java.util.Random;

public class BOSS_KingSlime extends Entity {

    GamePanel gp;

    public BOSS_KingSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        boss = true;
        type = type_monster;
        name = "KingSlime";
        speed = 3;
        maxLife = 1;
        life = maxLife;
        attack = 50;
        defense = 10;
        exp = 100;
        sleep = true;
//        projectile = new OBJ_Rock(gp);
//        collision = false;

        int size = gp.tileSize*5;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = size;
        solidArea.height = size-30;
        //
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    int i=5;
    public void getImage(){
        up1 = setUp("/monster/boss_1",gp.tileSize*i,gp.tileSize*i);
       up2 = setUp("/monster/boss_2",gp.tileSize*i,gp.tileSize*i);
        down1 = setUp("/monster/boss_1",gp.tileSize*i,gp.tileSize*i);
       down2 = setUp("/monster/boss_2",gp.tileSize*i,gp.tileSize*i);
        left1 = setUp("/monster/boss_1",gp.tileSize*i,gp.tileSize*i);
       left2 = setUp("/monster/boss_2",gp.tileSize*i,gp.tileSize*i);
        right1 = setUp("/monster/boss_1",gp.tileSize*i,gp.tileSize*i);
        right2 = setUp("/monster/boss_2",gp.tileSize*i,gp.tileSize*i);
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

        if(i >= 0 && i < 100){
            dropItem(new OBJ_infinityGaunlet(gp));
        }
    }
//    public void setRandomPosition() {
//        int i = new Random().nextInt(4)+1;
//        worldY = (37 + i) * gp.tileSize;
//        i = new Random().nextInt(7)+1;
//        worldX = (19 + i) * gp.tileSize;
//    }
}

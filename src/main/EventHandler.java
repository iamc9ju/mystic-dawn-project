package main;

import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    //Map,col,row;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap,tempCol,tempRow;


    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
            //ใส่ตัวดักจับ Event ให้ทุกตำแหน่งใน Array 2D;
        }


    }

    public void checkEvent(){

        //Check if the player character is more than 1 tile away from the last event;
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance,yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            if(hit(0,27,16,"right")){damagePit(gp.dialogueState);}
            else if(hit(0,23,12,"up")){healingPool(gp.dialogueState);}
            else if(hit(0,22,45,"any")||hit(0,20,45,"any")||hit(0,21,45,"any")){
                teleport(1,37,36);
            }
            else if(hit(1,28,35,"any")||hit(1,27,35,"any")){
                teleport(2,23,25);
            }
            else if(hit(2,24,26,"any")||hit(2,23,26,"any")||hit(2,21,26,"any")||hit(2,24,26,"any")){
                teleport(1,28,36);
            }
            else if(hit(1,36,13,"any")){
                teleport(3,8,45);
            }
            else if(hit(1,36,37,"any")||hit(1,37,37,"any")){
                teleport(0,21,45);
            }
            else if(hit(3,27,21,"any")){
                teleport(4,32,38);
            }
            else if(hit(4,31,38,"any")){
                teleport(3,28,21);
            }
            else if(hit(3,7,45,"any")){
                teleport(1,35,13);
            }
            else if(hit(1,12,9,"up")){
                speak(gp.npc[1][0]);
            }
        }
    }

    public void speak(Entity entity){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }


    public boolean hit(int map,int col,int row,String reqDirection){
        boolean hit = false;
        //ลอจิคในการตรวจสอบการชน
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    //เช็คระยะห่างตอนเดินออกจากกับดัก
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState; //change to dialogue state draw dialogue and player life
        gp.playSoundEffect(6);
        gp.ui.currentDialogue = "You fall in to a pit!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.playSoundEffect(2);
            gp.player.attackCanceled = true;

            gp.ui.currentDialogue = "You drink water. \nYour life has been recovered.\n" + "(The progress has been saved)";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.assetSetter.setMonster();
        }
    }
    public void teleport(int map, int col, int row){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSoundEffect(13);
    }

}

package main;

import entity.NPC_Oldman;
import monster.MON_GreenSlime;
import objects.*;
import tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 27;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 31;
        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
    }

    public void setNPC(){
        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_Oldman(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 21;
        gp.npc[mapNum][0].worldY = gp.tileSize * 21;


    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize *21;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 38;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;

        mapNum = 1;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 7;
        i++;

    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i =0;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,12);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,28,12);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,29,12);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,12);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,31,12);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,32,12);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,33,12);i++;

        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,20);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,21);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,22);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,20,20);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,20,21);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,20,22);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,22,24);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,23,24);i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,24,24);i++;


    }
}

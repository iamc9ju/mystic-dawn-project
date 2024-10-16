package main;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;

import data.SaveLoad;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import monster.MON_GreenSlime;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN settings
    final int originalTileSize = 16; // 32 pixels
    final int scale = 3;

    public final int tileSize = originalTileSize*scale; //48x48 pixels
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 14;
    public final int screenWidth = maxScreenCol*tileSize; //960 piexels
    public final int screenHeight = maxScreenRow*tileSize;

    //WORLD PARAMETERS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    //FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;


    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    SaveLoad saveLoad = new SaveLoad(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile interactiveTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
//        playMusic(0);
//        stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        //logic คือวาดภาพหน้าจอเกมใน tempSeen เเล้วเอาภาพที่ได้ไปขยายเต็มจอ จะได้ไม่ต้องเปลี่ยนทีละองค์ประกอบ
        g2 = (Graphics2D)tempScreen.getGraphics();

    }

    public void resetGame(boolean restart){
        player.setDefaultPositions();
        player.restoreStatus();
        assetSetter.setNPC();
        assetSetter.setMonster();

        if(restart){
            player.setDefaultValues();
            player.setItems();
            assetSetter.setObject();
            assetSetter.setInteractiveTile();
        }
    }
//    public void restart(){
//        player.setDefaultPositions();
//        player.restoreLifeAndMana();
//        assetSetter.setNPC();
//        assetSetter.setMonster();
//
//    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run method
    }

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if(gameState == playState){
            player.update();

            for(int i=0;i<npc[1].length;i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        respawnMonster(i);
                    }
                }
            }
            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive == true){
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false){
                        projectileList.remove(i);
                    }

                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                    }

                }
            }
            for(int i = 0;i < interactiveTile[1].length; i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nothing
        }
    }

    public void drawToTempScreen(){
        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }else{
            //OTHER SCREEN
            //TILE
            tileM.draw(g2);//called draw inside tileManager

            //INTERACTIVE TILE
            for(int i = 0; i < interactiveTile[1].length;i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].draw(g2);
                }
            }

            //เพิ่ม player,npc,object ลง arrayList
            entityList.add(player);
            for(int i = 0;i< npc[1].length;i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }
            for(int i = 0 ; i< obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0 ; i< monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }
            for(int i = 0 ; i< projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0 ; i< particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);

                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i =0; i< entityList.size();i++){
                entityList.get(i).draw(g2);
            }
            //EMPTY ENTITY LIST
            entityList.clear();

        }
        //UI
        ui.draw(g2);
//        DEBUG
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX" + player.worldX, x, y); y+= lineHeight;
            g2.drawString("WorldY" + player.worldY,x,y); y+= lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize,x,y); y+= lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize,x,y ); y+= lineHeight;
            g2.drawString("Draw Time: " + passed,x,y);

        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }else{
        //OTHER SCREEN
            //TILE
            tileM.draw(g2);//called draw inside tileManager

            //INTERACTIVE TILE
            for(int i = 0; i < interactiveTile[1].length;i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].draw(g2);
                }
            }

            //เพิ่ม player,npc,object ลง arrayList
            entityList.add(player);
            for(int i = 0;i< npc[1].length;i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }
            for(int i = 0 ; i< obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0 ; i< monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }
            for(int i = 0 ; i< projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0 ; i< particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);

                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i =0; i< entityList.size();i++){
                entityList.get(i).draw(g2);
            }
            //EMPTY ENTITY LIST
            entityList.clear();

            }
            //UI
            ui.draw(g2);
//        DEBUG
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX" + player.worldX, x, y); y+= lineHeight;
            g2.drawString("WorldY" + player.worldY,x,y); y+= lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize,x,y); y+= lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize,x,y ); y+= lineHeight;
            g2.drawString("Draw Time: " + passed,x,y);

        }
        g2.dispose(); //save some memory

    }

    public void playMusic(int index){
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int index){
        soundEffect.setFile(index);
        soundEffect.play();
    }

    public void respawnMonster(int index) {
        // สร้างมอนสเตอร์ใหม่
        monster[currentMap][index] = new MON_GreenSlime(this);  // หรือประเภทมอนสเตอร์อื่นๆ ตามที่คุณต้องการ

        // กำหนดตำแหน่งใหม่ให้กับมอนสเตอร์
        monster[currentMap][index].setRandomPosition();

        // รีเซ็ตค่าต่างๆ ของมอนสเตอร์
        monster[currentMap][index].restoreStatus();
    }

}

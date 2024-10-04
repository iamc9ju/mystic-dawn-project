package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

import entity.Entity;
import entity.Player;
import objects.SuperObject;
import tile.TileManager;
public class GamePanel extends JPanel implements Runnable{
    //SCREEN settings
    final int originalTileSize = 16; // 32 pixels
    final int scale = 3;

    public final int tileSize = originalTileSize*scale; //48x48 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol*tileSize;
    public final int screenHeight = maxScreenRow*tileSize;

    //WORLD PARAMETERS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

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
//        playMusic(0);
//        stopMusic();
        gameState = titleState;
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run method
    }
    /*		public void run() {
                double drawInterval = 1000000000/FPS; //0.01666 second
                double nextDrawTime = System.nanoTime() +  drawInterval;
                while(gameThread != null) {

                    // 1 update information such as character position;
                    update();
                    // 2 DRAW draw the screen with the updated information
                    repaint();

                    try {
                        double remainingTime = nextDrawTime - System.nanoTime();
                        remainingTime = remainingTime/1000000;

                        if(remainingTime < 0) {
                            remainingTime = 0;
                        }
                        Thread.sleep((long) remainingTime);

                        nextDrawTime += drawInterval;
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            */
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

            for(int i=0;i<npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nothing
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
            tileM.draw(g2);//called draw inside tileManagher
            //OBJECT
            for (int i = 0; i< obj.length;i++){
                if(obj[i] != null){
                    obj[i].draw(g2,this);
                }
            }
            //NPC
            for(int i = 0 ; i < npc.length;i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }
            //PLAYER
            player.draw(g2);
            //UI
            ui.draw(g2);
        }

        //DEBUG
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd -drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed ,10,400);
            System.out.println("Draw Time : "+passed);
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
}

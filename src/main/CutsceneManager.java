//package main;
//
//import objects.OBJ_infinityGaunlet;
//
//import java.awt.*;
//
////public class CutsceneManager {
////    GamePanel gp;
////    Graphics2D g2;
////    public int sceneNum;
////    public int scenePhase;
////    int counter = 0;
////    float alpha = 0f;
////    int y;
////
////    public final int ending = 0;
////    public CutsceneManager(GamePanel gp) {
////        this.gp = gp;
////    }
////    public void draw(Graphics2D g2) {
////        this.g2 = g2;
////        switch(sceneNum) {
////            case ending: sceneEnding(); break;
////        }
////    }
////
////    public void sceneEnding() {
////        if(scenePhase == 0) {
////            gp.stopMusic();
////            gp.ui.npc = new OBJ_infinityGaunlet(gp);
////            scenePhase++;
////        }
////        if(scenePhase == 1) {
////            gp.ui.drawCharacterScreen();
////        }
////        if(scenePhase == 2) {
////            gp.playSoundEffect(4);
////            scenePhase++;
////        }
////        if(scenePhase == 3) {
////            if(counterReached(300) == true) {
////                scenePhase++;
////            }
////        }
////        if(scenePhase == 4) {
////            alpha+=0.005f;
////            if(alpha > 1f) {
////                alpha = 1f;
////            }
////            drawBlackBackground(alpha);
////            if(alpha == 1f) {
////                alpha = 0;
////                scenePhase++;
////            }
////        }
////        if(scenePhase == 5) {
////            drawBlackBackground(1f);
////
////            alpha+=0.005f;
////            if(alpha > 1f) {
////                alpha = 1f;
////            }
////        }
////    }
////
////    public boolean counterReached(int target) {
////        boolean counterReached = false;
////        counter++;
////        if(counter > target) {
////            counterReached = true;
////            counter= 0;
////        }
////        return counterReached;
////    }
////    public void drawBlackBackground(float alpha) {
////        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
////        g2.setColor(Color.BLACK);
////        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
////        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
////    }
////}

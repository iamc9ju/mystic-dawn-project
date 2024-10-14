package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    GamePanel gp;


    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,shotKeyPressed;
    //DEBUG
    boolean checkDrawTime;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode(); //return integer keyCode with the key in this event
        //TITLE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        //PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        //TRADE STATE
        else if (gp.gameState == gp.tradeState){
            tradeState(code);
        }
    }
    public void titleState(int code){
        if(gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState = 1;
                }
                if (gp.ui.commandNum == 1) {
                    //add later..
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    System.out.println("Do some fighter specific stuff!");
                    gp.gameState = gp.playState;
//                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 1) {
                    System.out.println("Do some Thife specific stuff!");
                    gp.gameState = gp.playState;
//                    gp.playMusic(0);

                }
                if (gp.ui.commandNum == 2) {
                    System.out.println("Do some sorcerer specific stuff!");
                    gp.gameState = gp.playState;
//                    gp.playMusic(0);

                }
                if (gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;

                }
            }
        }
    }
    public void playState(int code){
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_T){
            if (checkDrawTime == false){
                checkDrawTime = true;
            }else {
                checkDrawTime = false;
            }
        }
//        if(code == KeyEvent.VK_R){
//            gp.tileM.loadMap("/maps/worldv2.txt");
//        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }

        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void gameOverState(int code){
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
                gp.playMusic(0);

            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSoundEffect(9);
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN ) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSoundEffect(9);
            }

        }
        if(gp.ui.subState == 1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.playerSlotRow != 0){
                gp.ui.playerSlotRow--;
                gp.playSoundEffect(9);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.playerSlotCol != 0){
                gp.ui.playerSlotCol--;
                gp.playSoundEffect(9);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow !=3){
                gp.ui.playerSlotRow++;
                gp.playSoundEffect(9);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.playerSlotCol != 4){
                gp.ui.playerSlotCol++;
                gp.playSoundEffect(9);
            }
        }
    }
    public void npcInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.npcslotRow != 0){
                gp.ui.npcslotRow--;
                gp.playSoundEffect(9);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.npcslotCol != 0){
                gp.ui.npcslotCol--;
                gp.playSoundEffect(9);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.npcslotRow !=3){
                gp.ui.npcslotRow++;
                gp.playSoundEffect(9);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.npcslotCol != 4){
                gp.ui.npcslotCol++;
                gp.playSoundEffect(9);
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = false;
        }
    }
}

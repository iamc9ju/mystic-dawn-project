package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;


    public boolean upPressed,downPressed,leftPressed,rightPressed;
    //DEBUG
    boolean checkDrawTime;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode(); //return integer keyCode with the key in this event
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
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }else if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }

        //DEBUG
        if(code == KeyEvent.VK_T){
            if(checkDrawTime == false){
                checkDrawTime = true;
            }else if(checkDrawTime == true){
                checkDrawTime = false;
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
    }


}

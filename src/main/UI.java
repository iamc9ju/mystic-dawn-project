package main;

import objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;


public class UI {

    GamePanel gp;
    Font arial_40,arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#.00");//แปลงเป็นทศนิยมสองหลัก


    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN,40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
        arial_80B = new Font("Arial",Font.BOLD,80);
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        if(gameFinished == true){

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth(); // return length of this text;
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text,x,y);

            text = "You Time is : " + decimalFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth(); // return length of this text;
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize * 4);
            g2.drawString(text,x,y);

            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);
            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth(); // return length of this text;
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text,x,y);

            gp.gameThread = null; // Stop gameThread




        }else{
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("x "+ gp.player.hasKey,74,65);

            //TIME
            playTime += (double)1/60;
            g2.drawString("Time: "+decimalFormat.format(playTime),gp.tileSize*11,65);
            //MESSAGE
            if(messageOn == true){


                g2.setFont(g2.getFont().deriveFont(30F));//Get current font and derive font
                g2.drawString(message, gp.tileSize/2,gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }



    }
}

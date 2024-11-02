package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    public Lighting(GamePanel gp,int circleSize) {
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.screenWidth,gp.screenHeight));
        //สร้างแกน x y ของรัศมีของแสง
        int centerX = gp.player.screenX + (gp.tileSize)/2;
        int centerY = gp.player.screenY + (gp.tileSize)/2;
        //get top left x and y of the light;
        double x = centerX - (circleSize/2);
        double y = centerY - (circleSize/2);
        // create a light circle shape
        Shape circleShape = new Ellipse2D.Double(x,y,circleSize,circleSize);
        //Create a light circle area
        Area lightArea = new Area(circleShape);
        //Subtract the light circle from the screen rectangle
        screenArea.subtract(lightArea);

        //create a gradation effects
        Color color[] = new Color[5];
        float fraction[] = new float[5];

        color[0] = new Color(0,0,0,0f);
        color[1] = new Color(0,0,0,0.25f);
        color[2] = new Color(0,0,0,0.5f);
        color[3] = new Color(0,0,0,0.75f);
        color[4] = new Color(0,0,0,0.98f);


        g2.setColor(new Color(0,0,0,0.95f));
        //draw the screen rectangle without the light circle area
        g2.fill(screenArea);

        g2.dispose();
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter,0,0,null);
    }
}

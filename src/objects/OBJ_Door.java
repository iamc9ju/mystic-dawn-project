package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Door extends Entity {
    public  OBJ_Door(GamePanel gp){
        super(gp);
        name = "Door";
        down1 = setUp("/objects/door",gp.tileSize,gp.tileSize);
        down2 = setUp("/objects/door",gp.tileSize,gp.tileSize);

        collision = true;



    }
}

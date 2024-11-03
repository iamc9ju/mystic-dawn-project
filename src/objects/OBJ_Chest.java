package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    public static final String objName = "Chest";
    public  OBJ_Chest(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setUp("/objects/chest",gp.tileSize,gp.tileSize);
    }
}

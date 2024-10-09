package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setUp("/objects/heart_full",gp.tileSize,gp.tileSize);
        image2 = setUp("/objects/heart_blank",gp.tileSize,gp.tileSize);
        image3 = setUp("/objects/heart_half",gp.tileSize,gp.tileSize);

    }
}
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_DarkSword extends Entity {

    public OBJ_DarkSword(GamePanel gp) {
        super(gp);
        type = type_sword;
        name = "Dark Sword";
        down1 = setUp("/objects/Darksword",gp.tileSize,gp.tileSize);
        attackValue = 99;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name +"]\nAn old sword";
        price = 0;
    }
}

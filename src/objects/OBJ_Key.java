package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    GamePanel gp;

    public  OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Key";
        down1 = setUp("/objects/key",gp.tileSize,gp.tileSize);
        description = "[" + name +"]\nIt opens a door.";
        price = 100;
        stackable =true;


//
    }

    public boolean use(Entity entity){
//        gp.gameState = gp.dialogueState;
//        gp.ui.currentDialogue = "Test " + name + "!\n"
//                + "Your life has been recovered by " + value + ".";
        return true;
    }
}

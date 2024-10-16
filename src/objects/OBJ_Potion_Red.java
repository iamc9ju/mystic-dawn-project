package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        value = 5;
        name = "Red Potion";
        down1 = setUp("/objects/potion_red",gp.tileSize,gp.tileSize);
        description = "[" + name +"]\nHeals your life by " + value +".";
        price = 5;
        stackable = true;
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
                + "Your life has been recovered by " + value + ".";
        entity.life += value;
        return true;
    }

}

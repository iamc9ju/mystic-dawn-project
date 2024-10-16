package entity;

import main.GamePanel;
import objects.*;

public class NPC_Merchant extends Entity {
    public  NPC_Merchant(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;

        getNpcImage();
        setDialogue();
        setItem();
    }

    public void getNpcImage() {
        up1 = setUp("/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        up2 = setUp("/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        down1 = setUp("/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        down2  = setUp("/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        left1 = setUp("/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        left2 = setUp("/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        right1 = setUp("/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        right2 = setUp("/npc/merchant_down_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0] = "Do you want to trade ?";
        dialogues[1] = "You come to this island to find \n the treasure?";
        dialogues[2] = "I used to be a great wizard \n but now...I'm a bit too old \n for takingnan adventure.";
        dialogues[3] = "Well, good luck on you.";
    }

    public void setItem(){
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));

    }

    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}

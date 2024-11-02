package main;

import entity.Entity;
import objects.*;

public class EntityGenerator {
    GamePanel gp;
    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }
    public Entity getObject(String itemName){

        Entity obj = null;

        switch (itemName){
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_Boots.objName: obj = new OBJ_Boots(gp);break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp);break;
            case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp);break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp);break;
            case OBJ_Sword_Normal.objName: obj =  new OBJ_Sword_Normal(gp);break;
            case OBJ_Door.objName: obj = new OBJ_Door(gp);break;
        }
        return obj;
    }
}

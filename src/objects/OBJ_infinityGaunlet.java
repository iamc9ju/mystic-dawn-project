package objects;
import entity.Entity;
import main.GamePanel;
public class OBJ_infinityGaunlet extends Entity{
    GamePanel gp;
    public static final String objName = "Infiny Gaunlet";
    public OBJ_infinityGaunlet(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDialogue();
//        type = type_sword;
        name = objName;
        down1 = setUp("/objects/infinityGaunlet",gp.tileSize,gp.tileSize);
        description = "["+name+"]\n"+"I'm a Purple Sweet Potato";

    }

    public void setDialogue(){
        dialogues[0] = "You pick up the Infinity Gaunlet";
        dialogues[1] = "You can save the Realm";
    }
//    public boolean use(Entity entity){
//        gp.gameState = gp.cutsceneState;
//        gp.csManager.sceneNum = gp.csManager.ending;
//        return true;
//    }
}

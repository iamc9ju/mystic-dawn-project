package objects;
import entity.Entity;
import main.GamePanel;
public class OBJ_infinityGaunlet extends Entity{
    GamePanel gp;
    public static final String objName = "Infiny Gaunlet";
    public OBJ_infinityGaunlet(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setUp("/objects/infinityGaunlet",gp.tileSize,gp.tileSize);

    }
}

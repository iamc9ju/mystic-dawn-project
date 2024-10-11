package objects;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 10;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setUp("/projectile/fireball_up_1",gp.tileSize,gp.tileSize);
        up2 = setUp("/projectile/fireball_up_2",gp.tileSize,gp.tileSize);
        down1 = setUp("/projectile/fireball_down_1",gp.tileSize,gp.tileSize);
        down2 = setUp("/projectile/fireball_down_2",gp.tileSize,gp.tileSize);
        left1 = setUp("/projectile/fireball_left_1",gp.tileSize,gp.tileSize);
        left2 = setUp("/projectile/fireball_left_2",gp.tileSize,gp.tileSize);
        right1 = setUp("/projectile/fireball_right_1",gp.tileSize,gp.tileSize);
        right2 = setUp("/projectile/fireball_right_2",gp.tileSize,gp.tileSize);

    }
}

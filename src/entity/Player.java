package entity;

import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import objects.*;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;



    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        solidAreaDefaultX = solidArea.x;  //เก็บค่าเริ่มต้นของตำแหน่งพื้นที่ชน เพื่อใช้ในภายหลังหากจำเป็น
        solidAreaDefaultY = solidArea.y;

//        attackArea.width = 36;
//        attackArea.height = 36;


        setDefaultValues();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; //player position
        worldY = gp.tileSize * 21;
        gp.currentMap = 0;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 10000;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();

        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultPositions(){
        worldX = gp.tileSize * 23; //player position
        worldY = gp.tileSize * 21;
        direction = "down";
    }

    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
        attacking = false;

    }
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        up1 = setUp("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setUp("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size();i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size();i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setUp("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setUp("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_axe) {
            attackUp1 = setUp("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setUp("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void update() {

        if (attacking == true) {
            attacking();
        }


        // Check if any key is pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            // check tile collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);
            gp.collisionChecker.checkEntity(this,gp.monster);
            //CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK INTERACTIVE TILE COLLISION
            int interactiveTileIndex = gp.collisionChecker.checkEntity(this,gp.interactiveTile);

            //CHECK EVENT
            gp.eventHandler.checkEvent();

            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            if (keyH.enterPressed == true && attackCanceled == false) {
//                   gp.playMusic(7);
                attacking = true;
//                   spriteCounter = 0;
            }
            attackCanceled = false;

            if (attacking == false) {
                spriteCounter++;
                if (spriteCounter > 10) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            }
        } else {
            //
        }
        gp.keyH.enterPressed = false;

        if(gp.keyH.shotKeyPressed == true && projectile.alive == false
                && shotAvailableCounter == 30 && projectile.haveResource(this) == true ){
            System.out.println("Test");

            //set default coordinates, direction and user
            projectile.set(worldX,worldY,direction,true,this);

            //subtract the cose
            projectile.subtractResource(this);

            //add it ti the list
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;

            gp.playSoundEffect(10);
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
//            gp.stopMusic();
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.playSoundEffect(12);
        }
    }


    public void attacking(){

        spriteCounter++;
        if(spriteCounter <=5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            //Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/F for the attackArea
            switch (direction){
                case "up": worldY -= attackArea.height;break;
                case "down" : worldY += attackArea.height;break;
                case "left" : worldX -= attackArea.width;break;
                case "right" : worldX += attackArea.width;break;
            }
            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //Check monster collision with the updated worldX,worldY and solidArea
            int monsterIndex = gp.collisionChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex,attack);

            int interactiveTileIndex = gp.collisionChecker.checkEntity(this,gp.interactiveTile);
            damageInteractiveTile(interactiveTileIndex);



            //After checking collision,restore the original data;
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
            
        }
        if(spriteCounter >=25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public  void pickUpObject(int index){
        //pick up only items

        //inventory items

        if(index != 999) {
            if(gp.obj[gp.currentMap][index].type == type_pickupOnly){
                gp.obj[gp.currentMap][index].use(this);
                gp.obj[gp.currentMap][index] = null;
            }
            else{
                String text;

                if(canObtainItem(gp.obj[gp.currentMap][index])){
                    gp.playSoundEffect(1);
                    text = "Got a " + gp.obj[gp.currentMap][index].name + "!";
                }
                else{
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][index] = null;
            }

        }
    }

    public void contactMonster(int index){
        if(index != 999){

            if(invincible == false && gp.monster[gp.currentMap][index].dying == false){
                gp.playSoundEffect(6);

                int damage = gp.monster[gp.currentMap][index].attack - defense;
                if(damage <= 0){
                    damage = 1;
                    gp.ui.addMessage("This monster is too weak for you !");
                }
                life -= damage;
                invincible = true;
            }

        }
    }
    public void damageMonster(int index,int attack){
        if(index != 999){
            if(gp.monster[gp.currentMap][index].invincible == false){

                int damage = attack - gp.monster[gp.currentMap][index].defense;
                if(damage <= 0){
                    damage = 0;
                }

                gp.playSoundEffect(5);
                gp.monster[gp.currentMap][index].life -= damage;
                gp.ui.addMessage(damage+ "damage!");

                gp.monster[gp.currentMap][index].invincible = true;
                gp.monster[gp.currentMap][index].damageReaction();

                if(gp.monster[gp.currentMap][index].life <=0){
                    gp.monster[gp.currentMap][index].dying = true;
                    gp.ui.addMessage("Killed the "+gp.monster[gp.currentMap][index].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][index].exp + "!");
                    exp += gp.monster[gp.currentMap][index].exp;
                    checkLevelUp();
                }
            }
        }

    }

    public void damageInteractiveTile(int index){
        if(index != 999 && gp.interactiveTile[gp.currentMap][index].destructible == true
                && gp.interactiveTile[gp.currentMap][index].isCorrectItem(this) == true && gp.interactiveTile[gp.currentMap][index].invincible == false){
//            gp.playSoundEffect(11);
            gp.interactiveTile[gp.currentMap][index].playSoundEffect(11);
            gp.interactiveTile[gp.currentMap][index].life--;
            gp.interactiveTile[gp.currentMap][index].invincible = true;

            //Generate Particle
            generateParticle(gp.interactiveTile[gp.currentMap][index],gp.interactiveTile[gp.currentMap][index]);

            if(gp.interactiveTile[gp.currentMap][index].life == 0){
                gp.interactiveTile[gp.currentMap][index] = gp.interactiveTile[gp.currentMap][index].getDestroyedForm();
            }

        }
    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            life = maxLife;
            mana = maxMana;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSoundEffect(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n"
                    + "You feel stronger!";
        }
    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
        if (itemIndex < inventory.size()){

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){

                if (selectedItem.use(this) == true){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
//                gp.playSoundEffect(7);
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        //CHECK IF STACKABLE
        if(item.stackable == true){
            int index = searchItemInInventory(item.name);
            if(index != 999){
                inventory.get(index).amount++;
                canObtain =true;
            }
            else { //New item so need to check vacancy
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else { // Not stackable so check vacancy
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i= 0;i<inventory.size();i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;


        switch (direction) {
            case "up":
                if(attacking == false){image = (spriteNum == 1) ? up1 : up2;}
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 2) ? attackUp1 : attackUp2;
                }
                break;
            case "down":
                if(attacking == false){image = (spriteNum == 1) ? down1 : down2;}
                if(attacking == true){image = (spriteNum == 2) ? attackDown1 : attackDown2;}
                break;
            case "left":
                if(attacking == false){image = (spriteNum == 1) ? left1 : left2;}
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 2) ? attackLeft1 : attackLeft2;
                }
                break;
            case "right":
                if(attacking == false){image = (spriteNum == 1) ? right1 : right2;}
                if(attacking == true){image = (spriteNum == 2) ? attackRight1 : attackRight2;}
                break;
        }
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); //ทำให้ Graphic จากลงเหลือ 30%
        }
        if (image != null) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height);
        } else {
            System.err.println("Image is null for direction: " + direction);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));


//        g2.setFont(new Font("Arial",Font.PLAIN,26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter,10,400);
    }
}

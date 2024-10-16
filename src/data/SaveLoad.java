package data;

import entity.Entity;
import main.GamePanel;
import objects.*;

import javax.xml.crypto.Data;
import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    public Entity getObject(String itemName){

        Entity obj = null;

        switch (itemName){
            case "Woodcutter's Axe": obj = new OBJ_Axe(gp); break;
            case "Boots": obj = new OBJ_Boots(gp);break;
            case "Key": obj = new OBJ_Key(gp); break;
            case "Red Potion": obj = new OBJ_Potion_Red(gp);break;
            case "Blue Shield": obj = new OBJ_Shield_Blue(gp);break;
            case "Wood Shield": obj = new OBJ_Shield_Wood(gp);break;
            case "Normal Sword": obj =  new OBJ_Sword_Normal(gp);break;
            case "Door": obj = new OBJ_Door(gp);break;
        }
        return obj;
    }

    public void save(){
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage dataStorage = new DataStorage();
            dataStorage.level = gp.player.level;
            dataStorage.maxLife = gp.player.maxLife;
            dataStorage.life = gp.player.life;
            dataStorage.maxMana = gp.player.maxMana;
            dataStorage.mana = gp.player.mana;
            dataStorage.strength = gp.player.strength;
            dataStorage.dexterity = gp.player.dexterity;
            dataStorage.exp = gp.player.exp;
            dataStorage.nextLevelExp = gp.player.nextLevelExp;
            dataStorage.coin = gp.player.coin;

            //PLAYER INVENTORY
            for(int i =0;i<gp.player.inventory.size();i++){
                dataStorage.itemName.add(gp.player.inventory.get(i).name);
                dataStorage.itemAmount.add(gp.player.inventory.get(i).amount);
            }
            //PLAYER EQUIPMENT
            dataStorage.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            dataStorage.currentShieldSlot = gp.player.getCurrentShieldSlot();

            // Write the DataStorage object
            objectOutputStream.writeObject(dataStorage);

        }catch (Exception e){
            System.out.println("Save Exception!");
        }

    }

    public void load(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //Read the DataStorage Object
            DataStorage dataStorage = (DataStorage)objectInputStream.readObject();

            gp.player.level = dataStorage.level;
            gp.player.maxLife = dataStorage.maxLife;
            gp.player.life = dataStorage.life;
            gp.player.maxMana = dataStorage.maxMana;
            gp.player.mana = dataStorage.mana;
            gp.player.strength = dataStorage.strength;
            gp.player.dexterity = dataStorage.dexterity;
            gp.player.exp = dataStorage.exp;
            gp.player.nextLevelExp = dataStorage.nextLevelExp;
            gp.player.coin = dataStorage.coin;

            //PLAYER INVENTORY
            gp.player.inventory.clear();
            for (int i =0;i< dataStorage.itemName.size();i++){
                gp.player.inventory.add(getObject(dataStorage.itemName.get(i)));
                gp.player.inventory.get(i).amount = dataStorage.itemAmount.get(i);
            }
            //PLAYER EQUIPMENT
            gp.player.currentWeapon = gp.player.inventory.get(dataStorage.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(dataStorage.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerAttackImage();


        }catch (Exception e){
            System.out.println("Load Exception!");
        }
    }
}

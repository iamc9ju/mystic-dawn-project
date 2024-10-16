package data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // PLAYER STATS
    int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    //PLAYER INVENTORY
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<Integer> itemAmount = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;


}

package engine;

import entity.Item;

public class CashItemManager {
    private static CashItemManager instance;

    private Item.ItemType itemtype;

    private Item.PotionItem potionItem;

    int BulletSpeedItem_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[0]);
    int PointUpItem_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[1]);
    int ShieldItem_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[2]);
    int SpeedUpItem_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[3]);
    int ExtraLifeItem_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[4]);
    int MachineGunItem_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[5]);
    int speeduppotion_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[6]);
    int invincibilitypotion_count = Integer.parseInt(FileManager.getInstance().loadCashItem()[7]);
    CashItemManager() {
        this.itemtype = null;
        this.potionItem = null;
    }

    public static CashItemManager getInstance(){
        if (instance == null)
            instance = new CashItemManager();
        return instance;
    }


    public void setItemType(Item.ItemType itemtype){ this.itemtype = itemtype; }

    public void setPotionItem(Item.PotionItem potionItem) { this.potionItem = potionItem; }

    public Item.ItemType getItemType(){
        return this.itemtype;
    }

    public Item.PotionItem getPotionItem() { return this.potionItem; }

    public boolean buyItem(int item_Num){

        if (cashItemAmount(item_Num) == 10){
            return false;
        }
        else {
            switch (item_Num){
                case 1:
                    this.itemtype = Item.ItemType.BulletSpeedItem;
                    BulletSpeedItem_count++;
                    break;
                case 2:
                    this.itemtype = Item.ItemType.PointUpItem;
                    PointUpItem_count++;
                    break;
                case 3:
                    this.itemtype = Item.ItemType.ShieldItem;
                    ShieldItem_count++;
                    break;
                case 4:
                    this.itemtype = Item.ItemType.SpeedUpItem;
                    SpeedUpItem_count++;
                    break;
                case 5:
                    this.itemtype = Item.ItemType.ExtraLifeItem;
                    ExtraLifeItem_count++;
                    break;
                case 6:
                    this.itemtype = Item.ItemType.MachineGun;
                    MachineGunItem_count++;
                    break;
            }
        }
        FileManager.saveCashitem(item_Num, cashItemAmount(item_Num));
        return true;
    }

    public boolean buyPotion(int item_Num){

        if (cashItemAmount(item_Num) == 10){
            return false;
        }
        else {
            switch (item_Num){
                case 7:
                    this.potionItem = Item.PotionItem.SpeedUpPotion;
                    speeduppotion_count++;
                    break;
                case 8:
                    this.potionItem = Item.PotionItem.InvincibilityPotion;
                    invincibilitypotion_count++;
                    break;
            }
        }
        FileManager.saveCashitem(item_Num, cashItemAmount(item_Num));
        return true;
    }

    public boolean useItem(int item_Num){
        if (cashItemAmount(item_Num) == 0) {
            return false;
        }
        else {
            switch (item_Num){
                case 1:
                    BulletSpeedItem_count--;
                    this.itemtype = Item.ItemType.BulletSpeedItem;
                    break;
                case 2:
                    PointUpItem_count--;
                    this.itemtype = Item.ItemType.PointUpItem;
                    break;
                case 3:
                    ShieldItem_count--;
                    this.itemtype = Item.ItemType.ShieldItem;
                    break;
                case 4:
                    SpeedUpItem_count--;
                    this.itemtype = Item.ItemType.SpeedUpItem;
                    break;
                case 5:
                    ExtraLifeItem_count--;
                    this.itemtype = Item.ItemType.ExtraLifeItem;
                    break;
                case 6:
                    MachineGunItem_count--;
                    this.itemtype = Item.ItemType.MachineGun;
                    break;
            }
        }
        FileManager.saveCashitem(item_Num, cashItemAmount(item_Num));
        return true;
    }

    public boolean usePotionItem(int item_Num) {
        if (cashItemAmount(item_Num) == 0) {
            return false;
        }
        else {
            switch (item_Num) {
                case 7:
                    if (speeduppotion_count > 0) {
                        this.potionItem = Item.PotionItem.SpeedUpPotion;
                        speeduppotion_count--;
                    }
                    break;
                case 8:
                    if (invincibilitypotion_count > 0) {
                        this.potionItem = Item.PotionItem.InvincibilityPotion;
                        invincibilitypotion_count--;
                    }
                    break;
            }
        }
        FileManager.saveCashitem(item_Num, cashItemAmount(item_Num));
        return true;
    }
    public int cashItemAmount(int item_num){

        int amount = 0;

        switch (item_num) {
            case 1:
                amount = this.BulletSpeedItem_count;
                break;
            case 2:
                amount = this.PointUpItem_count;
                break;
            case 3:
                amount = this.ShieldItem_count;
                break;
            case 4:
                amount = this.SpeedUpItem_count;
                break;
            case 5:
                amount = this.ExtraLifeItem_count;
                break;
            case 6:
                amount = this.MachineGunItem_count;
                break;
            case 7:
                amount = this.speeduppotion_count;
                break;
            case 8:
                amount = this.invincibilitypotion_count;
                break;
        }
        return amount;
    }
}

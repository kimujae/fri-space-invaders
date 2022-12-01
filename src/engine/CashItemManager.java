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

    private CashItemManager() {
        this.itemtype = null;
        this.potionItem = null;
    }

    public static CashItemManager getInstance(){
        if (instance == null)
            instance = new CashItemManager();
        return instance;
    }

    public void setItemType(Item.ItemType itemtype){ this.itemtype = itemtype; }

//    public void setPotionItem(Item.PotionItem potionItem) { this.potionItem = potionItem; }

    public Item.ItemType getItemType(){
        return this.itemtype;
    }

//    public Item.PotionItem getPotionItem() { return this.potionItem; }

    public boolean buyItem(int item_Num){

        boolean check = false;

        switch (item_Num){
            case 1:
                this.itemtype = Item.ItemType.BulletSpeedItem;
                if(BulletSpeedItem_count < 10) {
                    BulletSpeedItem_count++;
                    check = true;
                }
                break;
            case 2:
                this.itemtype = Item.ItemType.PointUpItem;
                if(PointUpItem_count < 10) {
                    PointUpItem_count++;
                    check = true;
                }
                break;
            case 3:
                this.itemtype = Item.ItemType.ShieldItem;
                if(ShieldItem_count < 10) {
                    ShieldItem_count++;
                    check = true;
                }
                break;
            case 4:
                this.itemtype = Item.ItemType.SpeedUpItem;
                if(SpeedUpItem_count < 10) {
                    SpeedUpItem_count++;
                    check = true;
                }
                break;
            case 5:
                this.itemtype = Item.ItemType.ExtraLifeItem;
                if(ExtraLifeItem_count < 10) {
                    ExtraLifeItem_count++;
                    check = true;
                }
                break;
            case 6:
                this.itemtype = Item.ItemType.MachineGun;
                if(MachineGunItem_count < 10) {
                    MachineGunItem_count++;
                    check = true;
                }
                break;
//            case 7:
//                this.itemtype = Item.ItemType.BulletSpeedItem;
//                if(speeduppotion_count < 10) {
//                    speeduppotion_count++;
//                    check = true;
//                }
//                break;
//            case 8:
//                this.itemtype = Item.ItemType.BulletSpeedItem;
//                if(invincibilitypotion_count < 10) {
//                    invincibilitypotion_count++;
//                    check = true;
//                }
//                break;
        }
        FileManager.saveCashitem(item_Num, cashItemAmount(item_Num));
        return check;
    }

    public void useItem(int item_Num){
        switch (item_Num){
            case 1:
                if(BulletSpeedItem_count > 0) {
                    BulletSpeedItem_count--;
                    this.itemtype = Item.ItemType.BulletSpeedItem;
                }
                break;
            case 2:
                if(PointUpItem_count > 0) {
                    PointUpItem_count--;
                    this.itemtype = Item.ItemType.PointUpItem;
                }
                break;
            case 3:
                if(ShieldItem_count > 0) {
                    ShieldItem_count--;
                    this.itemtype = Item.ItemType.ShieldItem;
                }
                break;
            case 4:
                if(SpeedUpItem_count > 0) {
                    SpeedUpItem_count--;
                    this.itemtype = Item.ItemType.SpeedUpItem;
                }
                break;
            case 5:
                if(ExtraLifeItem_count > 0) {
                    ExtraLifeItem_count--;
                    this.itemtype = Item.ItemType.ExtraLifeItem;
                }
                break;
            case 6:
                if(MachineGunItem_count > 0) {
                    MachineGunItem_count--;
                    this.itemtype = Item.ItemType.MachineGun;
                }
                break;
//            case 7:
//                if(speeduppotion_count > 0) {
//                    speeduppotion_count--;
//                    this.potionItem = Item.PotionItem.SpeedUpPotion;
//                }
//                break;
//            case 8:
//                if(invincibilitypotion_count > 0) {
//                    invincibilitypotion_count--;
//                    this.potionItem = Item.PotionItem.InvincibilityPotion;
//                }
//                break;
        }
        FileManager.saveCashitem(item_Num, cashItemAmount(item_Num));
    }

//    public void usePotionItem(int item_Num) {
//        switch (item_Num) {
//            case 7:
//                if(speeduppotion_count > 0) {
//                    speeduppotion_count--;
//                    this.potionItem = Item.PotionItem.SpeedUpPotion;
//                }
//                break;
//            case 8:
//                if(invincibilitypotion_count > 0) {
//                    invincibilitypotion_count--;
//                    this.potionItem = Item.PotionItem.InvincibilityPotion;
//                }
//                break;
//        }
//    }
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
//            case 7:
//                amount = this.speeduppotion_count;
//                break;
//            case 8:
//                amount = this.invincibilitypotion_count;
//                break;
        }
        return amount;
    }


}

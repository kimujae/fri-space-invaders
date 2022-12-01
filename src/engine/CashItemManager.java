package engine;

import entity.Item;

public class CashItemManager {
    private static CashItemManager instance;

    private Item.ItemType itemtype;

    //pr 후 아래 주석 해제
    //int BulletSpeedItem_count = FileManager.getInstance().loadCashItem()[0];
    //int PointUpItem_count = FileManager.getInstance().loadCashItem()[];
    //int ShieldItem_count = FileManager.getInstance().loadCashItem()[];\
    //int SpeedUpItem_count = FileManager.getInstance().loadCashItem()[];\
    //int ExtraLifeItem_count = FileManager.getInstance().loadCashItem()[];
    //int MachineGunItem_count = FileManager.getInstance().loadCashItem()[];

    private CashItemManager() {
        this.itemtype = null;
    }

    public static CashItemManager getInstance(){
        if (instance == null)
            instance = new CashItemManager();
        return instance;
    }

    public void setItemType(Item.ItemType itemtype){
        //cashitem을 선택하면, 선택한 itemtype을 set 했다가, get으로 itemtype을 반환 할 것.
    }

    public Item.ItemType getItemtype(){
        return this.itemtype;
    }

    public boolean buyItem(int item_Num){
        //설명에 맞게 구현

        return true; // 임시로 달아둔 return문 구현 후 제거 바랍니다.
    }

    public void useItem(int item_Num){
        //설명에 맞게 구현
    }

    public int cashItemAmount(int item_num){
        /*
        BulletSpeedItem =1;
        PointUpItem =2;
        ShieldItem =3;
        SpeedUpItem =4;
        ExtraLifeItem =5;
        MachineGunItem = 6;

        ex_ item_num == 1 일 경우, return BulletSpeedItme_count;
         */
        return 0; // 임시로 달아둔 return문 구현 후 제거 바랍니다.
    }


}

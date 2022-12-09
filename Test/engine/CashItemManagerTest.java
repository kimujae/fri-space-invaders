package engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashItemManagerTest {

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach");
    }

    @Test
    void getInstance() {
    }

    @Test
    void setItemType() {
    }

    @Test
    void setPotionItem() {
    }

    @Test
    void getItemType() {
    }

    @Test
    void getPotionItem() {
    }

    @Test
    @Tag("buyItem")
    void buyItem() {
        CashItemManager cashItemManager = new CashItemManager();
        boolean buy = cashItemManager.buyItem(2);
        assertEquals(true, buy);
        System.out.println("buyItem");
    }

    @Test
    @Tag("buyPotion")
    void buyPotion() {
        CashItemManager cashItemManager = new CashItemManager();
        boolean buyP = cashItemManager.buyPotion(7);
        boolean buyP2 = cashItemManager.buyPotion(7);
        assertEquals(true, buyP);
        assertEquals(true, buyP2);
        System.out.println("buyPotion");
    }

    @Test
    @Tag("useItem")
    void useItem() {
        CashItemManager cashItemManager = new CashItemManager();
        boolean use = cashItemManager.useItem(2);
        assertEquals(true, use);
        System.out.println("useItem");
    }

    @Test
    @Tag("usePotionItem")
    void usePotionItem() {
        CashItemManager cashItemManager = new CashItemManager();
        boolean buyP = cashItemManager.usePotionItem(7);
        boolean buyP2 = cashItemManager.usePotionItem(8);
        assertEquals(true, buyP);
        assertEquals(true, buyP2);
        System.out.println("usePotion");
    }

    @Test
    void cashItemAmount() {
    }
}
package engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    void buyItem() {
        CashItemManager cashItemManager = new CashItemManager();
        boolean buy = cashItemManager.buyItem(2);
        assertEquals(true, buy);
        System.out.println("buyItem");
    }

    @Test
    void buyPotion() {
    }

    @Test
    void useItem() {
        CashItemManager cashItemManager = new CashItemManager();
        boolean use = cashItemManager.useItem(2);
        assertEquals(true, use);
        System.out.println("useItem");
    }

    @Test
    void usePotionItem() {
    }

    @Test
    void cashItemAmount() {
    }
}
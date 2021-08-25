package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account myAcc;

    @BeforeEach
    void setUp() {
        myAcc = new Account(000);
    }

    @Test
    void accountGeneralTest() {
        assertEquals(000,myAcc.getPassword());
        myAcc.setPassword(123);
        assertEquals(123,myAcc.getPassword());
        myAcc.setBalance(20000);
        assertEquals(20000,myAcc.getBalance());
        assertEquals(4,myAcc.getStockList().getStocks().size());
        Stock testStock = new Stock("testStock");
        myAcc.addStockToStockList(testStock);
        assertEquals(5,myAcc.getStockList().getStocks().size());
        myAcc.setStock(testStock,200);
        assertEquals(1,myAcc.getHoldStock().size());
    }

    @Test
    void getHoldStockDetailTest() {
        Stock myStock = new Stock("myStock",true,5,10);
        myAcc.buyStock(myStock,10);
        assertEquals(myStock,myAcc.getHoldStockFromName("myStock"));
        assertEquals(null,myAcc.getHoldStockFromName("thisIsNotInList"));
        assertEquals(10000 - 10 * 10,myAcc.getBalance());
        assertEquals(true,myAcc.getHold().containsKey(myStock));
    }

    @Test
    void buySellStockTest() {
        Stock myStock = new Stock("myStock");
        myAcc.buyStock(myStock,10);
        assertEquals(1,myAcc.getHoldStock().size());
        myAcc.buyStock(myStock,100);
        assertEquals(1,myAcc.getHoldStock().size());
        myAcc.sellStock(myStock,5);
        assertEquals(1,myAcc.getHoldStock().size());
        myAcc.sellStock(myStock);
        assertEquals(0,myAcc.getHoldStock().size());
    }


}
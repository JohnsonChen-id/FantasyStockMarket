package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test unit for stockListClass
class StockListTest {

    StockList myStockList;


    @BeforeEach
    void setUp() {
        myStockList = new StockList();
    }

    @Test
    void stockAdminStatus() {
        Stock newStock = new Stock("myStock",true,2,10);
        assertEquals(2,newStock.getReputation());
        assertEquals(10,newStock.getPrice());
        Stock notAdminStock = new Stock("notAdminStock",false,2,10);
        assertFalse(notAdminStock.getPrice() == 10);
        newStock.setChange(false,10);
        assertFalse(newStock.getChange(true) == 10);
        assertEquals(-1,newStock.getChange(false));
        newStock.changePrice(50);
        assertTrue(newStock.getPrice() == 50);
        newStock.setChange(true,10);
        assertEquals(10,newStock.getChange(true));
    }


    @Test
    void addRemoveStockTest() {
        Stock myStock = new Stock("myStock");
        Stock myOtherStock = new Stock("myOtherStock");
        myStockList.addStock(myStock);
        assertEquals(5,myStockList.getStocks().size());
        myStockList.addStock(myOtherStock);
        assertEquals(6,myStockList.getStocks().size());
        myStockList.removeStock(myStock);
        assertEquals(5,myStockList.getStocks().size());
        myStockList.addStock(myOtherStock);
        assertEquals(5,myStockList.getStocks().size());
    }

    @Test
    void getStockByNameTest() {
        Stock myStock = new Stock("myStock");
        myStockList.addStock(myStock);
        assertEquals(myStock,myStockList.getStockByName("myStock"));
        assertEquals("ios",myStockList.getStockByName("ios").getName());
        assertEquals(null,myStockList.getStockByName("thisIsNotInList"));
    }

    @Test
    //Since we could not test random behaviour to compare numbers, what we can do is
    //make sure that the stocks are still the original stocks
    void changeAllTest() {
        myStockList.changeAll();
        assertEquals(4,myStockList.getStocks().size());
        Stock myStock = new Stock("myStock",true,1,4);
        myStockList.addStock(myStock);
        myStock.setChange(true,-8);
        myStockList.changeAll();
        assertEquals(6.67,myStock.getPrice());


    }
}
package persistence;


import model.Account;
import model.Stock;
import model.StockList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Account account = new Account(123);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account account = new Account(123);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            account = reader.read();
            assertEquals(123,account.getPassword());
            assertEquals(0,account.getHoldStock().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account account = new Account(123);
            Stock ios = new Stock("ios", true, 4,20);
            account.setStock(ios,300);
            Stock feetLocker = new Stock("feetLocker",true,3,10);
            account.setStock(feetLocker,200);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            account = reader.read();
            assertEquals(123,account.getPassword());
            StockList stockList = account.getStockList();
            assertEquals(4, stockList.getStocks().size());
            checkStock("ios",4,stockList.getStocks().get(0));
            checkStock("tisla",5,stockList.getStocks().get(1));
            assertEquals(2, account.getHoldStock().size());
            checkStock("ios",4,account.getHoldStockFromName("ios"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
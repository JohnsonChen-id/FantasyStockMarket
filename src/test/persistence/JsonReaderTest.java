package persistence;

import model.Account;

import model.StockList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account account = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountHold() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountHold.json");
        try {
            Account account = reader.read();
            assertEquals(0,account.getPassword());
            assertEquals(0, account.getHoldStock().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account account = reader.read();
            assertEquals(123,account.getPassword());
            StockList stockList = account.getStockList();
            assertEquals(4, stockList.getStocks().size());
            checkStock("ios",4,stockList.getStocks().get(0));
            checkStock("tisla",5,stockList.getStocks().get(1));
            assertEquals(2, account.getHoldStock().size());
            checkStock("ios",4,account.getHoldStockFromName("ios"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
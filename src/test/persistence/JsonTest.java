package persistence;

import model.Stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkStock(String name,int reputation,Stock stock) {
        assertEquals(name, stock.getName());
        assertEquals(reputation,stock.getReputation());
    }
}

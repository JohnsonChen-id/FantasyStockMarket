package model;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
    /*
    The stockList contains all stock that has been constructed and initialized, including  user-added random stocks
    as well as several default stocks that exist in the beginning.
     */

public class StockList {
    private List<Stock> stocks;

    //Create a default stockList with four initial stock
    public StockList() {
        stocks = new ArrayList<>();
        Stock ios = new Stock("ios", true, 4,20);
        Stock tisla = new Stock("tisla",true,5,15);
        Stock tacoBall = new Stock("tacoBall",true,1,15);
        Stock feetLocker = new Stock("feetLocker",true,3,10);
        addStock(ios);
        addStock(tisla);
        addStock(tacoBall);
        addStock(feetLocker);

    }


    public List<Stock> getStocks() {
        return stocks;
    }

    //MODIFIES: stocks
    //EFFECTS: adds stock to stockList if not existing
    public void addStock(Stock stock) {
        boolean exists = false;
        for (Stock existingStock : stocks) {
            if (existingStock.getName().equals(stock.getName())) {
                exists = true;
            }
        }
        if (! exists) {
            stocks.add(stock);
        }
    }

    //REQUIRES: stock already in stocks
    //MODIFIES: stocks
    //EFFECTS: removes stock from the stockList
    public void removeStock(Stock stock) {
        stocks.remove(stock);
    }

    ///REQUIRES: the stock is within the list
    //EFFECTS: produce stock from list given stock name
    public Stock getStockByName(String name) {
        for (Stock stock : stocks) {
            if (stock.getName().equals(name)) {
                return stock;
            }

        }
        return null;
    }

    //resource: https://www.geeksforgeeks.org/bigdecimal-class-java/
    //MODIFIES: stock.price, stock.change
    //EFFECTS: change all stock's price by adding change and giving it new changes
    public void changeAll() {
        for (Stock stock : stocks) {
            double price;
            price = stock.getPrice() + (stock.getChange(true));
            if (price <= 4 && stock.getChange(true) <= 0) {
                price = stock.getPrice() - (stock.getChange(true) / 3);
            }
            BigDecimal priceSet = new BigDecimal(Double.toString(price));
            priceSet = priceSet.setScale(2, RoundingMode.HALF_UP);
            stock.changePrice(priceSet.doubleValue());
            stock.nextChange();
        }
    }


}

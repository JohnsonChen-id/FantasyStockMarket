package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.HashMap;
import java.util.Set;

    /*
    This is a class that stores all user information such as account balance, account hold stock list.
     */
public class Account implements Writeable {

    //private static final int STOCK_PRICE_CAP = 10000;
    private static final int START_BALANCE = 10000;
    private int balance;
    private HashMap<Stock, Integer> holdDetail;
    private int password;
    private StockList myStockList = new StockList();

    public Account(int password) {
        initialize(password);
        holdDetail = new HashMap<>();
    }

    //EFFECT: get account initial balance to START_BALANCE
    private void initialize(int password) {
        balance = START_BALANCE;
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public StockList getStockList() {
        return myStockList;
    }


    public int getPassword() {
        return password;
    }

    public HashMap getHold() {
        return holdDetail;
    }

    public Set<Stock> getHoldStock() {
        return holdDetail.keySet();
    }

    public void addStockToStockList(Stock stock) {
        myStockList.addStock(stock);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setStock(Stock stock,int amount) {
        holdDetail.put(stock,amount);
    }

    //REQUIRES: stock is in list of hold stock
    //EFFECTS: given stock name to produce the given stock from hold list.
    public Stock getHoldStockFromName(String name) {
        for (Stock stock : getHoldStock()) {
            if (stock.getName().equals(name)) {
                return stock;
            }
        }
        return null;
    }

    //REQUIRES: stock price * num <= balance, stock is valid
    //MODIFIES: this, balance
    //EFFECTS: purchase stock and put it into holdList, then take that off balance
    public void buyStock(Stock stock, int num) {
        if (holdDetail.containsKey(stock)) {
            holdDetail.put(stock,num + holdDetail.get(stock));
        } else {
            holdDetail.put(stock,num);
        }
        balance -= stock.getPrice() * num;
    }

    //REQUIRES: stock not null and valid
    //MODIFIES: this, balance
    //EFFECTS: sell number of stock and take the number off, then add that into balance
    public void sellStock(Stock stock, int num) {
        int previous = holdDetail.get(stock);
        holdDetail.remove(stock);
        holdDetail.put(stock,previous - num);
        balance += stock.getPrice() * num;
    }

    //REQUIRES: stock not null and valid
    //MODIFIES: this, balance
    //EFFECTS: sell all of one type stock and take the stock off hold list. then add that into balance
    public void sellStock(Stock stock) {
        balance += stock.getPrice() * holdDetail.get(stock);
        holdDetail.remove(stock);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("password",password);
        json.put("balance",balance);
        json.put("hold stock list",holdListToJson());
        json.put("market stock list",myStockListToJson());
        return json;
    }

    private JSONArray holdListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Stock stock : holdDetail.keySet()) {
            jsonArray.put(holdStockToJson(stock));
        }
        return jsonArray;
    }

    private JSONObject holdStockToJson(Stock stock) {
        JSONObject json = new JSONObject();
        json.put("Stock",stock.toJson());
        json.put("hold amount",holdDetail.get(stock));
        return json;
    }

    private JSONArray myStockListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Stock stock : myStockList.getStocks()) {
            jsonArray.put(stock.toJson());
        }
        return jsonArray;
    }
}

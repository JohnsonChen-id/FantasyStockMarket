package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Account;
import model.Stock;
import org.json.*;

// Represents a reader that reads account from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        int password = jsonObject.getInt("password");
        int balance = jsonObject.getInt("balance");
        Account account = new Account(password);
        account.setBalance(balance);
        addHoldStock(account, jsonObject);
        addStockToMarketList(account,jsonObject);
        return account;
    }


    //EFFECTS: adds jsonArray from JSON objects representing holdStock list
    private void addHoldStock(Account account,JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("hold stock list");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStockAmount(account,nextStock);
        }
    }

    //MODIFIES: account holdStock
    //EFFECTS: reads from JSON and place the corresponding holdStock into account
    private void addStockAmount(Account account,JSONObject jsonObject) {
        int hold = jsonObject.getInt("hold amount");
        JSONObject jsonObjectStock = jsonObject.getJSONObject("Stock");
        Stock stock = getStockFromJson(jsonObjectStock);
        account.setStock(stock,hold);
    }

    //EFFECTS: reads from JSON to get an stock and returns it
    private Stock getStockFromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int reputation = jsonObject.getInt("reputation");
        double price = jsonObject.getDouble("price");
        double change = jsonObject.getDouble("change");
        Stock stock = new Stock(name,true,reputation,price);
        stock.setChange(true,change);
        return stock;
    }

    //MODIFIES: account stockList
    //EFFECTS: reads from JSON to get list of stock in market and place that into stockList
    private void addStockToMarketList(Account account,JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("market stock list");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            account.addStockToStockList(getStockFromJson(nextStock));
        }
    }
}


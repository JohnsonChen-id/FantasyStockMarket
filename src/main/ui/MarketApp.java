package ui;

import model.Account;
import model.Stock;
import model.StockList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
    This is the main ui page for all user related interface. This interface allows user to make changes to their
    account and play with the game. The user now can buy in, sell according to the market price and when finished,
    press next day to progress in the time line.
 */

public class MarketApp {

    private static final String JSON_STORE = "./data/accountStore.json";
    Account myAccount = new Account(0);
    private Scanner input;
    StockList myStockList = myAccount.getStockList();
    boolean continueApp = true;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: instantiates json reader and writer and runs the market
    public MarketApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMarket();

    }

    // 1 check account detail or add stock from user
    // 1.a check account balance
    // 1.b check buy-in stock value
    // 1.c add new stock to stock list
    // 2 check market stock list
    // 3 buy in stock
    // 4 sell out stock
    // 5 next workday (or timer)
    public void runMarket() {

        input = new Scanner(System.in);
        System.out.println("Hello Dear User!");
        System.out.println("This is your personal stock market mini game");
        System.out.println("Do u want to load profile? y for yes, or press any key to register");
        String key = input.next();
        if (key.equals("y")) {
            loadAll();
            login();
        } else {
            System.out.println("please input your password, mush be all integers:");
            int password = input.nextInt();
            myAccount.setPassword(password);
        }
        while (continueApp) {
            mainPage();
        }

    }

    //EFFECTS: after the first login day of login new function runs
    public void runMarketAfterLoginDay() {
        input = new Scanner(System.in);
        System.out.println("Hello Dear User!");
        System.out.println("A brand new day!");
        while (continueApp) {
            mainPage();
        }
    }

    //EFFECTS: this is the main page for ui containing the general interface
    public void mainPage() {
        printMain();
        String key = input.next();
        switch (key.toLowerCase()) {
            case "a":
                accountDetail();
                break;
            case "c":
                printMarketList();
                break;
            case "d":
                purchase();
                break;
            case "e":
                sell();
                break;
            case "#":
                myStockList.changeAll();
                runMarketAfterLoginDay();
            case "z":
                exit();

        }


    }

    //EFFECTS: prompts user to a menu for user account detail
    private void accountDetail() {
        System.out.println("Please enter which account detail you want to get: ");
        System.out.println("Enter m for check my account balance");
        System.out.println("Enter b for check buy-in hold stock value");
        System.out.println("Enter n to add new stock into market");
        System.out.println("Enter 0 to return back to main menu");
        String key = input.next();
        switch (key) {
            case "m":
                System.out.println(myAccount.getBalance() + "is your balance now");
                break;
            case "b":
                checkHold();
                break;
            case "n":
                System.out.println("Please input the name of your stock");
                String name = input.next();
                Stock stock = new Stock(name);
                myAccount.addStockToStockList(stock);
                break;
            case "0":
                mainPage();
                break;

        }
    }

    //EFFECTS:helper function to print out the main menu
    private void printMain() {

        System.out.println("Please select an option from below :");
        System.out.println("a - Check my account detail or add new stock");
        System.out.println("c - check market stock list");
        System.out.println("d - purchase");
        System.out.println("e - sell");
        System.out.println("# - next workday");
        System.out.println("z - exit");
    }

    //MODIFIES:myAccount balance, myAccount holdDetail
    //EFFECTS: print out user interface of buy in stock, including showing the stock list, reading from keyboard input
    //         and compute purchase into user account
    private void purchase() {
        String name;
        int amount;
        double price;
        double total;
        Stock stock;
        System.out.println("Which stock do you want to buy:");
        System.out.println("Press * for stock list, or type the name of stock ( please enter exact the name)");
        name = input.next();

        if (name.equals("*")) {
            printMarketList();
            System.out.println(" ");
            purchase();
        } else {
            System.out.println("You want to buy in " + name);
            System.out.println("Please enter the number of stock you want to buy in: ");
            amount = input.nextInt();
            stock = myStockList.getStockByName(name);
            price = stock.getPrice();
            System.out.println("You want to buy in " + amount + "of " + name + "price is " + price);
            total = amount * price;
            System.out.println("total is" + total);
            cashOut(amount,total,stock);
        }

    }

    //MODIFIES:myAccount balance, myAccount holdDetail
    //EFFECTS: print out user interface of sell stock, including showing the hold stock list, reading from keyboard
    //         input and compute the cash into user account
    private void sell() {
        String name;
        int amount;
        double price;

        Stock stock;
        System.out.println("Which stock do you want to sell:");
        System.out.println("Press * for hold stock list, or type the name of stock ( please enter exact the name)");
        name = input.next();
        if (name.equals("*")) {
            printHoldList();
            System.out.println(" ");
            sell();
        } else { //TODO: check stock in list
            System.out.println("You want to sell" + name);
            System.out.println("Please enter the number of stock you want to sell, or -1 for sell all");
            amount = input.nextInt();
            stock = myAccount.getHoldStockFromName(name);
            price = stock.getPrice();
            sellHelper(amount,stock,name,price);
        }
    }

    //MODIFIES:myAccount balance, myAccount holdDetail
    //EFFECTS: input sell amount, the stock and price to finish the selling process dealing with account
    private void sellHelper(int amount, Stock stock, String name,double price) {
        double total;
        if (amount == -1) {
            System.out.println("You want to sell ALL of " + name + "price is" + price);
            int number = (int) myAccount.getHold().get(stock);
            total = number * price;
            System.out.println("total is " + total);
            myAccount.sellStock(stock);
        } else {
            System.out.println("You want to sell" + amount + "of" + name + "price is" + price);
            total = amount * price;
            System.out.println("total is" + total);
            myAccount.sellStock(stock,amount);
        }

    }

    //EFFECTS: prints out the list of all stocks kept in stockList, in columns as listed
    //          "stock name" + "current price"
    private void printMarketList() {
        for (Stock stock : myAccount.getStockList().getStocks()) {
            String name = stock.getName();
            double price = stock.getPrice();
            System.out.println(name + "     " + price);
        }
    }

    //EFFECTS: print out a summary of holding worth of all stocks individually and a total worth of all holds
    private void checkHold() {
        double totalWorth = 0;
        for (Stock stock : myAccount.getHoldStock()) {
            int amount = (int) myAccount.getHold().get(stock);
            double value = stock.getPrice() * amount;
            System.out.println(stock.getName() + "     " + amount + "   for a worth of" + value);
            totalWorth += value;
        }
        System.out.println("The total worth of all hold stock is " + totalWorth);
    }

    //EFFECTS: print our a short summary of all stocks hold and amount of each
    private void printHoldList() {
        for (Stock stock : myAccount.getHoldStock()) {
            int amount = (int) myAccount.getHold().get(stock);
            System.out.println(stock.getName() + "     holding" + amount);
        }
    }

    //MODIFIES:myAccount balance, myAccount holdDetail
    //EFFECTS: take buy in amount, the stock and total value to finish the buy in process dealing with the account
    private void cashOut(int amount, double total, Stock stock) {
        if (total <= myAccount.getBalance()) {
            myAccount.buyStock(stock,amount);
            System.out.println("Your purchase was successful");
        } else {
            System.out.println("Your purchase failed. Try again? press t to retry");
            String key = input.next();
            if (key.equals("t")) {
                purchase();
            } else {
                mainPage();
            }
        }
    }

    //EFFECTS: if password correct then goes into the account
    private void login() {
        System.out.println("Please enter your account password");
        int key = input.nextInt();
        while (key != myAccount.getPassword()) {
            System.out.println("This is the wrong password");
            System.out.println("Please enter your account password");
            key = input.nextInt();
        }
        System.out.println("Welcome to your account");

    }

    //EFFECTS: prompt the user for saving progress and then exit
    private void exit() {
        System.out.println("Do u want to save your progress? y for yes");
        String key = input.next();
        if (key.equals("y")) {
            saveAll();
        }
        System.out.println("It's a pleasure seeing you at market this time!");
        continueApp = false;
    }

    // EFFECTS: saves account to file
    public void saveAll() {
        try {
            jsonWriter.open();
            jsonWriter.write(myAccount);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadAll() {
        try {
            myAccount = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
// Future version todos: record the buy in price （not this version)
//                       Create stock #index for user choice in the future (not this version)
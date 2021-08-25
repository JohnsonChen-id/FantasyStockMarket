package model;

import org.json.JSONObject;
import persistence.Writeable;

import java.lang.Math;
/*
    This model builds up the most important class: Stock, for our game. A stock class will have it's reputation,
    used to influence the change of the stock price; a price of worth per unit at this time, a name, and a change
    for the next workday period.
 */

public class Stock implements Writeable {

    private static final int MAX_REP = 5;
    private static final int MIN_BEGIN_PRICE = 10;
    private static final int MAX_BEGIN_PRICE = 20;
    private static final int MIN_CHANGE_TILE = -7;
    private static final int MAX_CHANGE_TILE = 7;
    private final int reputation;
    private double price;
    private final String name;
    private double change;

    //creates user stock with random reputation, begin price and change for next period
    public Stock(String name) {
        this.name = name;
        this.reputation = (int) (Math.random() * (MAX_REP + 1));
        this.price = (int)(Math.random() * (MAX_BEGIN_PRICE - MIN_BEGIN_PRICE + 1)
                 + MIN_BEGIN_PRICE);
        this.change = (int) ((int)(Math.random() * (MAX_CHANGE_TILE - MIN_CHANGE_TILE + 1)
                         + MIN_CHANGE_TILE) * 0.04 + reputation * 0.02);
    }

    //given admin status, create stock with given reputation and begin price, but no control over change
    public Stock(String name, boolean admin,int reputation, double price) {
        this.name = name;
        this.reputation = reputation;
        this.change = (int)(Math.random() * (MAX_CHANGE_TILE - MIN_CHANGE_TILE + 1)
                + MIN_CHANGE_TILE) * 0.04 + reputation * 0.02;
        if (admin) {
            this.price = price;
        } else {
            this.price = (int)(Math.random() * (MAX_BEGIN_PRICE - MIN_BEGIN_PRICE + 1)
                    + MIN_BEGIN_PRICE);
        }

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getChange(boolean admin) {
        if (admin) {
            return change;
        }
        return -1;
    }

    public int getReputation() {
        return reputation;
    }


    //MODIFIES: this
    //EFFECTS :produce a function to generate next change status according to random and stock reputation
    public void nextChange() {
        change =
                (Math.random() * (MAX_CHANGE_TILE - MIN_CHANGE_TILE + 1)
                + MIN_CHANGE_TILE) * 0.5 + reputation * 0.1;
    }

    public void setChange(boolean admin, double change) {
        if (admin) {
            this.change = change;
        }
    }

    //MODIFIES: this
    //EFFECTS : a function helper for the changeAll function within stockList
    protected void changePrice(double value) {
        price = value;

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("reputation",reputation);
        json.put("price",price);
        json.put("change",change);
        return json;
    }
}

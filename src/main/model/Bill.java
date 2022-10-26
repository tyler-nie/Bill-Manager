package model;

import org.json.JSONObject;
import persistence.Writable;

public class Bill implements Writable {
    private int payeeId;
    private int id;
    private int numberOfPeople;
    private double cost;

    // Requires: cost > 0, num >= 2
    // Effects: creates a bill with id, the id of person who paid the bill, the number of people, and the cost
    public Bill(int id, int personId, double cost, int num) {
        this.id = id;
        this.payeeId = personId;
        this.numberOfPeople = num;
        this.cost = cost;
    }

    public int getID() {
        return id;
    }

    public int getPersonID() {
        return payeeId;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public double getCost() {
        return cost;
    }

    public double splitBill() {
        return cost / numberOfPeople;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("payee id", this.payeeId);
        json.put("number of people", this.numberOfPeople);
        json.put("cost", this.cost);
        return json;
    }

}

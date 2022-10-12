package model;

public class Bill {
    private int payeeId;
    private int id;
    private int numberOfPeople;
    private double cost;

    // Requires: cost > 0
    // Effects: creates a bill with id, the id of person who paid the bill, the number of people, and the cost
    public Bill(int id, int personId, int num, double cost) {
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
}

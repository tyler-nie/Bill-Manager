package model;

public class Bill {
    private int payeeId;
    private double cost;

    public Bill(int id, double cost) {
        this.payeeId = id;
        this.cost = cost;
    }
}

package app.entities;

public class Orderline {
    private int orderlineId;
    private int orderId;
    private int orderlineAmount;
    private int cupcakeTopId;
    private int cupcakeBottomId;
    private int orderlinePrice;


    public Orderline(int orderlineId, int orderId, int orderlineAmount, int cupcakeTopId, int cupcakeBottomId, int orderlinePrice) {
        this.orderlineId = orderlineId;
        this.orderId = orderId;
        this.orderlineAmount = orderlineAmount;
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.orderlinePrice = orderlinePrice;
    }

    public int getOrderlineId() {
        return orderlineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getOrderlineAmount() {
        return orderlineAmount;
    }

    public int getCupcakeTopId() {
        return cupcakeTopId;
    }

    public int getCupcakeBottomId() {
        return cupcakeBottomId;
    }

    public int getOrderlinePrice() {
        return orderlinePrice;
    }

    @Override
    public String toString() {
        return "Orderline{" +
                "orderlineId=" + orderlineId +
                ", orderId=" + orderId +
                ", orderlineAmount=" + orderlineAmount +
                ", cupcakeTopId=" + cupcakeTopId +
                ", cupcakeBottomId=" + cupcakeBottomId +
                ", orderlinePrice=" + orderlinePrice +
                '}';
    }
}

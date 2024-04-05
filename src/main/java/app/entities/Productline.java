package app.entities;

public class Productline {
    private int productlineId;
    private int orderId;
    private int productlineAmount;
    private int cupcakeTopId;
    private int cupcakeBottomId;
    private int productlinePrice;


    public Productline(int productlineId, int orderId, int productlineAmount, int cupcakeTopId, int cupcakeBottomId, int productlinePrice) {
        this.productlineId = productlineId;
        this.orderId = orderId;
        this.productlineAmount = productlineAmount;
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.productlinePrice = productlinePrice;
    }

    public Productline(int productlineId, int cupcakeTopId, int cupcakeBottomId, int productlinePrice) {
        this.productlineId = productlineId;
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.productlinePrice = productlinePrice;
    }

    public int getProductlineId() {
        return productlineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductlineAmount() {
        return productlineAmount;
    }

    public int getCupcakeTopId() {
        return cupcakeTopId;
    }

    public int getCupcakeBottomId() {
        return cupcakeBottomId;
    }

    public int getProductlinePrice() {
        return productlinePrice;
    }

    @Override
    public String toString() {
        return "Orderline{" +
                "orderlineId=" + productlineId +
                ", orderId=" + orderId +
                ", orderlineAmount=" + productlineAmount +
                ", cupcakeTopId=" + cupcakeTopId +
                ", cupcakeBottomId=" + cupcakeBottomId +
                ", orderlinePrice=" + productlinePrice +
                '}';
    }
}

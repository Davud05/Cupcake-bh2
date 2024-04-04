package app.entities;

public class Order {
    private int orderId;
    private int customerId;
    private int orderAmount;
    private String orderDate;
    private int orderPrice;


    public Order(int orderId, int customerId, int orderAmount, String orderDate, int orderPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderAmount=" + orderAmount +
                ", orderDate='" + orderDate + '\'' +
                ", orderPrice=" + orderPrice +
                '}';
    }
}



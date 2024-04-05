package app.entities;

public class Top {
    private int topId;
    private int topPrice;
    private String topName;

    // Constructor
    public Top(int topId, int topPrice, String topName) {
        this.topId = topId;
        this.topPrice = topPrice;
        this.topName = topName;
    }

    public int getTopId() {
        return topId;
    }

    public void setTopId(int topId) {
        this.topId = topId;
    }

    // Getter for topPrice
    public int getTopPrice() {
        return topPrice;
    }

    // Getter for topName
    public String getTopName() {
        return topName;
    }

    @Override
    public String toString() {
        return "Top{" +
                "topId=" + topId +
                ", topPrice=" + topPrice +
                ", topName='" + topName + '\'' +
                '}';
    }
}

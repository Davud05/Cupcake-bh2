package app.entities;

public class Top
{
    private int topId;
    private String topPrice;
    private String topName;

    // Adjusting the constructor to align with the new member variables
    public Top(int topId, String topPrice, String topName) {
        this.topId = topId;
        this.topPrice = topPrice;
        this.topName = topName;
    }

    // Additional constructors or methods should be adjusted similarly if needed.

    public int getTopId() {
        return topId;
    }

    public String getTopPrice() {
        return topPrice;
    }

    public String getTopName() {
        return topName;
    }

    @Override
    public String toString() {
        return "Food{" +
                "topId=" + topId +
                ", topPrice='" + topPrice + '\'' +
                ", topName='" + topName + '\'' +
                '}';
    }
}

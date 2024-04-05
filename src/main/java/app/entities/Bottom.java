package app.entities;

public class Bottom {
    private int bottomId;
    private int bottomPrice;
    private String bottomName;

    // Constructor
    public Bottom(int bottomId, int bottomPrice, String bottomName) {
        this.bottomId = bottomId;
        this.bottomPrice = bottomPrice;
        this.bottomName = bottomName;
    }

    // Getter for bottomId
    public int getBottomId() {
        return bottomId;
    }

    // Setter for bottomId
    public void setBottomId(int bottomId) {
        this.bottomId = bottomId;
    }

    // Getter for bottomPrice
    public int getBottomPrice() {
        return bottomPrice;
    }

    // Getter for bottomName
    public String getBottomName() {
        return bottomName;
    }

    @Override
    public String toString() {
        return "Bottom{" +
                "bottomId=" + bottomId +
                ", bottomPrice=" + bottomPrice +
                ", bottomName='" + bottomName + '\'' +
                '}';
    }
}

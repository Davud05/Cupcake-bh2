package app.entities;

public class Bottom
{
    private int bottomId;
    private int bottomPrice;
    private String bottomName;


    public Bottom(int bottomId, int bottomPrice, String bottomName) {
        this.bottomId = bottomId;
        this.bottomPrice = bottomPrice;
        this.bottomName = bottomName;
    }

    public int getBottomId() {
        return bottomId;
    }


    public int getBottomPrice() {
        return bottomPrice;
    }

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

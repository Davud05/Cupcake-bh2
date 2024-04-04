package app.entities;

public class Bottom
{
    private int bottomId;
    private String bottomPrice;
    private String bottomName;
    public Bottom(int bottomId, String bottomPrice, String bottomName) {
        this.bottomId = bottomId;
        this.bottomPrice = bottomPrice;
        this.bottomName = bottomName;
    }


    // public Food(String bottomName) {
    //    this.bottomName = bottomName;
    // }

    public int getBottomId() {
        return bottomId;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public String getBottomName() {
        return bottomName;
    }

    @Override
    public String toString() {
        return "Bottom{" +
                "bottomId=" + bottomId +
                ", bottomPrice='" + bottomPrice + '\'' +
                ", bottomName='" + bottomName + '\'' +
                '}';
    }
}

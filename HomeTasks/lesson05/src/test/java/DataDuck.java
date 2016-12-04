/**
 * Created by sunny_IT on 12/4/2016.
 */
public class DataDuck {

    private String name;
    private String regularPrice;
    private String campaignPrice;
    private String regularPriceStyle;
    private String campaignPriceStyle;


    public DataDuck(String[] data) throws Exception {

        if(data.length != 5)
            throw new Exception("Invalid duck data ");

        this.name = data[0];
        this.regularPrice = data[1];
        this.campaignPrice = data[2];
        this.regularPriceStyle = data[3];
        this.campaignPriceStyle = data[4];
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        if(this == obj) return true;

        DataDuck otherDuck = (DataDuck) obj;

        if(otherDuck.name.compareTo(this.name) == 0
                && otherDuck.regularPrice.compareTo(this.regularPrice) == 0
                && otherDuck.campaignPrice.compareTo(this.campaignPrice) == 0
                && otherDuck.campaignPriceStyle.compareTo(this.campaignPriceStyle) == 0
                && otherDuck.regularPriceStyle.compareTo(this.regularPriceStyle) == 0) return true;

        return false;
    }
}

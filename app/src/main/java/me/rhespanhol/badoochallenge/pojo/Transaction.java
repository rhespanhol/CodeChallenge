package me.rhespanhol.badoochallenge.pojo;

/**
 * Created by rhespanhol on 21/12/15.
 */
public class Transaction {

    private String amount;

    private String sku;

    private String currency;

    private float gbpPrice;


    public String getAmount() {
        return amount;
    }

    public String getSku() {
        return sku;
    }

    public String getCurrency() {
        return currency;
    }

    public Float getGbpPrice() {
        return gbpPrice;
    }

    public void setGbpPrice(float gbpPrice) {
        this.gbpPrice = gbpPrice;
    }
}

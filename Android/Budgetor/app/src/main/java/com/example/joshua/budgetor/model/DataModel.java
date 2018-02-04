package com.example.joshua.budgetor.model;

/**
 * Created by joshuacheung on 9/21/17.
 */

public class DataModel {
    private String timeStamp;
    private String item;
    private String price;
    private String description;

    public String getTimeStamp () {return timeStamp;}

    public void setTimeStamp(){this.timeStamp = timeStamp;}

    public String getItem(){ return item; }

    public void setItem() { this.item = item; }

    public String getPrice(){ return price; }

    public void setPrice() { this.price = price; }

    public String description() { return description; }

    public void setDescription() { this.description = description; }

}

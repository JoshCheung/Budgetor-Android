package com.example.joshua.budgetor;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Joshua on 9/16/17.
 */

public class ListElement implements Parcelable{

    public String labelItems;
    public String labelPrices;
    public String setDescription;
    public String timeStamp;

    public ListElement(String date, String items, String prices, String descriptions) {
        timeStamp = date;
        labelItems = items;
        labelPrices = "$" + prices;
        setDescription = descriptions;
    }

    protected ListElement(Parcel in) {
        timeStamp = in.readString();
        labelItems = in.readString();
        labelPrices = in.readString();
        setDescription = in.readString();
    }

    public static final Creator<ListElement> CREATOR = new Creator<ListElement>() {
        @Override
        public ListElement createFromParcel(Parcel in) {
            return new ListElement(in);
        }

        @Override
        public ListElement[] newArray(int size) {
            return new ListElement[size];
        }
    };

    public String toString(){
        return (timeStamp + ", " + labelItems + " ," + labelPrices+ " ," + setDescription );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(labelItems);
        parcel.writeString(labelPrices);
        parcel.writeString(setDescription);
        parcel.writeString(timeStamp);
    }
}

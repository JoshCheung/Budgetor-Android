package com.example.joshua.budgetor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joshua on 9/15/17.
 */

public class Statistics extends Activity {
    public List<ListElement> logList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        logList = this.getIntent().getParcelableArrayListExtra("entryList");
        //System.out.println(logList);
        getTotal();
        perWeek();

    }

    private double totalAmount = 0;

    void getTotal() {
        for (int i = 0; i < logList.size(); i++) {
            totalAmount += Double.parseDouble(logList.get(i).labelItems.substring(1));
        }
        TextView total = (TextView) findViewById(R.id.total);
        total.setText("Total: $" + String.format("%.2f",totalAmount));
    }

    void perWeek(){
        TextView average = (TextView) findViewById(R.id.average);
        average.setText( "Per week: $" + String.format("%.2f",totalAmount/7));
    }

}

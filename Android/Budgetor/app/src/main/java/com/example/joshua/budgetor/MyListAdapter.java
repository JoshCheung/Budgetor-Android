package com.example.joshua.budgetor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.List;

/**
 * Created by joshuacheung on 9/21/17.
 */

public class MyListAdapter extends ArrayAdapter<ListElement> {

    List<ListElement> entryList;
    Context context;
    private LayoutInflater mInflater;

    public MyListAdapter(Context context, List<ListElement> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        entryList = objects;
    }

    @Override
    public ListElement getItem(int position) {
        return entryList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = mInflater.inflate(R.layout.list_element, null);
        }


        TextView tv = itemView.findViewById(R.id.itemText);
        TextView st = itemView.findViewById(R.id.priceText);
        TextView timeStamp = itemView.findViewById(R.id.dateTime);


        timeStamp.setText(getItem(position).timeStamp);
        tv.setText(getItem(position).labelItems);
        st.setText(getItem(position).labelPrices);


        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //longClick(view, position);
                return true;
            }
        });

        return itemView;
    }


}
package com.example.joshua.budgetor;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;

import android.provider.MediaStore;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import junit.framework.Test;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static List<ListElement> entryList = new ArrayList<ListElement>();
    JSONArray jsonArray = new JSONArray();
    RequestQueue queue;
    private PopupMenu popupmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(MainActivity.this, PopWindow.class);
                startActivity(add);
            }
        });
        queue = Volley.newRequestQueue(this);
        display();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }

    public void populateListView() {
        ArrayAdapter<ListElement> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private class MyListAdapter extends ArrayAdapter<ListElement> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.list_element, entryList);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.list_element, null);
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
                    longClick(view, position);
                    return true;
                }
            });

            return itemView;
        }
    }

    public void longClick(final View view, final int position) {
        popupmenu = new PopupMenu(MainActivity.this, view);
        popupmenu.getMenuInflater().inflate(R.menu.popup_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int cd = item.getItemId();
                if (cd == R.id.change) {
                    edit(entryList.get(position), position);
                } else if (cd == R.id.delete) {
                    delete(position);
                }
                populateListView();
                return true;
            }
        });
        popupmenu.show();
    }

    public void delete(int position) {
        entryList.remove(position);
    }

    public void edit(final ListElement oldItem, final int position) {
        //Toast.makeText(this, "EDITING", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.edit_box);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);
        txtMessage.setText("EDIT");
        txtMessage.setTextColor(Color.parseColor("#1F618D"));
        final EditText i_editor = dialog.findViewById(R.id.item_txt);
        final EditText p_editor = dialog.findViewById(R.id.price_txt);
        final EditText d_editor = dialog.findViewById(R.id.des_txt);
        final EditText ts_editor = dialog.findViewById(R.id.time);
        i_editor.setText(oldItem.labelItems);
        p_editor.setText(oldItem.labelPrices);
        d_editor.setText(oldItem.setDescription);
        TextView date = (TextView) findViewById(R.id.dateTime);
        ts_editor.setText(date.getText().toString());

        Button enter = dialog.findViewById(R.id.btdone);
        Button cancel = dialog.findViewById(R.id.btCancel);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = i_editor.getText().toString();
                String price = p_editor.getText().toString();
                String des = d_editor.getText().toString();
                String newTime = ts_editor.getText().toString();

                if (item.isEmpty() || item.length() == 0 || item.equals("") || item == null || price.isEmpty() || price == null || price.length() == 0) {
                    dialog.dismiss();
                } else {
                    entryList.set(position, new ListElement(newTime, item, price, des));
                    oldItem.timeStamp = newTime;
                    //TextView ts = (TextView)findViewById(R.id.dateTime);
                    //ts.setText("HELLO!");
                }
                populateListView();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
/*
    public void populateEntryList() {
        String item = getIntent().getStringExtra("new itemEntry");
        String price = getIntent().getStringExtra("new priceEntry");
        String des = getIntent().getStringExtra("new descriptionEntry");
        String date = getIntent().getStringExtra("new date");

        if (item != null && price != null && des != null) {
            entryList.add(new ListElement(item, price, des, date));
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(imageIntent, 0);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "/Budgetor/");
            imagesFolder.mkdirs();
            File image = new File(imagesFolder, "QR_" + timeStamp + ".jpg");
            System.out.println(image.getAbsolutePath().toString());
            Uri uriSavedImage = Uri.fromFile(image);
            System.out.println("time: " + timeStamp);
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "OPEN GALLERY", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_stats) {
            Toast.makeText(this, "OPEN Statistics", Toast.LENGTH_SHORT).show();
            Intent stats = new Intent(this, Statistics.class);
            stats.putParcelableArrayListExtra("entryList", (ArrayList<? extends Parcelable>) entryList);
            startActivity(stats);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void display(){

        String url = "https://script.googleusercontent.com/macros/echo?user_content_key=3sGqMRezgesJ7KNMO2qOno9OS7vO8sYSPdfWMwcmpI6G_zErUqJjfjN29wsIEhmBhAtbi6wZJ_nPWKxynOCkNiynzMQLPmBwOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1GhPSVukpSQTydEwAEXFXgt_wltjJcH3XHUaaPC1fv5o9XyvOto09QuWI89K6KjOu0SP2F-BdwUj3H8xjMjvLVo5LqctExJC1I9IWs9CQhlWO4v8kmFk9I9dBt_R6OsCP3LbHcAb3rI5y7FLqOV0Tk27B8Rh4QJTQ&lib=MnrE7b2I2PjfH799VodkCPiQjIVyBAxva";
        entryList.clear();
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("Sheet1");
                            for (int i = jsonArray.length()-1; i >=  0; i--) {
                                String time =  jsonArray.getJSONObject(i).getString("Time");
                                String item = jsonArray.getJSONObject(i).getString("Item");
                                String price = jsonArray.getJSONObject(i).getString("Price");
                                String des = jsonArray.getJSONObject(i).getString("Description");
                                entryList.add(new ListElement(time, item, price, des));
                                populateListView();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }

                });
            queue.add(jsObjRequest);

    }
}


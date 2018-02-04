package com.example.joshua.budgetor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Joshua on 9/7/17.
 */

public class PopWindow extends Activity {
    String date_time;
    String item_text;
    String price_Text;
    String description_Text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);
        Date now = new Date();
        EditText dateBlock = findViewById(R.id.time);
        String time = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.US).format(now).toString();
        dateBlock.setText(time);

    }
    public void onClickEnter(View v){
        EditText itemblock = findViewById(R.id.item);
        EditText priceBlock = findViewById(R.id.price);
        EditText descriptions =  findViewById(R.id.description);
        EditText dateBlock = findViewById(R.id.time);

        date_time = dateBlock.getText().toString().trim();
        item_text = itemblock.getText().toString().trim();
        price_Text = priceBlock.getText().toString().trim();
        description_Text = descriptions.getText().toString().trim();

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        if(item_text.isEmpty() || item_text.length() == 0 || item_text.equals("") || item_text == null || price_Text.length() == 0 || price_Text == "$") {


            dlgAlert.setMessage("Forgetting something?");
            dlgAlert.setTitle("Oh no!");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        else {

            Intent pressed = new Intent(this, MainActivity.class);
            //pressed.putExtra("new itemEntry", item_text);
            //pressed.putExtra("new priceEntry", price_Text);
            //pressed.putExtra("new descriptionEntry", description_Text);
            //pressed.putExtra("new date",date_time);
            new SendRequest().execute();
            startActivity(pressed);

        }
    }

    public void onClickCancel(View v){
        Intent cancel = new Intent(this, MainActivity.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(cancel);
        finish();
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //Change your web app deployed URL or or u can use this for attributes (name, country)
                URL url = new URL("https://script.google.com/macros/s/AKfycbzqirMw94rV7a_BNxPPugM7xUhKZ7DDnBcRPQwwFOtHF2V5kGKv/exec");

                JSONObject postDataParams = new JSONObject();


                String id= "1jyZyxXe65gxXicZsAKA76h_4S6t1adLEhF6Crfd7Q_k";
                //System.out.println("DATA: Description: " + description_Text);
                postDataParams.put("TimeStamp", date_time);
                postDataParams.put("Item", item_text);
                postDataParams.put("Price", price_Text);
                postDataParams.put("Description", description_Text);
                postDataParams.put("id",id);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    System.out.println("SB: " + sb);
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}


package com.example.joshua.budgetor;

import android.support.annotation.NonNull;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody;

import java.io.IOException;

/**
 * Created by joshuacheung on 9/21/17.
 */

public class JSONParser {

    private static final String MAIN_URL = "https://script.google.com/macros/s/AKfycbwdUs7rnmFmOzBN38lpFS3QGSDNB4YhH3IRoP6VZRDFRNozffA/exec?id=11jyZyxXe65gxXicZsAKA76h_4S6t1adLEhF6Crfd7Q_k&sheet=Sheet1";

    public static final String TAG = "TAG";

    private static final String KEY_USER_ID = "user_id";

    private static Response response;

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject getDataById(int userId) {

        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add(KEY_USER_ID, Integer.toString(userId))
                    .build();

            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        } catch (IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }





}

package com.example.asynchttpclienttest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client =  new  AsyncHttpClient();

        client.get("https://data.boch.gov.tw/opendata/assetsCase/5.1.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // 取出 JSONArray 的資料
                    jsonArray = new JSONArray(new String(responseBody, "utf8"));
                    System.out.println("jsonArray = " + jsonArray);

                    // 取出 JSONArray 中的 JsonObject
                    for (int i = 0; i < jsonArray.length(); i++ ) {
                        jsonObject = jsonArray.getJSONObject(i);

                        // 取出 JsonObject 中的 caseName 這筆資料
                        String caseName = jsonObject.getString("caseName");
                        System.out.println("caseName = " + caseName);
                    }
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }
}
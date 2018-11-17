package com.example.mohamedsherif.dawadoztask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CityForecasting extends AppCompatActivity {

    private TextView mDay1TempTextView;
    private TextView mDay2TempTextView;
    private TextView mDay3TempTextView;
    private TextView mDay4TempTextView;
    private TextView mDay5TempTextView;

    private ProgressDialog mProgressDialog;

    private int mCurrentCityId;
    private String mUrl_City_Moscow = "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&lang=zh_cn&appid=b1b15e88fa797225412429c1c50c122a1";
    private String mUrl_City_Altstadt = "https://samples.openweathermap.org/data/2.5/forecast/daily?q=M%C3%BCnchen,DE&appid=b6907d289e10d714a6e88b30761fae22";
    private String mUrl_City_Tawarano = "https://samples.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&appid=b1b15e88fa797225412429c1c50c122a1";
    private String mUrl_City_Mountain_View = "https://samples.openweathermap.org/data/2.5/forecast/daily?zip=94040&appid=b6907d289e10d714a6e88b30761fae22";

    // save data into share SharePreference
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mSharedPreferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_forecasting);
        getSupportActionBar().setTitle("Temperature");

        bindViews();

        mCurrentCityId = getIntent().getIntExtra("city_id", 0);

        switch (mCurrentCityId){
            case 1:
                if (isOnline()){
                    getTemperatureFromInternet(mUrl_City_Moscow);
                }else {
                    setTempOnTextViews(getTemperatureFromSharedPreference());
                }
                break;

            case 2:
                if (isOnline()){
                    getTemperatureFromInternet(mUrl_City_Altstadt);
                }else {
                    setTempOnTextViews(getTemperatureFromSharedPreference());
                }
                break;

            case 3:
                if (isOnline()){
                    getTemperatureFromInternet(mUrl_City_Tawarano);
                }else {
                    setTempOnTextViews(getTemperatureFromSharedPreference());
                }
                break;

            case 4:
                if (isOnline()){
                    getTemperatureFromInternet(mUrl_City_Mountain_View);
                }else {
                    setTempOnTextViews(getTemperatureFromSharedPreference());
                }
                break;

            default:
                Toast.makeText(this, "Invalid City ID", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void bindViews() {
        mDay1TempTextView = findViewById(R.id.city_forecasting_day_1);
        mDay2TempTextView = findViewById(R.id.city_forecasting_day_2);
        mDay3TempTextView = findViewById(R.id.city_forecasting_day_3);
        mDay4TempTextView = findViewById(R.id.city_forecasting_day_4);
        mDay5TempTextView = findViewById(R.id.city_forecasting_day_5);

        mProgressDialog = new ProgressDialog(this);

        // save data into share SharePreference
        mSharedPreference = getSharedPreferences("temp_file", MODE_PRIVATE);
        mSharedPreferenceEditor = mSharedPreference.edit();
    }

    private void getTemperatureFromInternet(String current_city_url) {
        mProgressDialog.setTitle("Getting Data");
        mProgressDialog.setMessage("Please wait until we get data...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        AndroidNetworking.get(current_city_url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.v("Sherif: ", response.toString());
                        parseJsonResponse(response);

                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgressDialog.dismiss();
                        Toast.makeText(CityForecasting.this, "Invalid API", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void parseJsonResponse(JSONObject jsonObjectRespnse) {
        int[] temperature_days = new int[5];

        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObjectRespnse.getJSONArray("list");

            for (int i = 0; i < 5; i++) {
                temperature_days[i] = jsonArray.getJSONObject(i).getJSONObject("temp").getInt("day");
            }

            // save data into share SharePreference
            saveTemperatureOnSharedPreference(temperature_days);

            setTempOnTextViews(temperature_days);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTempOnTextViews(int[] temperature_days){
        mDay1TempTextView.setText("Temperature: " + temperature_days[0]);
        mDay2TempTextView.setText("Temperature: " + temperature_days[1]);
        mDay3TempTextView.setText("Temperature: " + temperature_days[2]);
        mDay4TempTextView.setText("Temperature: " + temperature_days[3]);
        mDay5TempTextView.setText("Temperature: " + temperature_days[4]);
    }

    private void saveTemperatureOnSharedPreference(int[] temperature_days) {
        // save data into share SharePreference
        mSharedPreferenceEditor.putInt("city_" + mCurrentCityId + "_day_1", temperature_days[0]);
        mSharedPreferenceEditor.putInt("city_" + mCurrentCityId + "_day_2", temperature_days[1]);
        mSharedPreferenceEditor.putInt("city_" + mCurrentCityId + "_day_3", temperature_days[2]);
        mSharedPreferenceEditor.putInt("city_" + mCurrentCityId + "_day_4", temperature_days[3]);
        mSharedPreferenceEditor.putInt("city_" + mCurrentCityId + "_day_5", temperature_days[4]);
        mSharedPreferenceEditor.apply();
    }

    public int[] getTemperatureFromSharedPreference() {
        int[] temperature_days = new int[5];
        temperature_days[0] = mSharedPreference.getInt("city_" + mCurrentCityId + "_day_1", 0);
        temperature_days[1] = mSharedPreference.getInt("city_" + mCurrentCityId + "_day_2", 0);
        temperature_days[2] = mSharedPreference.getInt("city_" + mCurrentCityId + "_day_3", 0);
        temperature_days[3] = mSharedPreference.getInt("city_" + mCurrentCityId + "_day_4", 0);
        temperature_days[4] = mSharedPreference.getInt("city_" + mCurrentCityId + "_day_5", 0);
        return temperature_days;
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}

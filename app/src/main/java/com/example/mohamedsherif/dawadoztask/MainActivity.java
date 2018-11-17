package com.example.mohamedsherif.dawadoztask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.mohamedsherif.dawadoztask.adaptors.CitiesAdaptor;
import com.example.mohamedsherif.dawadoztask.models.City;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCitiesRecyclerView;
    private CitiesAdaptor mCitiesAdaptor;
    private ArrayList<City> mCitiesList = new ArrayList<>();

    private Button mContactUsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        fillCitiesList();

        mContactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactUs.class);
                startActivity(intent);
            }
        });
    }

    private void bindViews() {
        mCitiesRecyclerView = findViewById(R.id.main_activity_recycler_view);
        mCitiesAdaptor = new CitiesAdaptor(this, mCitiesList);
        mCitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCitiesRecyclerView.setAdapter(mCitiesAdaptor);

        mContactUsButton = findViewById(R.id.main_activity_contact_us_buttton);
    }

    private void fillCitiesList() {
        mCitiesList.add(new City(1,"Moscow"));
        mCitiesList.add(new City(2,"Altstadt"));
        mCitiesList.add(new City(3,"Tawarano"));
        mCitiesList.add(new City(4,"Mountain View"));
        mCitiesAdaptor.notifyDataSetChanged();
    }
}

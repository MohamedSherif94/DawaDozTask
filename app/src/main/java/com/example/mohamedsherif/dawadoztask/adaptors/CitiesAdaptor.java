package com.example.mohamedsherif.dawadoztask.adaptors;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamedsherif.dawadoztask.CityForecasting;
import com.example.mohamedsherif.dawadoztask.R;
import com.example.mohamedsherif.dawadoztask.models.City;

import java.util.ArrayList;

public class CitiesAdaptor extends RecyclerView.Adapter<CitiesAdaptor.CityViewHolder>{

    private ArrayList<City> citiesList;
    private Activity activity;

    public CitiesAdaptor(Activity activity, ArrayList<City> citiesList) {
        this.activity = activity;
        this.citiesList = citiesList;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_city_layout, parent, false);

        CityViewHolder vh = new CityViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CityViewHolder holder, int position) {
        final City currentCity = citiesList.get(position);
        holder.cityName.setText(currentCity.getmName());
        holder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CityForecasting.class);
                intent.putExtra("city_id", currentCity.getmID());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{

        TextView cityName;
        public CityViewHolder(View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.single_city_name);
        }
    }
}

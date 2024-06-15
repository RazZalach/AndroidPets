package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class MyFarmAdapter extends RecyclerView.Adapter<MyFarmHolder> {

    Context context;
    List<Farm> items;

    public MyFarmAdapter(Context context, List<Farm> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyFarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.farm_item_view, parent, false);
        return new MyFarmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFarmHolder holder, int position) {
        Farm currentFarm = items.get(position);

        long currentFarmId = currentFarm.getId();

        Log.e("raz","Farm name:" + currentFarm.getNameFarm());
        holder.indexNumber.setText(String.valueOf(position + 1)); // Display the position + 1 as the index number
        holder.farmName.setText(currentFarm.getNameFarm()); // Set the farm name
        int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
        int randomIndex = new Random().nextInt(images.length);
        holder.imageFarm.setImageResource(images[randomIndex]);
        holder.farmEditButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("farm_id", String.valueOf(currentFarmId));
            // Add more employee data as needed
            editor.apply();

            Intent intent = new Intent(context , FarmDetialsActivity.class);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

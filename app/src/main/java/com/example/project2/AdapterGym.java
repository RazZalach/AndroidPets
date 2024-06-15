package com.example.project2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.List;

public class AdapterGym extends RecyclerView.Adapter<HolderGym> {
    Context context;

    public AdapterGym(Context context, List<Gym> items) {
        this.context = context;
        this.items = items;
    }

    List<Gym> items;
    @NonNull
    @Override
    public HolderGym onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_gym, parent, false);
        return new HolderGym(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderGym holder, int position) {
        Gym gym = items.get(position);
        holder.nameView.setText(gym.getName());
        holder.address.setText(gym.getAddress());
        holder.indexNumber.setText(String.valueOf(position + 1));
        holder.coordinate.setText("Coordinates: " + gym.getCoordinates()[1] + ", " + gym.getCoordinates()[0]);
//        File imgFile = new File(gym.getPicUrl());
//
//        if (imgFile.exists()) {
//            Bitmap imgBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            holder.imageView.setImageBitmap(imgBitmap);
//        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

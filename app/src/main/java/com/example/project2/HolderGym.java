package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderGym extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView nameView, address ,indexNumber, coordinate;
    public HolderGym(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview_gym);
        nameView = itemView.findViewById(R.id.name_gym);
        address = itemView.findViewById(R.id.address);
        indexNumber = itemView.findViewById(R.id.index_number_gym);
        coordinate = itemView.findViewById(R.id.coordinate);
    }
}

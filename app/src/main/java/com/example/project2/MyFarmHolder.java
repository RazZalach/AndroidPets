package com.example.project2;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyFarmHolder extends RecyclerView.ViewHolder {
    public TextView indexNumber ,farmName;
    ImageView imageFarm;

    public ImageButton farmEditButton;
    public MyFarmHolder(@NonNull View itemView) {
        super(itemView);
        imageFarm = itemView.findViewById(R.id.image_farm);
        indexNumber = itemView.findViewById(R.id.index_number);
        farmName = itemView.findViewById(R.id.holder_farm_name);
        farmEditButton = itemView.findViewById(R.id.farm_edit_button);
    }
}

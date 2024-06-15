package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView, date_,diary_id ,indexNumber;
    ImageButton deleteButton , editButton;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        date_ = itemView.findViewById(R.id.date_);
        deleteButton = itemView.findViewById(R.id.delete_button);
        editButton = itemView.findViewById(R.id.edit_button);
        diary_id = itemView.findViewById(R.id.diary_id);
        indexNumber = itemView.findViewById(R.id.index_number);
    }
}


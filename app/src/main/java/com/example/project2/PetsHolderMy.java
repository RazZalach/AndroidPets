package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetsHolderMy  extends RecyclerView.ViewHolder {
    ImageView imageViewPet;
    TextView textViewName, textViewType, textViewAge, textViewColor, textViewIsAdopted;
    ImageButton deletebtn;
    Button buttonContact_my;
    public PetsHolderMy(@NonNull View itemView) {
        super(itemView);
        imageViewPet = itemView.findViewById(R.id.imageViewPet);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewType = itemView.findViewById(R.id.textViewType);
        textViewAge = itemView.findViewById(R.id.textViewAge);
        textViewColor = itemView.findViewById(R.id.textViewColor);
        textViewIsAdopted = itemView.findViewById(R.id.textViewIsAdopted);
        deletebtn = itemView.findViewById(R.id.delete_pet);
        buttonContact_my = itemView.findViewById(R.id.buttonContact_my);
    }
}

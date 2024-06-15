package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetsHolder extends RecyclerView.ViewHolder {
    ImageView imageViewPet;
    TextView textViewName, textViewType, textViewAge, textViewColor, textViewIsAdopted, textViewTimer; // Added textViewTimer
    Button savebtn, contactbtn;

    public PetsHolder(@NonNull View itemView) {
        super(itemView);

        imageViewPet = itemView.findViewById(R.id.imageViewPet);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewType = itemView.findViewById(R.id.textViewType);
        textViewAge = itemView.findViewById(R.id.textViewAge);
        textViewColor = itemView.findViewById(R.id.textViewColor);
        textViewIsAdopted = itemView.findViewById(R.id.textViewIsAdopted);
        savebtn = itemView.findViewById(R.id.buttonSavePet);
        contactbtn = itemView.findViewById(R.id.buttonContact);
        textViewTimer = itemView.findViewById(R.id.textViewTimer); // Initialize textViewTimer
    }
}

package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorseHolder extends RecyclerView.ViewHolder {
    public TextView textViewHorseName,textViewRace,textViewHorseAge, textViewIsHungry;
    public Button feed_horse_btn;

    public HorseHolder(@NonNull View itemView) {
        super(itemView);
        textViewHorseName = itemView.findViewById(R.id.textViewHorseName);
        textViewRace = itemView.findViewById(R.id.textViewRace);
        textViewHorseAge = itemView.findViewById(R.id.textViewHorseAge);
        textViewIsHungry = itemView.findViewById(R.id.textViewIsHungry );
        feed_horse_btn = itemView.findViewById(R.id.feed_horse_btn);

    }


}

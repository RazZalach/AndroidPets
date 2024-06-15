package com.example.project2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorseAdapter extends RecyclerView.Adapter<HorseHolder> {
    Context context;
    List<Horse> items;

    public HorseAdapter(Context context, List<Horse> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HorseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.horse_item_view, parent, false);
        return new HorseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorseHolder holder, @SuppressLint("RecyclerView") int position) {
        Horse horse = items.get(position);
        //textViewHorseName textViewRace textViewHorseAge textViewIsHungry
        holder.textViewHorseName.setText(horse.getHorseName());
        holder.textViewRace.setText(horse.getRace());
        holder.textViewHorseAge.setText(String.valueOf(horse.getHorseAge()));

        holder.textViewIsHungry.setText("Hungry: " + (horse.isHungry() ? "Yes" : "No"));

        if(horse.isHungry()){
            holder.feed_horse_btn.setVisibility(View.VISIBLE);
            holder.feed_horse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbFarm Db = new DbFarm(context);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    long farmId =Long.parseLong(sharedPreferences.getString("farm_id", "-1"));

                    Db.setHorseNotHungry(farmId, position);
                    refreshData(farmId);
                }
            });
        }else {
            holder.feed_horse_btn.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void refreshData(long farmId) {
        DbFarm db = new DbFarm(context);
        items.clear();
        items.addAll(db.getHorsesForFarm(farmId));
        notifyDataSetChanged();
    }
    public void updateData(List<Horse> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
}

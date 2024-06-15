package com.example.project2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PetsAdapterMy extends RecyclerView.Adapter<PetsHolderMy> {
    Context context;
    List<Pets> items;
    PetsDataBase petsDataBase;

    public PetsAdapterMy(Context context, List<Pets> items) {
        this.context = context;
        this.items = items;
        this.petsDataBase = new PetsDataBase(context);
    }

    @NonNull
    @Override
    public PetsHolderMy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.pets_item_view_my, parent, false);
        return new PetsHolderMy(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsHolderMy holder, @SuppressLint("RecyclerView") int position) {
        Pets pet = items.get(position);
        holder.textViewName.setText(pet.getName());

        // Set pet type
        holder.textViewType.setText(pet.getType());

        // Set pet age
        holder.textViewAge.setText("Age: " + pet.getAge() + " years");

        // Set pet color
        holder.textViewColor.setText("Color: " + pet.getColor());

        // Set adoption status
        holder.textViewIsAdopted.setText("Yes");

        Glide.with(context)
                .load(pet.getPicUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.baseline_person_24) // Placeholder image
                        .error(R.drawable.baseline_pets_24) // Error image in case of loading failure
                )
                .into(holder.imageViewPet);
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1);
                petsDataBase.deletePet(userId,pet.getName());
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.buttonContact_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactPhone = pet.getContactPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contactPhone));
                context.startActivity(intent);
            }
        });
        holder.imageViewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedPetName", pet.getName());
                editor.apply();

                Intent intent = new Intent(context, PetsDetialsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

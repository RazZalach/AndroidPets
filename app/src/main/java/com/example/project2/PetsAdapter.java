package com.example.project2;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PetsAdapter extends RecyclerView.Adapter<PetsHolder> {
    Context context;
    List<Pets> items;
    PetsDataBase petsDataBase;
    private Map<Integer, CountDownTimer> timers; // To store timers for each item
    private Map<Integer, Long> remainingTimes;

    public PetsAdapter(Context context, List<Pets> items) {
        this.context = context;
        this.items = items;
        this.petsDataBase = new PetsDataBase(context);
        this.timers = new HashMap<>();
        this.remainingTimes = new HashMap<>();
    }

    @NonNull
    @Override
    public PetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pets_item_view, parent, false);
        return new PetsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsHolder holder, @SuppressLint("RecyclerView") int position) {
        Pets pet = items.get(position);
        holder.textViewName.setText(pet.getName());
        holder.textViewType.setText(pet.getType());
        holder.textViewAge.setText("Age: " + pet.getAge() + " years");
        holder.textViewColor.setText("Color: " + pet.getColor());
        String adoptionStatus = pet.isAdopted() ? "Is Adopted: Yes" : "Is Adopted: No";
        holder.textViewIsAdopted.setText(adoptionStatus);

        Glide.with(context)
                .load(pet.getPicUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.baseline_person_24) // Placeholder image
                        .error(R.drawable.baseline_pets_24) // Error image in case of loading failure
                )
                .into(holder.imageViewPet);

        // Set up the countdown timer for each item
        CountDownTimer countDownTimer = timers.get(position);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        long randomTimeInMillis = getRandomTimeInMillis();
        remainingTimes.put(position, randomTimeInMillis);

        countDownTimer = new CountDownTimer(randomTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimes.put(position, millisUntilFinished);
                updateTimerText(holder.textViewTimer, millisUntilFinished); // Update timer text
            }

            @Override
            public void onFinish() {
                holder.savebtn.setVisibility(View.GONE);
                holder.textViewTimer.setVisibility(View.GONE); // Hide timer when finished
                timers.remove(position);
                remainingTimes.remove(position);
            }
        }.start();
        timers.put(position, countDownTimer);

        // Save button click listener
        holder.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1);
                if (userId != -1) {
                    List<Pets> AllUserPets = petsDataBase.getAllUserPets(userId);
                    for(Pets currentpet :AllUserPets ){
                        if(currentpet.getName().equals(pet.getName())){
                            Toast.makeText(context,"Pet " +pet.getName() +" All ready Adupted",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    petsDataBase.savePet(pet, userId);
                    Toast.makeText(context,"Pet " +pet.getName() +" Adupted",Toast.LENGTH_LONG).show();

                } else {
                    // Handle case where userId is not available
                }
            }
        });

        // Contact button click listener
        holder.contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactPhone = pet.getContactPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contactPhone));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private long getRandomTimeInMillis() {
        Random random = new Random();
        int randomSeconds = random.nextInt(41) + 20; // Generates random number between 20 to 60 inclusive
        return randomSeconds * 1000L; // Convert seconds to milliseconds
    }

    private void updateTimerText(TextView textView, long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textView.setText("Timer: " + timeLeftFormatted);
    }
}

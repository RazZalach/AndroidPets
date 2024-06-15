package com.example.project2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context context;
    List<DiaryPage> items;

    public MyAdapter(Context context, List<DiaryPage> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        DiaryPage diaryPage = items.get(position);
        holder.nameView.setText(diaryPage.getName());

        // Format the date before setting it to the TextView
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(diaryPage.getDateCreated());
        holder.date_.setText(formattedDate);
        holder.diary_id.setText(String.valueOf(items.get(position).getId()));
        holder.indexNumber.setText(String.format(Locale.getDefault(), "%d.", position + 1));
        // Load image from file path
        File imgFile = new File(diaryPage.getPicUrl());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imageView.setImageBitmap(myBitmap);
        } else {
            // Set a default image if the file doesn't exist
            holder.imageView.setImageResource(R.drawable.baseline_person_24);
        }

        DbDiary dbDiary = new DbDiary(context);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDiary.deleteDiaryPageById(diaryPage.getId());
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diary_id = Integer.parseInt(holder.diary_id.getText().toString());
                DiaryPage diaryPage = dbDiary.getDiaryPageById(diary_id);
                if (diaryPage != null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("diary_id", diary_id);
                    editor.apply();
                    Intent intent = new Intent(context, DiaryPageActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

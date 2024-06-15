package com.example.project2;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class HomeFragment extends Fragment {

    DbDiary dbDiary;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbDiary = new DbDiary(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        List<DiaryPage> items = dbDiary.readAllDiaryPages();
//
        if (items != null && !items.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(new MyAdapter(requireContext(), items));
        } else {
            Log.d(TAG, "No diary pages found or items is null");
        }

        return view;
    }
}


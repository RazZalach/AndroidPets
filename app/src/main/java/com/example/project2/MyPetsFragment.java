package com.example.project2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class MyPetsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PetsAdapterMy adapter;
    private PetsDataBase petsDataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pets, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_pets_my);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize Database
        petsDataBase = new PetsDataBase(getContext());

        // Load the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        if( userId != -1){
            // Get all pets for the user
            List<Pets> petsList = petsDataBase.getAllUserPets(userId);

            adapter = new PetsAdapterMy(getContext(), petsList);
            recyclerView.setAdapter(adapter);
        }

        return  view;
    }
}
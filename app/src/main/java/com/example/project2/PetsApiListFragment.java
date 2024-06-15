package com.example.project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


public class PetsApiListFragment extends Fragment {
    private RecyclerView recyclerView;
    private PetsAdapter petsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pets_api_list, container, false);

        recyclerView =view.findViewById(R.id.recyclerview_pets_api);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        PetsServiceApi petsServiceApi = new PetsServiceApi(requireContext());

        petsServiceApi.fetchPets(new PetsServiceApi.PetsResponseListener() {


            @Override
            public void onResponse(List<Pets> petsList) {
                petsAdapter = new PetsAdapter(requireContext(), petsList);
                recyclerView.setAdapter(petsAdapter);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }
        });

        return  view;
    }
}
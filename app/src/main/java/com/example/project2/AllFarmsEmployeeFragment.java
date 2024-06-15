package com.example.project2;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AllFarmsEmployeeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DbFarm db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_farms_employee, container, false);

        // Initialize the DbFarm object
        db = new DbFarm(requireContext());

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview_all_farm);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Fetch and display the farms
        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the list of farms when the fragment is resumed
        refreshList();
    }

    void refreshList() {
        // Fetch the employee ID from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        long employeeId = Long.parseLong(sharedPreferences.getString("employee_id", "-1"));

        // Fetch the list of farms for the employee
        List<Farm> items = db.getFarmsForEmployee(employeeId);

        // Set the adapter for the RecyclerView
        if (items != null && !items.isEmpty()) {
            recyclerView.setAdapter(new MyFarmAdapter(requireContext(), items));
        } else {
            Log.d("AllFarmsEmployeeFragment", "No farms found or items is null");
        }
    }
}

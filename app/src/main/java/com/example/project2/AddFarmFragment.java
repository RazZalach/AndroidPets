package com.example.project2;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddFarmFragment extends Fragment {

    private EditText editTextFarmName, editTextHorseLimit;
    private Button buttonAddFarm;
    private DbFarm dbFarm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_farm, container, false);

        editTextFarmName = view.findViewById(R.id.editTextFarmName);
        editTextHorseLimit = view.findViewById(R.id.editTextHorseLimit);
        buttonAddFarm = view.findViewById(R.id.buttonAddFarm);

        dbFarm = new DbFarm(getContext());

        buttonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFarm();
            }
        });

        return view;
    }

    private void addFarm() {
        String farmName = editTextFarmName.getText().toString().trim();
        String horseLimitStr = editTextHorseLimit.getText().toString().trim();

        if (farmName.isEmpty() || horseLimitStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int horseLimit;
        try {
            horseLimit = Integer.parseInt(horseLimitStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Horse limit must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the logged-in employee's ID from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        long employeeId = Long.parseLong(sharedPreferences.getString("employee_id", "-1"));

        if (employeeId == -1) {
            Toast.makeText(getContext(), "Error: Employee not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a list of random horses
        List<Horse> horsesList = createRandomHorses(horseLimit);

        // Create and save the farm
        Farm farm = new Farm(farmName, horseLimit);
        farm.setHorsesList(horsesList);
        long farmId = dbFarm.createFarm(farm);

        // Assign the farm to the employee
        dbFarm.addFarmForEmployee(employeeId, farmId);

        Toast.makeText(getContext(), "Farm added successfully", Toast.LENGTH_SHORT).show();
        notifyAdapter();
    }

    private void notifyAdapter() {
        // Find the fragment by tag
        Fragment fragment = getParentFragmentManager().findFragmentByTag("AllFarmsEmployeeFragment");
        if (fragment instanceof AllFarmsEmployeeFragment) {
            ((AllFarmsEmployeeFragment) fragment).refreshList();
        }
    }

    public List<Horse> createRandomHorses(int limit) {
        List<Horse> horses = new ArrayList<>();
        Random random = new Random();
        String[] horseNames = {"Shadow", "Spirit", "Thunder", "Blaze", "Storm", "Midnight", "Star", "Angel", "Comet"};

        for (int i = 0; i < limit; i++) {
            String name = horseNames[random.nextInt(horseNames.length)];
            String race = "Race " + (i + 1);
            double age = 10 + (15 - 10) * random.nextDouble(); // random age between 10 and 15
            boolean isHungry = random.nextBoolean();

            horses.add(new Horse(race, name, age, isHungry));
        }

        return horses;
    }
}

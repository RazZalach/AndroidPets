package com.example.project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddPetToApiFragment extends Fragment {

    private EditText editTextType, editTextName, editTextAge, editTextColor, editTextPicUrl, editTextContactPhone;
    private Button buttonAddPet;
    private PetsServiceApi petsServiceApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_pet_to_api, container, false);

        editTextType = view.findViewById(R.id.editTextType);
        editTextName = view.findViewById(R.id.editTextName);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextColor = view.findViewById(R.id.editTextColor);
        editTextPicUrl = view.findViewById(R.id.editTextPicUrl);
        editTextContactPhone = view.findViewById(R.id.editTextContactPhone);
        buttonAddPet = view.findViewById(R.id.buttonAddPet);

        petsServiceApi = new PetsServiceApi(requireContext());

        buttonAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = editTextType.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String ageStr = editTextAge.getText().toString().trim();
                String color = editTextColor.getText().toString().trim();
                String picUrl = editTextPicUrl.getText().toString().trim();
                String contactPhone = editTextContactPhone.getText().toString().trim();

                if (type.isEmpty() || name.isEmpty() || ageStr.isEmpty() || color.isEmpty() ||
                        picUrl.isEmpty() || contactPhone.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                double age = Double.parseDouble(ageStr);
                Pets newPet = new Pets();
                newPet.setType(type);
                newPet.setName(name);
                newPet.setAge(age);
                newPet.setColor(color);
                newPet.setPicUrl(picUrl);
                newPet.setContactPhone(contactPhone);
                newPet.setAdopted(false);

                petsServiceApi.addPet(newPet, new PetsServiceApi.PetAddResponseListener() {
                    @Override
                    public void onResponse(String message) {
                        Toast.makeText(requireContext(), "Pet added successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(requireContext(), "Error adding pet: " + message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return  view;
    }
}
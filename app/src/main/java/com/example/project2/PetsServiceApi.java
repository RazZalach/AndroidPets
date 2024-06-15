package com.example.project2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class PetsServiceApi {

    private static final String BASE_URL = "https://petsapi.onrender.com/pets";
    private RequestQueue requestQueue;
    private Context context;


    public PetsServiceApi(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    // Method to fetch all pets
    public void fetchPets(final PetsResponseListener listener) {
        String url = BASE_URL + "/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Pets> petsList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Pets pet = new Pets();
                                pet.setType(jsonObject.getString("Type"));
                                pet.setName(jsonObject.getString("Name"));
                                pet.setAge(jsonObject.getDouble("Age"));
                                pet.setColor(jsonObject.getString("Color"));
                                pet.setPicUrl(jsonObject.getString("PicUrl"));
                                pet.setContactPhone(jsonObject.getString("ContactPhone"));

                                // Check if "IsAdupted" key exists and handle appropriately
                                if (jsonObject.has("IsAdupted")) {
                                    pet.setAdopted(jsonObject.getBoolean("IsAdupted"));
                                } else {
                                    pet.setAdopted(false); // Default value or handle as needed
                                }

                                petsList.add(pet);
                            }
                            listener.onResponse(petsList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Parsing error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PetsServiceApi", "Error fetching pets", error);
                        listener.onError("Request error");
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    // Method to add a new pet
    public void addPet(Pets newPet, final PetAddResponseListener listener) {
        String url = BASE_URL + "/add";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Type", newPet.getType());
            jsonObject.put("Name", newPet.getName());
            jsonObject.put("Age", newPet.getAge());
            jsonObject.put("Color", newPet.getColor());
            jsonObject.put("PicUrl", newPet.getPicUrl());
            jsonObject.put("ContactPhone", newPet.getContactPhone());
            jsonObject.put("IsAdupted", newPet.isAdopted());
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError("Error creating JSON object");
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            listener.onResponse(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PetsServiceApi", "Error adding pet", error);
                        listener.onError("Request error");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public interface PetAddResponseListener {
        void onResponse(String message);
        void onError(String message);
    }

    public interface PetsResponseListener {
        void onResponse(List<Pets> petsList);
        void onError(String message);
    }
}

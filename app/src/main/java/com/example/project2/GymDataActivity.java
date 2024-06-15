package com.example.project2;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class GymDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterGym adapter;
    private List<Gym> gymList;

    private String url = "https://mystudent-project.onrender.com/gym/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_data);

        recyclerView = findViewById(R.id.recyclerview_gym);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gymList = new ArrayList<>();
        adapter = new AdapterGym(this, gymList);
        recyclerView.setAdapter(adapter);

        fetchData();
    }

    private void fetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("Data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject gym = dataArray.getJSONObject(i);
                                String name = gym.getString("name");
                                String address = gym.getString("address");
                                JSONObject location = gym.getJSONObject("location");
                                JSONArray coordinates = location.getJSONArray("coordinates");
                                double longitude = coordinates.getDouble(0);
                                double latitude = coordinates.getDouble(1);
                                String picUrl = gym.getString("picUrl");

                                Gym gymItem = new Gym(name, address, new Double[]{longitude, latitude}, picUrl);
                                gymList.add(gymItem);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("GymDataActivity", "Failed to parse JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GymDataActivity", "Error: " + error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}

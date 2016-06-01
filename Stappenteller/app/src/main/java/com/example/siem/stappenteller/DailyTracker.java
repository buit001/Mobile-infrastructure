package com.example.siem.stappenteller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DailyTracker extends Activity{

    private ListView listView;

    private List<StapTracker> steps;
    private StepsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tracker);

        steps = new ArrayList<StapTracker>();
        adapter = new StepsAdapter(getApplicationContext(), R.layout.row_steps, steps);

        listView = (ListView) findViewById(R.id.stepList);
        listView.setAdapter(adapter);

        Intent intent = getIntent();

        int stepsNumber = intent.getIntExtra("stepCounter", 0);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        steps.add(new StapTracker(stepsNumber, date));
        steps.add(new StapTracker(5, "25-05-2016"));
        steps.add(new StapTracker(500, "24-05-2016"));

        adapter.notifyDataSetChanged();
        GetStepsList();
    }

    protected void GetStepsList() {
        String url = "http://stappentellerapi3.azurewebsites.net/api/values";


        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject stapObject = response.getJSONObject(i);
                                String aantalStappen = stapObject.getString("aantalStappen");
                                int stappen = Integer.parseInt(aantalStappen);

                                String date = stapObject.getString("Date");
                                steps.add(new StapTracker(stappen, date));
                                Toast.makeText(getApplicationContext(), "Successfuly completed task", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.println(Log.DEBUG, "JSON", error.toString());
                        Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        // Access the RequestQueue through your singleton class.
        Log.println(Log.DEBUG, "RequestLog", jsObjRequest.toString());
        NetworkManager.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

}

package com.example.siem.stappenteller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyTracker extends Activity{

    private ListView listView;

    private List<StapTracker> steps;
    private StepsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tracker);

        steps = new ArrayList<StapTracker>();
        adapter = new StepsAdapter(getApplicationContext(), R.layout.row_steps, steps);

        listView = (ListView) findViewById(R.id.stepList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        listView.setAdapter(adapter);

        Intent intent = getIntent();

        int stepsNumber = intent.getIntExtra("stepCounter", 0);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        //swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        loadChildren();
                                    }
                                }
        );
        loadChildren();

    }


    protected void loadChildren() {
        String url = getString(R.string.API_GetStappen);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            steps.clear();

                            JSONArray jsonArray = response.getJSONArray("Content");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject stepObject = jsonArray.getJSONObject(i);
                                String stappen = stepObject.getString("FirstName");
                                String dag = stepObject.getString("LastName");

                                steps.add(new StapTracker(stappen, dag));
                            }

                            adapter.notifyDataSetChanged();
                            Log.println(Log.DEBUG, "HTTPLOG", response.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.DEBUG, "JSON", error.toString());
                        Toast.makeText(getApplicationContext(), "failed to load steps", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // Access the RequestQueue through your singleton class.
        NetworkManager.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadChildren();
    }


}

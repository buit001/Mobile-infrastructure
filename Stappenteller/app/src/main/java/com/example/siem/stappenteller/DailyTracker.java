package com.example.siem.stappenteller;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
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

        steps.add(new StapTracker(5, "25-05-2016"));

        adapter.notifyDataSetChanged();
    }


}

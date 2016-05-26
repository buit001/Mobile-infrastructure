package com.example.siem.stappenteller;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView stepCounter;
    private Button stepList;
    public boolean step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stepCounter = (TextView) findViewById(R.id.stepCounter);
        stepList = (Button) findViewById(R.id.list);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyTracker.class);
                intent.putExtra("stepCounter", Math.round(Float.parseFloat(stepCounter.getText().toString())));
                startActivity(intent);
            }
        });
    }

    protected void onResume(){
        super.onResume();
        step = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepSensor != null){
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "Step sensor not available", Toast.LENGTH_LONG).show();
        }
    }

    protected void onPause(){
        super.onPause();
        step = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    public void onSensorChanged(SensorEvent event){
        if(step){
            stepCounter.setText(String.valueOf(event.values[0]));
        }
    }
}

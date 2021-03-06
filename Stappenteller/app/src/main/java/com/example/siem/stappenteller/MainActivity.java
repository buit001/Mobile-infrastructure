package com.example.siem.stappenteller;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView stepCounter;
    private Button stepList;
    private Button postSteps;
    public boolean step;
    public String day;
    Map<Object, Object> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stepCounter = (TextView) findViewById(R.id.stepCounter);
        stepList = (Button) findViewById(R.id.list);
        postSteps = (Button) findViewById(R.id.postSteps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        day = df.format(c.getTime());
        Toast.makeText(this, day, Toast.LENGTH_SHORT).show();

        Log.println(Log.DEBUG, "HTTPLOG", day);

        stepList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyTracker.class);
                intent.putExtra("stepCounter", Math.round(Float.parseFloat(stepCounter.getText().toString())));
                startActivity(intent);
            }
        });

        postSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onPost(stepCounter.getText().toString(), day);
                System.out.println(stepCounter.getText().toString());
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

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        day = df.format(c.getTime());
        Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
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

    public Map<Object, Object> getParams() {
        return params;
    }

    protected void onPost(String stappen, String dag) {


            String url = getString(R.string.API_PostStappen);

            params = new HashMap<Object, Object>();
            params.put("AantalStappen", stappen);
            params.put("Dag", dag);

            final JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //  User type is Parent
                            if (response.toString().contains("true")) {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                Log.println(Log.DEBUG, "HTTPLOG", response.toString());
                            }


                            }

            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Could not connect, try again ", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Access the RequestQueue through your singleton class.
            NetworkManager.getInstance(this).addToRequestQueue(jsObjRequest);
    }

}

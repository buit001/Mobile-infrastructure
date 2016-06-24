package com.example.siem.stappenteller;

/**
 * Created by siem on 25-5-2016.
 */
public class StapTracker {
    private String steps;
    private String date;

    public StapTracker(String steps, String date){
        this.steps = steps;
        this.date = date;
    }

    public String getSteps(){
        return steps;
    }

    public void setSteps(String steps){
        this.steps = steps;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}

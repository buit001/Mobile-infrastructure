package com.example.siem.stappenteller;

/**
 * Created by siem on 25-5-2016.
 */
public class StapTracker {
    private int steps;
    private String date;

    public StapTracker(int steps, String date){
        this.steps = steps;
        this.date = date;
    }

    public int getSteps(){
        return steps;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}

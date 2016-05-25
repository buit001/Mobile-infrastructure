package com.example.siem.stappenteller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by siem on 25-5-2016.
 */
public class StepsAdapter extends ArrayAdapter<StapTracker> {

    private LayoutInflater inflater;

    public StepsAdapter(Context context, int resource, List<StapTracker> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(getContext());
    }

    static class ViewHolder {
        TextView steps;
        TextView date;

        public ViewHolder(View view) {
            steps = (TextView) view.findViewById(R.id.steps);
            date = (TextView) view.findViewById(R.id.date);
        }

        //Method to set the values in a row
        public void populateRow(StapTracker stapTracker) {
            steps.setText("Steps: " + stapTracker.getSteps());
            date.setText( stapTracker.getDate());
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout into the View for the Row
        ViewHolder holder;

        //Check if the row is new
        if (convertView == null) {
            //Inflate the layout if it didn't exist yet
            convertView = inflater.inflate(R.layout.row_steps, parent, false);
            //Create a new view holder instance
            holder = new ViewHolder(convertView);
            //set the holder as a tag so we can get it back later
            convertView.setTag(holder);
        } else {
            //The row isn't new so we can reuse the view holder
            holder = (ViewHolder) convertView.getTag();
        }

        //Populate the row
        holder.populateRow(getItem(position));
        return convertView;

    }
}

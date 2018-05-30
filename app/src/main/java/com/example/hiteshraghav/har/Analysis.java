package com.example.hiteshraghav.har;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Analysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        String Userid = "group11";
        String[] activities = { "WALKING", "DOWNSTAIRS", "JOGGING", "UPSTAIRS","SITTING" ,"STANDING"};

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Activity Recognition");
        if (!folder.exists()){
            Toast.makeText(getApplicationContext(), "Activity Recognition folder not found.", Toast.LENGTH_LONG).show();
            return;
        }
        String filename = folder.toString() + "/Log.csv" ;
        Map<String, Integer> timings = new HashMap<>();

        // Initialize each label with 0
        for (String activity : activities) timings.put(activity.toLowerCase(), 0);

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");
                if (tokens[1].trim().equals(Userid)) {
                    tokens[2] = tokens[2].trim().toLowerCase();
                    if (timings.containsKey(tokens[2]))
                        timings.put(tokens[2], timings.get(tokens[2]) + 1);
                    else
                        Log.e("Error", tokens[2] + " key not found");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        int index = 0;

        ArrayList<String> labels = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : timings.entrySet()) {
            Integer value = entry.getValue();
            entries.add(new BarEntry(Float.valueOf(String.valueOf(value)) , index++));
            labels.add(entry.getKey());
            Log.d("Values", entry.getKey() + " : " + value);
        }
        Log.i("DATA", timings.toString() + Arrays.toString(new ArrayList[]{labels}));
        BarDataSet bardataset = new BarDataSet(entries, "Seconds");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.setDescription("Activity Analysis");
    }
}

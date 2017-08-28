package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by USER on 2017-08-14.
 */

public class AnalogActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analog);

        ListView analogListView = (ListView) findViewById(R.id.analogList);
        AnalogActivityListAdapter analogListAdapter = new AnalogActivityListAdapter();
        analogListView.setAdapter(analogListAdapter);
        setListData(analogListAdapter);
    }

    private void setListData(AnalogActivityListAdapter analogListAdapter){
        analogListAdapter.addItem("Acceleration Pedal Position", "0.0", 0);
        analogListAdapter.addItem("Alternator Voltage(V)", "9.5", 9.5/40);
        analogListAdapter.addItem("Arm in Pressure(bar)", "0.0", 0);
        analogListAdapter.addItem("Arm out Pressure(bar)", "0.0", 0);
        analogListAdapter.addItem("Battery Voltage(V)", "28.4", 28.4/40);
        analogListAdapter.addItem("Boom Down Pressure(bar)", "0.0", 0);
        analogListAdapter.addItem("Boom Pressure(bar)", "279.0", 479/500);
    }
}

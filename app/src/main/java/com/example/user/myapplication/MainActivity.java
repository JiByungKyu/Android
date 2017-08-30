package com.example.user.myapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent analogActivity = new Intent(this, AnalogActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.left_drawer);
        final ArrayList<MenuList> mMenu = setExpandableListData();
        ExpandableListViewAdapter expandableListAdapter = new ExpandableListViewAdapter(this, mMenu);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch(groupPosition){
                    //Monitoring
                    case 2:
                        switch (childPosition){
                            //Analog In-Out
                            case 2:
                                startActivity(analogActivity);
                                break;
                        }
                        break;
                }

                return false;
            }
        });

        ListView listView = (ListView) findViewById(R.id.right_drawer);
        GraphListViewAdapter listAdapter = new GraphListViewAdapter();
        listView.setAdapter(listAdapter);
        setListData(listAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_left);
        navigationView.setNavigationItemSelectedListener(this);

        ListView errorList = (ListView) findViewById(R.id.error_listView);
        ErrorListAdapter errorListAdapter = new ErrorListAdapter();
        errorList.setAdapter(errorListAdapter);
        setErrorListData(errorListAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private ArrayList<MenuList> setExpandableListData(){
        MenuList m1 = new MenuList("DX380LC-3");
        MenuList m2 = new MenuList("Home");
        MenuList m3 = new MenuList("Monitoring");
        MenuList m4 = new MenuList("Graph Output");
        MenuList m5 = new MenuList("Operation Hour Information");

        m3.item.add("Basic Information");
        m3.item.add("Digital In-Out");
        m3.item.add("Analog In-Out");

        m5.item.add("Daily Operation Information");
        m5.item.add("Total Operation Information");

        ArrayList<MenuList> allMenu = new ArrayList();
        allMenu.add(m1);        allMenu.add(m2);
        allMenu.add(m3);        allMenu.add(m4);
        allMenu.add(m5);

        return allMenu;
    }

    private void setListData(GraphListViewAdapter listAdapter) {
        listAdapter.addItem("2017.08.21", 2);
        listAdapter.addItem("2017 08 21 / 15:57:10", "DX380LC-3", "000000");
        listAdapter.addItem("2017 08 21 / 15:57:10", "DX380LC-3", "000000");

        listAdapter.addItem("2017.08.22", 2);
        listAdapter.addItem("2017 08 22 / 15:57:10", "DX380LC-3", "000000");
        listAdapter.addItem("2017 08 22 / 15:57:10", "DX380LC-3", "000000");

        listAdapter.addItem("2017.08.22", 2);
        listAdapter.addItem("2017 08 22 / 15:57:10", "DX380LC-3", "000000");
        listAdapter.addItem("2017 08 22 / 15:57:10", "DX380LC-3", "000000");

        listAdapter.addItem("2017.08.23", 2);
        listAdapter.addItem("2017 08 23 / 15:57:10", "DX140W-ACE", "000000");
        listAdapter.addItem("2017 08 23 / 17:40:10", "DX140W-ACE", "000000");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       // if (id == R.id.nav_home) {
            // Handle the camera action
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        /*switch(v.getId()){
            case R.id:
                Intent intent = new Intent(getApplicationContext(), AnalogActivity.class);
                startActivity(intent);
                break;
        }*/
    }

    public void setErrorListData(ErrorListAdapter errorListAdapter){
        errorListAdapter.addItem("V000201", "GUAGE PANEL ERROR, Failure mode not identifiable");
        errorListAdapter.addItem("V000202", "E-ECU ERROR, Failure mode not Identifiable");
        errorListAdapter.addItem("V000210", "PUMP P/V (A),Current below normal");
    }
}
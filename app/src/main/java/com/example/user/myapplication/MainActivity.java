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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.left_drawer);
        final ArrayList<Menu> mMenu = setExpandableListData();
        ExpandableListViewAdapter expandableListAdapter = new ExpandableListViewAdapter(this, mMenu);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        ListView listView = (ListView) findViewById(R.id.right_drawer);
        ListViewAdapter listAdapter = new ListViewAdapter();
        listView.setAdapter(listAdapter);
        setListData(listAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_left);
        navigationView.setNavigationItemSelectedListener(this);
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

    private ArrayList<Menu> setExpandableListData(){
        Menu m1 = new Menu("Home");
        Menu m2 = new Menu("Monitoring");
        m2.item.add("Basic Information");
        m2.item.add("Digital In-Out");
        m2.item.add("Analog In-Out");
        Menu m3 = new Menu("Graph Output");
        Menu m4 = new Menu("Operation Hour Information");
        m4.item.add("Daily Operation Information");
        m4.item.add("Total Operation Information");

        ArrayList<Menu> allMenu = new ArrayList();
        allMenu.add(m1);        allMenu.add(m2);
        allMenu.add(m3);        allMenu.add(m4);

        return allMenu;
    }

    private void setListData(ListViewAdapter listAdapter) {
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.monitoring_AnalogIO:
                Intent intent = new Intent(getApplicationContext(), AnalogActivity.class);
                startActivity(intent);
                break;
        }
    }
}
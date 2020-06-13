package com.example.chamikanandasiri.gpacal;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class PointsActivity extends AppCompatActivity {

    private EditText GPAEdiText;
    private Spinner semesterSpinner;
    private Button saveButton,plotButton;
    private TableLayout Table;
    private ArrayList semester_List,GPA_List;
    private String x;
    private float y;
    private  DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        handleWidgets();
        assignInputs();
        navigation();
    }

    private void navigation() {

        android.support.v7.widget.Toolbar navigation_toolBar = findViewById(R.id.input_toolbar);
        setSupportActionBar(navigation_toolBar);

        drawer = findViewById(R.id.input_drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,navigation_toolBar,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void assignInputs() {
        semester_List = new ArrayList<Integer>();
        GPA_List = new ArrayList<Float>();
        try{
            x = GPAEdiText.getText().toString();
            y = Float.valueOf(x);
            GPA_List.add(y);
        }
        catch (Exception error){
            Toast.makeText(PointsActivity.this,"You have to enter GPA",Toast.LENGTH_SHORT).show();
        }
    }

    private void handleWidgets() {

        GPAEdiText = findViewById(R.id.points_editText);
        semesterSpinner = findViewById(R.id.points_semesterSpinner);
        saveButton = findViewById(R.id.points_saveButton);
        plotButton = findViewById(R.id.points_plotButton);
        Table = findViewById(R.id.points_TableLayout);

        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(PointsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Semesters));
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);

        }
}

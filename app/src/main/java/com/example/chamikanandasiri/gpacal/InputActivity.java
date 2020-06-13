package com.example.chamikanandasiri.gpacal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class InputActivity extends AppCompatActivity {

    //defining variables for widgets
    private EditText SemesterEditText,no_of_ModulesEditText;
    private Button SaveButton,OKButton,CalculateButton,EraseButton;
    private Spinner CreditSpinner,GradeSpinner;
    private int semester , no_of_modules;
    private ArrayList<String> Credit_input_list,Grade_input_list;
    private HashMap<String,Float> GradeConversion;
    private DrawerLayout drawer;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        handleWidgets();
        disableButtons();
        assignInputs();
        //testGoToPoints();
        EraseLastEntry();
        CalculateGPA();
        navigation();

    }

    private void testGoToPoints() {
        EraseButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent GPAOutput1 = new Intent(InputActivity.this,PointsActivity.class);
                        startActivity(GPAOutput1);
                    }
                }
        );

    }

    public void navigation() {

        android.support.v7.widget.Toolbar navigation_toolBar = findViewById(R.id.input_toolbar);
        setSupportActionBar(navigation_toolBar);

        drawer =findViewById(R.id.input_drawerLayout);

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

    private void handleWidgets() {
        SemesterEditText = findViewById(R.id.input_editText1);
        no_of_ModulesEditText = findViewById(R.id.input_editText2);
        SaveButton = findViewById(R.id.input_button1);
        OKButton = findViewById(R.id.input_button2);
        CalculateButton = findViewById(R.id.input_button3);
        EraseButton = findViewById(R.id.input_button4);
        CreditSpinner = findViewById(R.id.input_spinner1);
        GradeSpinner = findViewById(R.id.input_spinner2);
        GradeConversion = new HashMap<>();

        GradeConversion.put("A+",(float) 4.2);
        GradeConversion.put("A",(float) 4.0);
        GradeConversion.put("A-",(float) 3.7);
        GradeConversion.put("B+",(float) 3.3);
        GradeConversion.put("B",(float) 3.0);
        GradeConversion.put("B-",(float) 2.7);
        GradeConversion.put("C+",(float) 2.3);
        GradeConversion.put("C",(float) 2.0);
        GradeConversion.put("C-",(float) 1.5);
        GradeConversion.put("D",(float) 1.0);

        ArrayAdapter<String> CreditAdapter = new ArrayAdapter<>(InputActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Credits));
        CreditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CreditSpinner.setAdapter(CreditAdapter);

        ArrayAdapter<String> GradeAdapter = new ArrayAdapter<>(InputActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Grades));
        GradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        GradeSpinner.setAdapter(GradeAdapter);

    }



    private void assignInputs() {
        OKButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        try {
                            semester = Integer.parseInt(SemesterEditText.getText().toString());
                            no_of_modules = Integer.parseInt(no_of_ModulesEditText.getText().toString());
                            Credit_input_list = new ArrayList<>();
                            Grade_input_list = new ArrayList<>();
                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                                    InputMethodManager.RESULT_UNCHANGED_SHOWN);

                            SaveButton.setEnabled(true);
                            saveValues();
                            Toast.makeText(InputActivity.this,"Finish", Toast.LENGTH_LONG).show();


                        }
                        catch (Exception errors){
                            Toast.makeText(InputActivity.this,"You have to Enter integers", Toast.LENGTH_LONG).show();
                        }

                    }






                }
        );

    }

    private void saveValues() {
        SaveButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Credit = CreditSpinner.getSelectedItem().toString();
                        String Grade = GradeSpinner.getSelectedItem().toString();
                        EraseButton.setEnabled(true);

                        if (Credit_input_list.size()< no_of_modules){

                            if (Credit_input_list.size() == no_of_modules-1) {
                                SaveButton.setEnabled(false);
                                CalculateButton.setEnabled(true);
                            }
                            Credit_input_list.add(Credit);
                            Grade_input_list.add(Grade);
                            Toast.makeText(InputActivity.this,Grade+" for a "+Credit+" Credit Module", Toast.LENGTH_SHORT).show();
                        }

                       else {
                            SaveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            CalculateButton.setEnabled(true);
                            Toast.makeText(InputActivity.this,"You have entered enough inputs", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

    private void CalculateGPA(){
        CalculateButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(no_of_modules == Credit_input_list.size()){
                        //Algorithm to calculate the GPA
                        float sum = 0,credit_sum = 0, product,GPA ;

                        for (int i = 0; i <no_of_modules ; i++) {

                            product = (Float.valueOf(Credit_input_list.get(i)))*(GradeConversion.get(Grade_input_list.get(i)));
                            credit_sum = credit_sum + (Float.valueOf(Credit_input_list.get(i)));
                            sum = sum + product;
                        }

                        GPA = sum/credit_sum;

                        //Toast.makeText(InputActivity.this,Float.toString(GPA), Toast.LENGTH_LONG).show();

                        //Move to Results Activity
                        Intent GPAOutput = new Intent(InputActivity.this,ResultsActivity.class);
                        GPAOutput.putExtra("calculated_GPA",GPA);
                        GPAOutput.putExtra("Credit_list", Credit_input_list);
                        GPAOutput.putExtra("Grade_list", Grade_input_list);
                        GPAOutput.putExtra("Semester", semester);
                        startActivity(GPAOutput);
                        }
                    }
                }
        );
    }

    private void disableButtons() {

        SaveButton.setEnabled(false);
        EraseButton.setEnabled(false);
        CalculateButton.setEnabled(false);

    }

    private void EraseLastEntry() {
        EraseButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Credit_input_list.size() > 0){
                            Credit_input_list.remove(Credit_input_list.size()-1);
                            Grade_input_list.remove(Grade_input_list.size()-1);
                            SaveButton.setEnabled(true);
                            saveValues();
                            Toast.makeText(InputActivity.this, Grade_input_list.get(Grade_input_list.size()-1)
                                    +" for "+Credit_input_list.get(Credit_input_list.size()-1)+ " credit subject ERASED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
}

package com.example.chamikanandasiri.gpacal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private Button BackButton;
    private int semester;
    private Float calculated_GPA;
    private ArrayList<String> Credit_input_list, Grade_input_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        IntentValues();
        GivingOutput();
        BackButton();
        TableValues();

    }


    private void IntentValues() {

        //getting values packed with intent
        calculated_GPA = getIntent().getFloatExtra("calculated_GPA", (float) 0.12);
        Credit_input_list = getIntent().getStringArrayListExtra("Credit_list");
        Grade_input_list = getIntent().getStringArrayListExtra("Grade_list");
        semester = getIntent().getIntExtra("Semester", 1);

    }

    @SuppressLint("SetTextI18n")
    private void GivingOutput() {

        //assigning widgets and output
        TextView GPAValue = findViewById(R.id.results_textView2);
        TextView resultText = findViewById(R.id.results_textView1);
        BackButton = findViewById(R.id.results_button);

        //writing output
        resultText.setText(" Your Semester GPA for semester " + semester + " is ");
        GPAValue.setText(Float.toString(calculated_GPA));
    }

    private void BackButton() {


        BackButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent BackButton = new Intent(ResultsActivity.this, InputActivity.class);
                        startActivity(BackButton);
                    }
                }
        );


    }

    private void TableValues() {

        TableLayout table = findViewById(R.id.tableLayout);
        table.setGravity(Gravity.CENTER);

        TableRow table_row = new TableRow(this);
        TextView TXT1 = new TextView(this);
        TextView TXT2 = new TextView(this);
        TXT1.setText("CREDIT");
        TXT1.setGravity(Gravity.CENTER_HORIZONTAL);
        TXT1.setTextSize(25);
        TXT1.setTextColor(Color.argb(255, 0, 0, 0));
        TXT2.setText("GRADE");
        TXT2.setGravity(Gravity.CENTER_HORIZONTAL);
        TXT2.setTextSize(25);
        TXT2.setTextColor(Color.argb(255, 0, 0, 0));
        table_row.addView(TXT1);
        table_row.addView(TXT2);
        table.addView(table_row);

        for (int i = 0; i < Credit_input_list.size(); i++) {

            table_row = new TableRow(this);
            TXT1 = new TextView(this);
            TXT2 = new TextView(this);
            TXT1.setText(Credit_input_list.get(i));
            TXT1.setGravity(Gravity.CENTER_HORIZONTAL);
            TXT1.setTextSize(20);
            TXT2.setText(Grade_input_list.get(i));
            TXT2.setGravity(Gravity.CENTER_HORIZONTAL);
            TXT2.setTextSize(20);
            table_row.addView(TXT1);
            table_row.addView(TXT2);
            table.addView(table_row);

        }
    }
}
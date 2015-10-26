package com.kanakb.healthtracker;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class Landing extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button bmiButton;
    private EditText height;
    private EditText weight;
    private EditText age;
    private Spinner gender;
    private TextView calculatedBmi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        bmiButton=(Button)findViewById(R.id.button5);
        height=(EditText)findViewById(R.id.editText8);
        weight=(EditText)findViewById(R.id.editText9);
        age=(EditText)findViewById(R.id.editText10);
        gender=(Spinner)findViewById(R.id.spinner);
        calculatedBmi=(TextView)findViewById(R.id.textView10);



        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        bmiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Double bmi=calorieCounter(Double.parseDouble(weight.getText().toString())*0.45,Double.parseDouble(height.getText().toString())*30.48,Integer.parseInt(age.getText().toString()),gender.getSelectedItem().toString());
                calculatedBmi.setText(bmi.toString());
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Double calorieCounter(double weight,double height,int age,String gender){
            Double  result=null;
        try {

            if ("Male".equals(gender)){
                Calculable calc = new ExpressionBuilder("10 * "+weight + "+ 6.25 *"+ height+" - 5 * "+age +"+5").build();
                 result = calc.calculate();
            }

            if("Female".equals(gender)){
                Calculable calc = new ExpressionBuilder("10 * "+weight + "+6.25 *"+ height+" - 5 * "+age+"-161").build();
                 result = calc.calculate();

            }




        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return result;
    }




}
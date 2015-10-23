package com.kanakb.healthtracker;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class Landing extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Double calorieCounter(double weight,double height,int age,String gender){
            Double  result=null;
        try {

            if ("male".equals(gender)){
                Calculable calc = new ExpressionBuilder("10 * "+weight + "+ 6.25 *"+ height+" - 5 * "+age +"+5").build();
                 result = calc.calculate();
            }

            if("female".equals(gender)){
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
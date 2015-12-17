package com.adinaandsari.virtuallibrary;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import entities.Book;
import entities.Category;
import model.backend.Backend;

public class customer extends AppCompatActivity {
    private Spinner spinner;
    private String[] categoryList;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //the category spinner
        spinner = (Spinner) findViewById(R.id.category_spinner_customer);
        categoryList = getResources().getStringArray(R.array.category_array);
        dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categoryList);
        spinner.setAdapter(dataAdapter);

        spinner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Spinner spinnerCategory = (Spinner)findViewById(R.id.category_spinner_customer);
                String category = spinnerCategory.getSelectedItem().toString();

                Intent intent = new Intent(customer.this, BookListByCategory.class);
                intent.putExtra("Category from customer activity", category);
                startActivity(intent);
            }
        });
    }

}

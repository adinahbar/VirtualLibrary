package com.adinaandsari.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import entities.Category;
import entities.Gender;
import entities.Language;
import entities.Status;
import entities.Supplier;

public class addBook extends AppCompatActivity {

    //this user
    Intent preIntent = getIntent();
    Supplier user = (Supplier) preIntent.getSerializableExtra(ConstValue.SUPPLIER_KEY);

    private Spinner categorySpinner , languageSpinner;
    EditText editTextOfName,editTextOfAuthor,editTextOfPublisher,editTextOfSummary,editTextOfDatePublished;
    private String name ,author , publisher ,summary;
    private Language language;
    private Category category;
    private Date datePublished;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //find all the viewers
        findView();

        //the category spinner
        String[] categoryList = getResources().getStringArray(R.array.category_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categoryList);
        categorySpinner.setAdapter(dataAdapter);

        //the category spinner
        String[] LanguageList = getResources().getStringArray(R.array.language_array);
        ArrayAdapter<String> languageSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,LanguageList);
        languageSpinner.setAdapter(languageSpinnerAdapter);

    }

    //function to find view by id for the view in the activity
    void findView()
    {
        categorySpinner = (Spinner)findViewById(R.id.category_spinner_add_book);
        languageSpinner = (Spinner)findViewById(R.id.language_spinner_add_book);
        editTextOfAuthor = (EditText)findViewById(R.id.author_edit_text_add_book);
        editTextOfPublisher = (EditText)findViewById(R.id.publisher_book_edit_text_add_book);
        editTextOfSummary = (EditText)findViewById(R.id.summary_edit_text_add_book);
        editTextOfDatePublished = (EditText)findViewById(R.id.published_date_edit_text_add_book);
        editTextOfName = (EditText)findViewById(R.id.book_name_edit_text_add_book);
        addButton = (Button)findViewById(R.id.add_book_button);
    }

}

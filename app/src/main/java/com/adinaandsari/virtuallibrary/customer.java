package com.adinaandsari.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import entities.Book;
import entities.Customer;
import entities.Supplier;
import model.backend.Backend;

public class customer extends AppCompatActivity {
    private Spinner categorySpinner,authorSpinner,supplierSpinner;
    private TextView bookOfTheStoreText;
    Button shopBookOfTheStoreButton;
    //this customer
    Intent preIntent = getIntent();
    Customer user = (Customer) preIntent.getSerializableExtra(ConstValue.CUSTOMER_KEY);
    private Backend backendFactory = model.datasource.BackendFactory.getInstance();
    Book bookOfTheStore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //the book of the store
        try {
            bookOfTheStore = backendFactory.bookOfTheStore();
            bookOfTheStoreText = (TextView) findViewById(R.id.book_of_the_store_textView);
            bookOfTheStoreText.setText(bookOfTheStore.getBookName());
        } catch (Exception e) {
            bookOfTheStoreText.setText("There are no best seller book to watch");
        }
        shopBookOfTheStoreButton = (Button)findViewById(R.id.shop_book_of_the_store_button_customer);
        shopBookOfTheStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookOfTheStore != null)
                {
                    //go to the order activity for this book and customer
                    Intent intent = new Intent(customer.this, addOrder.class);
                    intent.putExtra(ConstValue.BOOK_KEY, bookOfTheStore);//add the book
                    intent.putExtra(ConstValue.CUSTOMER_KEY, user);//add the customer
                    startActivity(intent);
                }
                Toast.makeText(customer.this, "There are no book of the store for now", Toast.LENGTH_LONG).show();
            }
        });

        //the category spinner
        categorySpinner = (Spinner) findViewById(R.id.category_spinner_customer);
        String[] categoryList = getResources().getStringArray(R.array.category_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categoryList);
        categorySpinner.setAdapter(dataAdapter);

        //the author spinner
        String[] authorList;
        authorSpinner = (Spinner) findViewById(R.id.author_spinner_customer);
        try {
            //get the author name and put them in an array
            ArrayList<String> authorListName = backendFactory.authorList();
            authorList = new String[authorListName.size()];
            int j = 0;
            for (String n : authorListName)
            {
                authorList[j] = n;
                j++;
            }
        }catch (Exception e)//there are no book
        {
            authorList = new String[1];
            authorList[0] = "";
            Toast.makeText(customer.this, "There are no book for now", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> authorDataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, authorList);
        authorSpinner.setAdapter(authorDataAdapter);

        //the supplier spinner
        String[] supplierList;
        supplierSpinner = (Spinner) findViewById(R.id.shop_by_supplier_spinner_customer);
        try{
            //take the name foreach supplier
            ArrayList<Supplier> supplierArrayList = backendFactory.getSupplierList();
            supplierList = new String[supplierArrayList.size()];
            int i = 0;
            for (Supplier s:supplierArrayList){
                supplierList[i] = s.getName();
                i++;}
        } catch (Exception e)//there are no supplier
        {
            supplierList = new String[1];
            supplierList[0] = "";
            Toast.makeText(customer.this, "There are no supplier for now", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> supplierDataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, supplierList);
        supplierSpinner.setAdapter(supplierDataAdapter);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) findViewById(R.id.category_spinner_customer);
                String category = spinner.getSelectedItem().toString();
                Intent intent = new Intent(customer.this, BookListByCategory.class);
                intent.putExtra("Category from customer activity", category);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        authorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) findViewById(R.id.author_spinner_customer);
                String name = spinner.getSelectedItem().toString();
                Intent intent = new Intent(customer.this, BookListByAuthor.class);
                intent.putExtra("authorName", name);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) findViewById(R.id.shop_by_supplier_spinner_customer);
                String name = spinner.getSelectedItem().toString();
                Intent intent = new Intent(customer.this, BookListBySupplier.class);
                intent.putExtra("supplierName", name);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}

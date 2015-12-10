package com.adinaandsari.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import entities.Customer;
import entities.Manager;
import entities.Privilege;
import entities.Supplier;
import model.backend.Backend;
import model.datasource.BackendFactory;

public class log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.privilege_spinner);
        String[] privilegeList = getResources().getStringArray(R.array.privilege_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,privilegeList);
        spinner.setAdapter(dataAdapter);

        //log in button
        Button button = (Button)findViewById(R.id.logInButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the values
                EditText editTextOfID = (EditText)findViewById(R.id.enter_id);
                long id = Long.parseLong(editTextOfID.getText().toString());
                Spinner spinnerPrivilege = (Spinner)findViewById(R.id.privilege_spinner);
                String privilege = spinnerPrivilege.getSelectedItem().toString();
                Backend backendFactory = model.datasource.BackendFactory.getInstance();
                boolean found = false;
                //try to find the person and open the appropriate activity
                try {
                switch (privilege) {
                    case "customer":
                        ArrayList<Customer> customerArrayList = backendFactory.getCustomerList(Privilege.MANAGER);
                        for (Customer c :customerArrayList){
                            if (c.getNumID()==id)
                            {
                                found = true;
                            }
                        }
                        if (found == true)
                        {
                            Intent intent = new Intent(log_in.this, customer.class);
                            startActivity(intent);
                        }
                        else
                            throw new Exception("ERROR - The ID you entered doesn't match any existing customer");
                            break;
                    case "manager":
                        Manager manager = backendFactory.getManger();
                        if (manager.getNumID() == id)
                            found = true;
                        if (found == true)
                        {
                            Intent intent = new Intent(log_in.this, manger.class);
                            startActivity(intent);
                        }
                        else
                            throw new Exception("ERROR - The ID you entered doesn't match any existing manger");
                        break;
                    case "supplier":
                        ArrayList<Supplier> supplierArrayList = backendFactory.getSupplierList();
                        for (Supplier s :supplierArrayList){
                            if (s.getNumID()==id)
                            {
                                found = true;
                            }
                        }
                        if (found == true)
                        {
                            Intent intent = new Intent(log_in.this, supplier.class);
                            startActivity(intent);
                        }
                        else
                            throw new Exception("ERROR - The ID you entered doesn't match any existing supplier");
                        break;
                }}
                catch (Exception e)
                {
                    //print the exception in a toast view
                    Toast.makeText(log_in.this,"Failed to log in:\n" + e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

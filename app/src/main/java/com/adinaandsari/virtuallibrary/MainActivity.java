package com.adinaandsari.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

import entities.Customer;
import entities.Gender;
import entities.Privilege;
import entities.Status;
import model.backend.Backend;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            Backend backendFactory = model.datasource.BackendFactory.getInstance();
            backendFactory.addCustomer(new Customer(123456789, "", "", "", "", Gender.FEMALE,
                    new Date(1994, 8, 5), "", Status.DIVORCED), Privilege.MANAGER);
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, "Failed to sign in:\n" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        }
        //log in button
        Button logButton = (Button)findViewById(R.id.logInButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, log_in.class);
                startActivity(intent);
            }
        });

        //sign in button
        Button signButton = (Button)findViewById(R.id.singInButton);
        signButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void  onClick(View v){
                Intent intent = new Intent(MainActivity.this, add_customer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}

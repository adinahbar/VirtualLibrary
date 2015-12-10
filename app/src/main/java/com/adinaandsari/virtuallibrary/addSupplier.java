package com.adinaandsari.virtuallibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class addSupplier extends AppCompatActivity {

    private CheckBox male,female;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //spinner for the type of the supplier
        spinner = (Spinner) findViewById(R.id.type_spinner_add_supplier);
        String[] typeList = getResources().getStringArray(R.array.supplier_type_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,typeList);
        spinner.setAdapter(dataAdapter);

    }

    //making sure that only one checkbox can be checked
    public void onCheckBoxClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.male_checkBox_add_customer:
                female.setChecked(false);
                break;
            case R.id.female_checkBox_add_customer:
                male.setChecked(false);
        }
    }

}

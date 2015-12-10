package com.adinaandsari.virtuallibrary;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import entities.Customer;
import entities.Gender;
import entities.Manager;
import entities.Privilege;
import entities.Status;
import model.backend.Backend;
import model.datasource.BackendFactory;

public class UpdateCustomer extends AppCompatActivity {

    private long id;
    private String name ,address , phoneNumber , email;
    private Status status;
    private Gender gender;
    private Date birthday;
    private Spinner idSpinner , statusSpinner;
    EditText editTextOfName,editTextOfAddress,editTextOfPhoneNumber,editTextOfEmail,editTextOfBirthday,editTextOfCreditCard;
    CheckBox vipCheckBox;
    private CheckBox male,female;
    Button updateButton;

    //function to find view by id for the view in the activity
    void findView()
    {
        editTextOfName = (EditText)findViewById(R.id.name_edit_text_update_customer);
        editTextOfAddress = (EditText)findViewById(R.id.address_edit_text_update_customer);
        editTextOfPhoneNumber = (EditText)findViewById(R.id.phone_number_edit_text_update_customer);
        editTextOfEmail = (EditText)findViewById(R.id.email_edit_text_update_customer);
        editTextOfBirthday = (EditText)findViewById(R.id.birthday_edit_text_update_customer);
        editTextOfCreditCard = (EditText)findViewById(R.id.numOfCreditCard_edit_text_update_customer);
        vipCheckBox = (CheckBox)findViewById(R.id.vip_checkBox_update_customer);
        idSpinner = (Spinner)findViewById(R.id.id_spinner_update_customer);
        statusSpinner = (Spinner)findViewById(R.id.status_spinner_update_customer);
        male = (CheckBox) findViewById(R.id.male_checkBox_update_customer);
        female = (CheckBox) findViewById(R.id.female_checkBox_update_customer);
        updateButton = (Button)findViewById(R.id.update_customer_button_update_customer);
    }

    //this manager
    Intent preIntent = getIntent();
    Manager user = (Manager) preIntent.getSerializableExtra(ConstValue.MANAGER_KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findView();

        //spinner for the status of the customer
        String[] statusList = getResources().getStringArray(R.array.status_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,statusList);
        statusSpinner.setAdapter(dataAdapter);

        //try to load the data of the selected customer
        try {
            enterDetail();
        }
        catch (Exception e)
        {
            Toast.makeText(UpdateCustomer.this,e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        //update button
        /*
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //get the values and validation check of input
                    EditText editTextOfID = (EditText) findViewById(R.id.id_edit_text_add_customer);
                    id = Long.parseLong(editTextOfID.getText().toString());
                    if ((int) (Math.log10(id) + 1) != 9)
                        throw new Exception("ERROR: ID must contain 9 digits");
                    EditText editTextOfName = (EditText) findViewById(R.id.name_edit_text_add_customer);
                    name = editTextOfName.getText().toString();
                    EditText editTextOfAddress = (EditText) findViewById(R.id.address_edit_text_add_customer);
                    address = editTextOfAddress.getText().toString();
                    EditText editTextOfPhoneNumber = (EditText) findViewById(R.id.phon_number_edit_text_add_customer);
                    phoneNumber = editTextOfPhoneNumber.getText().toString();
                    if (phoneNumber.contains("[a-zA-Z]+") != false)
                        throw new Exception("ERROR: Phone number must contain only digits");
                    EditText editTextOfEmail = (EditText) findViewById(R.id.email_edit_text_add_customer);
                    email = editTextOfEmail.getText().toString();
                    if (email.contains("@") == false) {
                        throw new Exception("ERROR: your email address is wrong");
                    }
                    Spinner spinnerStatus = (Spinner) findViewById(R.id.status_spinner_add_customer);
                    status = Status.valueOf(spinnerStatus.getSelectedItem().toString().toUpperCase(Locale.ENGLISH));
                    if (((CheckBox) findViewById(R.id.male_checkBox_add_customer)).isChecked()) {
                        gender = Gender.MALE;
                    } else if (((CheckBox) findViewById(R.id.female_checkBox_add_customer)).isChecked()) {
                        gender = Gender.FEMALE;
                    } else
                        throw new Exception("ERROR: you didn't checked any checkbox ");
                    EditText editTextOfBirthday = (EditText) findViewById(R.id.birthdat_edit_text_add_customer);
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    birthday = df.parse(editTextOfBirthday.getText().toString());
                    Calendar now = Calendar.getInstance();
                    Calendar birthdayDate = Calendar.getInstance();
                    birthdayDate.setTime(birthday);

                    if (birthdayDate.before(now) != true) {
                        throw new Exception("ERROR: your birthday isn't correct");
                    }

                    //try to add a new customer
                    Backend backendFactory = model.datasource.BackendFactory.getInstance();
                    backendFactory.addCustomer(new Customer(id, name, address, phoneNumber, email, gender,
                            birthday, "", status), Privilege.MANAGER);
                    Toast.makeText(add_customer.this, "Sign in has been successful!\nA mail will be sent to you shortly", Toast.LENGTH_LONG).show();

                    //sent an email
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, email);
                    i.putExtra(Intent.EXTRA_SUBJECT, "Success sign in to our virtual book store");
                    i.putExtra(Intent.EXTRA_TEXT, "Congratulation! you entered our costumer club.\nyou're welcome to start shopping :)");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(add_customer.this, "There are no email clients installed\nThe mail won't be sent to you", Toast.LENGTH_LONG).show();
                    }

                    //go to the next activity
                    //go to the customer activity
                    Intent intent = new Intent(add_customer.this, customer.class);
                    startActivity(intent);

                } catch (Exception e) {
                    //print the exception in a toast view
                    Toast.makeText(add_customer.this, "Failed to sign in:\n" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        */
    }

    void enterDetail() throws Exception
    {
        //spinner for the id of the customer
        String[] customersID = getCustomerID();
        ArrayAdapter<String> dataIDAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,customersID);
        idSpinner.setAdapter(dataIDAdapter);
        idSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Customer customer = BackendFactory.getInstance().findCustomerByID(Long.parseLong(idSpinner.getSelectedItem().toString()));
                    editTextOfName.setText(customer.getName());
                    editTextOfAddress.setText(customer.getAddress());
                    editTextOfPhoneNumber.setText(customer.getPhoneNumber());
                    editTextOfEmail.setText(customer.getEmailAddress());
                    editTextOfBirthday.setText(customer.getBirthDay().toString());
                    editTextOfCreditCard.setText(customer.getNumOfCreditCard());
                    vipCheckBox.setChecked(customer.isVIP());
                    switch (customer.getGender())
                    {
                        case MALE:
                            male.setChecked(true);
                            break;
                        case FEMALE:
                            female.setChecked(true);
                            break;
                    }
                    String statusString = customer.getStatus().toString().toLowerCase(Locale.ENGLISH);
                    statusSpinner.setSelection(((ArrayAdapter<String>)statusSpinner.getAdapter()).getPosition(statusString));
                }
                catch (Exception e)
                {
                //print the exception in a toast view
                Toast.makeText(UpdateCustomer.this,e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
            }
        });

    }

    //function to return all the customers id
    String[] getCustomerID ()throws Exception
    {
        ArrayList<Customer> customerArrayList = BackendFactory.getInstance().getCustomerList(user.getPrivilege());
        String[] id = new String[ customerArrayList.size()] ;
        int i = 0;
        for (Customer c : customerArrayList)
        {
            id[i] =((Long)c.getNumID()).toString();
            i++;
        }
        return id;
    }

    //making sure that only one checkbox can be checked
    public void onCheckBoxClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.male_checkBox_update_customer:
                female.setChecked(false);
                break;
            case R.id.female_checkBox_update_customer:
                male.setChecked(false);
        }
    }

}

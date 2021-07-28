package com.example.broadcastreceiverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //references to buttons and other controls on the layout
    private Button btn_create;
    private Spinner spinnerAge,spinnerGender;
    private EditText cust_email,cust_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        btn_create = findViewById(R.id.btn_create);
        cust_email = findViewById(R.id.db2_email);
        cust_password = findViewById(R.id.db2_password);

        //Create the spinners for age and gender selection
        spinnerAge = findViewById(R.id.spinnerAge);
        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);
        spinnerAge.setOnItemSelectedListener(this);

        spinnerGender = findViewById(R.id.spinnerSex);
        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,R.array.sex,android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);
        spinnerGender.setOnItemSelectedListener(this);

        //button listeners for the register button
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel = new CustomerModel(-1,cust_email.getText().toString(),cust_password.getText().toString(),spinnerAge.getSelectedItem().toString(),spinnerGender.getSelectedItem().toString());
                Toast.makeText(Activity2.this,customerModel.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //We create a back button for second activity and through androidManifest we declare mainActivity as the parent of the second activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
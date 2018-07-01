package com.example.jola.journalapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity{

    EditText email;
    EditText password;
    Button register;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void RegisterUser(){
        String passwordText = password.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        if(!(TextUtils.isEmpty(passwordText) && TextUtils.isEmpty(emailText)) ){

        }
        else {
            Toast.makeText(this,"Username/Password is invalid",Toast.LENGTH_LONG).show();
        }
    }

}

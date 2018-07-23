package com.example.jola.journalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity{

    EditText email;
    EditText password;
    Button register;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
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
            public void onClick(View view) {
                if(view == register){
                    RegisterUser();
                }
            }
        });
    }
    private void RegisterUser(){
        String passwordText = password.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        if(!(TextUtils.isEmpty(passwordText) && TextUtils.isEmpty(emailText)) ){
            progressDialog.setMessage("Registering User...");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this,
                                "Registered Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this,
                                "Could not Register",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this,"Username/Password is invalid",Toast.LENGTH_LONG).show();
        }
    }

}

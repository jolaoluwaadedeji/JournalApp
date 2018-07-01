package com.example.jola.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryActivity extends AppCompatActivity {
    EditText title;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        title = (EditText) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final Date date = new Date();
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseUtil().getDb(getApplicationContext()).userDao()
                        .insertAll(new Entry(title.getText().toString(),
                                content.getText().toString(),formatter.format(date)));
                startActivity(new Intent(EntryActivity.this,ViewEntriesActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.signout,menu);
        return  true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.sign_out:
                //signOut();
                return  true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}

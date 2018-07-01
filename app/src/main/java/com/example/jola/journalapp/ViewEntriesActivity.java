package com.example.jola.journalapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Entry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        DatabaseUtil databaseUtil = new DatabaseUtil();
        entries = databaseUtil.getDb(getApplicationContext()).userDao().getAllEntries();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(entries);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEntriesActivity.this, EntryActivity.class);
                intent.putExtra("key", "Started From The ViewEntriesActivity"); //Optional parameters
                ViewEntriesActivity.this.startActivity(intent);
            }
        });
    }

}

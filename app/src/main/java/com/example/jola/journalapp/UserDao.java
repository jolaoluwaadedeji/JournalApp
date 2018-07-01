package com.example.jola.journalapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jola.journalapp.Entry;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Entry")
    List<Entry> getAllEntries();

    @Insert
    void insertAll(Entry... entries);
}

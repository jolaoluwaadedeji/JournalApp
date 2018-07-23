package com.example.jola.journalapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android .arch.persistence.room.Delete;

import com.example.jola.journalapp.Entry;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Entry")
    List<Entry> getAllEntries();

    @Insert
    void insertEntry(Entry entry);

    @Update
    int updateEntry(Entry entry);

    @Query("DELETE FROM Entry")
    void deleteAllEntries();

    @Delete
    void deleteEntry(Entry entry);
}

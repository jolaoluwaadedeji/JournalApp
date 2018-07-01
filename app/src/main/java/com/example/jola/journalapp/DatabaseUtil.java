package com.example.jola.journalapp;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseUtil {
    public final AppDatabase getDb(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
        return db;
    }
}

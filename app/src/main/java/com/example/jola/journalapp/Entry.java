package com.example.jola.journalapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Entry {
    public Entry(String Title, String Content, String LastModifiedDate){
        this.Title = Title;
        this.Content = Content;
        this.LastModifiedDate = LastModifiedDate;
    }
    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "title")
    private  String Title;
    @ColumnInfo(name = "content")
    private  String Content;
    @ColumnInfo(name = "last_modified_date")
    private  String LastModifiedDate;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedDate() {
        return LastModifiedDate;
    }
}

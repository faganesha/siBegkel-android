package com.example.sibengkel.controllers;

import android.content.ContentValues;

import com.example.sibengkel.utils.Database;
import com.example.sibengkel.utils.DatabaseContents;

import java.util.List;

public class BookController {

    private static Database database;
    private static BookController instance;

    private BookController(){}

    public static BookController getInstance(){
        if(instance == null)
            instance = new BookController();

        return instance;
    }

    public static void setDatabase(Database db){
        database = db;
    }

    public int Book(ContentValues content){
        int id = database.insert(DatabaseContents.TABLE_BOOK.toString(), content);

        return id;
    }

    public ContentValues getDataByEmail(String email) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_BOOK + " WHERE email = '"+ email +"'";
        List<Object> contents = database.select(queryString);

        if (contents.isEmpty()) {
            return null;
        }

        ContentValues content = (ContentValues) contents.get(0);
        return content;
    }

    public ContentValues getDataByTanggal(String tanggal) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_BOOK + " WHERE tanggal = '"+ tanggal +"'";
        List<Object> contents = database.select(queryString);

        if (contents.isEmpty()) {
            return null;
        }

        ContentValues content = (ContentValues) contents.get(0);
        return content;
    }

}

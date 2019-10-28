package com.example.consumerapp;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.consumerapp.DbContract.DbColumns.DATE;
import static com.example.consumerapp.DbContract.DbColumns.DESCRIPTION;
import static com.example.consumerapp.DbContract.DbColumns.ID;
import static com.example.consumerapp.DbContract.DbColumns.POSTER_PATH;
import static com.example.consumerapp.DbContract.DbColumns.TITLE;

public class Mapping {
    public static ArrayList<Movie> mapCursor(Cursor cursor){
        ArrayList<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH));

            movies.add(new Movie(id,title,date,description,posterPath));
        }
        return movies;
    }
}

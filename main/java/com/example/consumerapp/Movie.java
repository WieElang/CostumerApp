package com.example.consumerapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.consumerapp.DbContract.getColumnInt;
import static com.example.consumerapp.DbContract.getColumnString;

public class Movie implements Parcelable {
    private int id;
    private String title,date,description,posterPath;

    public Movie(int id, String title, String date, String description, String posterPath) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.posterPath = posterPath;
    }

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor,DbContract.DbColumns.ID);
        this.title = getColumnString(cursor,DbContract.DbColumns.TITLE);
        this.date = getColumnString(cursor, DbContract.DbColumns.DATE);
        this.description = getColumnString(cursor, DbContract.DbColumns.DESCRIPTION);
        this.posterPath = getColumnString(cursor, DbContract.DbColumns.POSTER_PATH);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.posterPath);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

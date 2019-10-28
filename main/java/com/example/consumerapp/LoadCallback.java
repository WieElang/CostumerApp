package com.example.consumerapp;

import android.database.Cursor;

 public interface LoadCallback {
    void postExecute(Cursor movies);
}

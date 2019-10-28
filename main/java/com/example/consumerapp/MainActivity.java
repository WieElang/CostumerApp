package com.example.consumerapp;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.consumerapp.DbContract.DbColumns.CONTENT;
import static com.example.consumerapp.Mapping.mapCursor;

public class MainActivity extends AppCompatActivity implements LoadCallback {
    private Adapter adapter;
    private DataObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        observer = new DataObserver(handler, this);
        if (adapter != null) {
            getContentResolver().registerContentObserver(CONTENT, true, observer);
            new getData(this, this).execute();
        } else {
            Toast.makeText(getApplicationContext(), "Adapter null", Toast.LENGTH_LONG).show();
        }
    }


    @Override

    public void postExecute(Cursor movie) {
        ArrayList<Movie> movieArrayList = mapCursor(movie);
        if (movieArrayList.size() > 0) {
            adapter.setMovies((movieArrayList));
        } else {
            Toast.makeText(this, "No More Data now", Toast.LENGTH_LONG).show();
            adapter.setMovies(new ArrayList<Movie>());
        }
    }


        private static class getData extends AsyncTask<Void, Void, Cursor> {
            private final WeakReference<Context> weakContext;
            private final WeakReference<LoadCallback> weakCallback;

            private getData(Context context, LoadCallback callback) {
                weakContext = new WeakReference<>(context);
                weakCallback = new WeakReference<>(callback);
            }

            @Override
            protected Cursor doInBackground(Void... voids) {
                return weakContext.get().getContentResolver().query(CONTENT, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                weakCallback.get().postExecute(cursor);
            }
        }

         static class DataObserver extends ContentObserver {
            final Context context;

            /**
             * Creates a content observer.
             *
             * @param handler The handler to run {@link #onChange} on, or null if none.
             */

            DataObserver(Handler handler, Context context) {
                super(handler);
                this.context = context;
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                new getData(context, (MainActivity) context).execute();
            }
        }
}

package com.example.flowers;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


/**
 * FlowerRoomDatabase includes code to create the Database
 * After the app creates the database ,
 * all interactions happen through FlowerViewModel
 */
@Database(entities = {Flower.class}, version = 4, exportSchema = false)
public abstract class FlowerRoomDatabase extends RoomDatabase {
    public abstract FlowerDao flowerDao();
    private static FlowerRoomDatabase INSTANCE;

    //Database building
    public static FlowerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FlowerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FlowerRoomDatabase.class, "flower_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Populate the database in background
     */
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private PopulateDbAsync(FlowerRoomDatabase fd) {
           fd.flowerDao();
        }
        @Override
        protected Void doInBackground(final Void... voids) {
            return null;
        }
    }
}

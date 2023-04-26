package com.example.flowers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Flower.class}, version = 1, exportSchema = false)
public abstract class FlowerRoomDatabase extends RoomDatabase {
    public abstract FlowerDao flowerDao();

    private static FlowerRoomDatabase INSTANCE;

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

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final FlowerDao dao;
        String[] flowers = {"rose","margarita"};
        String[] dates = {"2/4/2023",null};

        private PopulateDbAsync(FlowerRoomDatabase fd) {
            dao = fd.flowerDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            dao.deleteAll();
            for (int i = 0; i <= flowers.length - 1; i++) {
                Flower flower = new Flower(flowers[i], dates[i]);
                dao.insertFlower(flower);
            }
            return null;
        }
    }
}

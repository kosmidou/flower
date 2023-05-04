package com.example.flowers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * This class holds the implementation code for the methods that interact with the database
 * For inserting,updating,deleting flowers or running queries you have to run the
 * appropriate database methods in background
 */
public class FlowerRepository {

    private FlowerDao flowerDao;
    private LiveData<List<Flower>> allItems;

    FlowerRepository(Application application) {

        FlowerRoomDatabase fd = FlowerRoomDatabase.getDatabase(application);
        flowerDao = fd.flowerDao();
        allItems = flowerDao.getAllItems();
    }

    LiveData<List<Flower>> getAllItems() {
        return allItems;
    }

    public void insertFlower(Flower flower) {
        new insertAsyncTask(flowerDao).execute(flower);
    }

    public void deleteFlower(Flower flower) {
        new deleteAsyncTask(flowerDao).execute(flower);
    }

    public void updateFlower(Flower flower) {
        new updateAsyncTask(flowerDao).execute(flower);
    }

    private static class insertAsyncTask extends AsyncTask<Flower, Void, Void> {
        private FlowerDao flowerDao;

        insertAsyncTask(FlowerDao flowerDao) {
            this.flowerDao = flowerDao;
        }

        @Override
        protected Void doInBackground(final Flower... flowers) {

            flowerDao.insertFlower(flowers[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Flower, Void, Void> {

        private FlowerDao flowerDao;

        deleteAsyncTask(FlowerDao flowerDao) {
            this.flowerDao = flowerDao;
        }

        @Override
        protected Void doInBackground(final Flower... flowers) {

            flowerDao.delete(flowers[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Flower, Void, Void> {
        private FlowerDao flowerDao;

        updateAsyncTask(FlowerDao flowerDao) {
            this.flowerDao = flowerDao;
        }

        @Override
        protected Void doInBackground(final Flower... flowers) {

            flowerDao.update(flowers[0]);
            return null;
        }
    }


}

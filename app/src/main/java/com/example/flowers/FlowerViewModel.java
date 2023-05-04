package com.example.flowers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * FlowerViewModel helps the interaction between
 * UI and data layer of the app
 */
public class FlowerViewModel extends AndroidViewModel {
    private FlowerRepository flowerRepository;
    private LiveData<List<Flower>> allItems;

    public FlowerViewModel(@NonNull Application application) {

        super(application);
        flowerRepository = new FlowerRepository(application);
        allItems = flowerRepository.getAllItems();
    }

    LiveData<List<Flower>> getAllItems() {
        return allItems;
    }

    public void insert(Flower flower) {
        flowerRepository.insertFlower(flower);
    }

    public void delete(Flower flower) {
        flowerRepository.deleteFlower(flower);
    }

    public void update(Flower flower) {
        flowerRepository.updateFlower(flower);
    }
}

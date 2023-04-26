package com.example.flowers;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FlowerDao {
    @Insert
    void insertFlower(Flower flower);

    @Query("DELETE FROM flower")
    void deleteAll();

    @Delete
    void delete(Flower flower);

    @Update
    void update(Flower flower);

    @Query("SELECT * from flower ORDER BY flower ASC")
    LiveData<List<Flower>> getAllItems();

}

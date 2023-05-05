package com.example.flowers;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for a flower
 * Each method performs a database operation ,
 * such as inserting,deleting and updating a flower
 * Running DB queries
 */
@Dao
public interface FlowerDao {
    @Insert
    void insertFlower(Flower flower);

    /**
     * Delete all items from the table
     */
    @Query("DELETE FROM flower")
    void deleteAll();

    /**
     * @param flower Delete specific flower from the table
     */
    @Delete
    void delete(Flower flower);

    @Update
    void update(Flower flower);

    @Query("SELECT * from flower ORDER BY flowerId")
    LiveData<List<Flower>> getAllItems();

}

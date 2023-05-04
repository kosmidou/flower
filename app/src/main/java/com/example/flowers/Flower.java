package com.example.flowers;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "flower")
public class Flower implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "flower")
    private String flowerName;

    @ColumnInfo(name = "date")
    private long date;

    @Ignore
    public Flower(int id, @NonNull String flowerName, long date) {
        this.id = id;
        this.flowerName = flowerName;
        this.date = date;
    }


    public Flower(@NonNull String flowerName,long date) {
        this.flowerName = flowerName;
        this.date = date;
    }

    public String getFlowerName() {
        return this.flowerName;
    }

    public int getId() {
        return this.id;
    }

    public long getDate() {
        return this.date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

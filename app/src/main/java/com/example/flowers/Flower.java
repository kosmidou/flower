package com.example.flowers;

import android.annotation.SuppressLint;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String longToString(long currentDate){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(currentDate);
        return strDate;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

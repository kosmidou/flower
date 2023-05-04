package com.example.flowers;

import android.annotation.SuppressLint;
import android.text.TextUtils;

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

/**
 * Entity class that represents a flower in the
 * database matched with name(mandatory) and date
 */
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

    public Flower(@NonNull String flowerName) {
        this.flowerName = flowerName;
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

    public String getDateFromLong(long currentDate) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(currentDate);
        return strDate;
    }

    public Flower setFlowerName(String flowerName) {
        this.flowerName = flowerName;
        return this;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Flower setDateFromString(@NonNull String currentDate) {
        if (TextUtils.isEmpty(currentDate)) {
            this.date = 0;
            return this;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parsedDate = dateFormat.parse(currentDate);
            this.date = parsedDate != null ? parsedDate.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this;
    }
}

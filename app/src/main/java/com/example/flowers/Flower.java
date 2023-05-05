package com.example.flowers;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private int flowerId;
    @NonNull
    @ColumnInfo(name = "flower")
    private String flowerName;

    @ColumnInfo(name = "date")
    private long flowerDate;

    public Flower(@NonNull String flowerName) {
        this.flowerName = flowerName;
    }

    @NonNull
    public String getFlowerName() {
        return this.flowerName;
    }

    public int getFlowerId() {
        return this.flowerId;
    }

    public long getFlowerDate() {
        return this.flowerDate;
    }

    public void setFlowerId(int id) {
        this.flowerId = id;
    }

    public String getDateFromLong(long currentDate) {
        DateFormat dateFormat = dateFormatting();
        return  dateFormat.format(currentDate);
    }

    public Flower setFlowerName(String flowerName) {
        this.flowerName = flowerName;
        return this;
    }

    public void setFlowerDate(long date) {
        this.flowerDate = date;
    }

    public Flower setDateFromString(@NonNull String currentDate) {
        if (TextUtils.isEmpty(currentDate)) {
            this.flowerDate = 0;
            return this;
        }

        DateFormat dateFormat = dateFormatting();
        try {
            Date parsedDate = dateFormat.parse(currentDate);
            this.flowerDate = parsedDate != null ? parsedDate.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this;
    }

    public DateFormat dateFormatting(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}

package com.example.flowers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity class that represents a flower in the
 * database matched with name(mandatory) , date and image
 */
@Entity(tableName = "flower")
public class Flower implements Serializable {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @PrimaryKey(autoGenerate = true)
    private int flowerId;
    @NonNull
    @ColumnInfo(name = "flower")
    private String flowerName;

    @ColumnInfo(name = "date")
    private long flowerDate;

    @ColumnInfo(name = "image path")
    private String flowerImage;

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

    public String getFlowerImage() {
        return this.flowerImage;
    }

    public void setFlowerId(int id) {
        this.flowerId = id;
    }

    public String getDateFromLong(long currentDate) {
        return DATE_FORMAT.format(currentDate);
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

        DateFormat dateFormat = DATE_FORMAT;
        try {
            Date parsedDate = dateFormat.parse(currentDate);
            this.flowerDate = parsedDate != null ? parsedDate.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void setFlowerImagePath(Bitmap capturedImage, Context context, int generatedNumber) {
        this.flowerImage = writeImageInStorage(capturedImage, context, generatedNumber);
    }

    public void setFlowerImage(String flowerImage) {
        this.flowerImage = flowerImage;
    }

    public String writeImageInStorage(Bitmap capturedImage, Context context, int generatedNumber) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, generatedNumber + ".jpg");
        if (!file.exists()) {
            // FileOutputStream is meant for writing streams of raw bytes such as image data in this case
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return file.toString();
    }

}

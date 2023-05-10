package com.example.flowers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
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

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    @PrimaryKey(autoGenerate = true)
    private int flowerId;
    @NonNull
    @ColumnInfo(name = "flower")
    private String flowerName;

    @ColumnInfo(name = "date")
    private long flowerDate;

    @ColumnInfo(name="image")
    private byte[] flowerImage;

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

    public Bitmap getFlowerimage(){

        if(this.flowerImage == null){
            setFlowerimage(Bitmap.createBitmap(100,100 ,Bitmap.Config.ARGB_4444));
        }
        return getImage(this.flowerImage);
    }

    public byte[] getFlowerImage(){ return this.flowerImage;}

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void setFlowerId(int id) {
        this.flowerId = id;
    }

    public String getDateFromLong(long currentDate) {
        return  DATE_FORMAT.format(currentDate);
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

    public Flower setFlowerimage(Bitmap image){
        this.flowerImage = getBytes(image);
        return this;
    }

    public void setFlowerImage(byte[] flowerImage){
        this.flowerImage=flowerImage;
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }




}

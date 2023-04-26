package com.example.flowers;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="flower")
public class Flower {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "flower")
    private String flowerName;

    @ColumnInfo(name = "date")
    private String date;


    public Flower(int id, @NonNull String flowerName ,  String date){
        this.id=id;
        this.flowerName=flowerName;
        this.date=date;
    }
    @Ignore
    public Flower(@NonNull String flowerName,String date){
        this.flowerName=flowerName;
        this.date=date;
    }
    @Ignore
    public Flower(@NonNull String flowerName){
        this.flowerName=flowerName;
    }

    public String getFlowerName(){
        return this.flowerName;}

    public int getId (){
        return this.id;}

    public String getDate(){
        return this.date;}

    public void setId(int id){
        this.id=id;
    }
    public void setFlowerName(String flowerName){
        this.flowerName=flowerName;
    }
    public void setDate(String date){
        this.date=date;
    }
}

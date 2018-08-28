package com.example.superl.park30;

/**
 * Created by gdei on 2018/4/19.
 */

public class ParkMsg {

    private String name;
    private double longitude;   //经度
    private double  latitude;   //维度
    private int canPark;    //可停车位数量
    private int totalPark;      //总车位数
    private float distance;       //距离
    private int grade;
    private double price;
    public ParkMsg(String name, double latitude, double longitude, int canPark, int totalPark, int grade, double price){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.canPark = canPark;
        this.totalPark = totalPark;
        this.grade = grade;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getCanPark() {
        return canPark;
    }

    public int getTotalPark() {
        return totalPark;
    }

    public float getDistance() {
        return distance;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

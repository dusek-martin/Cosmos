package com.soulk.cosmos;

public class SpacePoint {
    private int id;
    private double x, y;

    public SpacePoint(int id, double x, double y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {return id;}
    public double getX(){return x;}
    public double getY() {return y;}
}

package com.soulk.cosmos;

public class SpacePoint {
    private int id;
    protected float x, y;

    public SpacePoint(int id, float x, float y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {return id;}
//    public float getX(){return x;}
//    public float getY() {return y;}
//    public void setX(float x) {this.x = x;}
//    public void setY(float y) {this.y = y;}
}

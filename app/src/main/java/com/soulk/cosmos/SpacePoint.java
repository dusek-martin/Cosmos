package com.soulk.cosmos;

public class SpacePoint {
    private int id;
    public Vector position;

    public SpacePoint(int id, Vector position){
        this.id = id;
        this.position = position;
    }

    public int getId() {return id;}
}

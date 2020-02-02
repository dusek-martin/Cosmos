package com.soulk.cosmos;

import android.graphics.Bitmap;

public class StaticSpaceObject extends SpaceObject {

    public StaticSpaceObject(int id, Bitmap image, double density, double direction, double velocity,
                             double volume, float startingPositionX, float startingPositionY){
        super(id, image, density, direction, velocity, volume,
                startingPositionX, startingPositionY);
    }

    @Override
    public void updateCoordinates(ForceInterference forceInterference, double seconds){

    }
}

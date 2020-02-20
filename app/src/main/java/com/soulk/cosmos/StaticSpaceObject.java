package com.soulk.cosmos;

import android.graphics.Bitmap;

public class StaticSpaceObject extends SpaceObject {

    public StaticSpaceObject(int id, Bitmap image, double density, double volume,
                             Vector position, Vector speed){
        super(id, image, density, volume, position, speed);
    }

    @Override
    public void updatePosition(Vector forceInterference, double seconds){
        Vector targetPosition = position;

        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(this.getSpeed(), seconds));

        this.setSpeed(Vector.subtractVectors(targetPosition, position));

        position = targetPosition;
    }
}

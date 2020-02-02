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
        double targetX = x, targetY = y;

        targetX += super.getVelocity() * seconds * Math.cos(Math.toRadians(super.getDirection()));
        targetY += -super.getVelocity() * seconds * Math.sin(Math.toRadians(super.getDirection()));

        super.setVelocity(Math.hypot(x - targetX, y - targetY) / seconds);
        super.setDirection(-Math.toDegrees(Math.atan2((targetY - y), (targetX - x))));
        if (super.getDirection() < 0) {super.setDirection(super.getDirection() + 360);}

        x = (float) targetX;
        y = (float) targetY;
    }
}

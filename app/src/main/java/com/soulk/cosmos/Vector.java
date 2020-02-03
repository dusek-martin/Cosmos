package com.soulk.cosmos;

public class Vector {
    public double size;
    //direction counter-clockwise from X axis in degrees
    public double direction;
    // x and y coordinates from [0, 0]
    public double x, y;

    public Vector(double size, double direction){
        this.size = size;
        this.direction = direction;
        x = size * Math.cos(Math.toRadians(direction));
        //y-axis heads down
        y = -size * Math.sin(Math.toRadians(direction));
    }

    public void setByCoordinates(double x, double y){
        this.x = x;
        this.y = y;
        size = Math.hypot(x, y);
        direction = -Math.toDegrees(Math.atan2(y , x)) < 0 ? -Math.toDegrees(Math.atan2(y , x)) : -Math.toDegrees(Math.atan2(y , x)) + 360;
    }
}

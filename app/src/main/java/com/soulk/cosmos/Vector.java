package com.soulk.cosmos;

public class Vector {
    private static double pi = Math.PI;
    // x and y coordinates from [0, 0]
    public float x, y;

    public Vector(double size, double angle) {
        x = getX(size, angle);
        y = getY(size, angle);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector addVectors(Vector a, Vector b) {
        return new Vector((float) (a.x + b.x), (float) (a.y + b.y));
    }

    public static Vector subtractVectors(Vector a, Vector b) {
        return new Vector((float) (a.x - b.x), (float) (a.y - b.y));
    }

    public static Vector scaleVector(Vector a, double s) {
        return new Vector((float) (a.x * s), (float) (a.y * s));
    }
    public static Vector byAngle(double angle) {
        return new Vector(1, angle);
    }

    //angle counter-clockwise from X axis in radians
    public double getAngle() {
        double angle = -Math.atan2(y, x);
        return angle < 0 ? angle + 2 * pi : angle;
    }

    public double getSize() {
        return Math.hypot(x, y);
    }

    public void setSize(double size) {
        double angle = this.getAngle();
        x = getX(size, angle);
        y = getY(size, angle);
    }

    public void setAngle(double angle) {
        double size = this.getSize();
        x = getX(size, angle);
        y = getY(size, angle);
    }

    private float getX(double size, double angle){return (float) (size * Math.cos(angle));}
    private float getY(double size, double angle){return (float) (-size * Math.sin(angle));}
}

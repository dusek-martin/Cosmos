package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceObject extends SpacePoint{
    private double density, direction, velocity, volume;
    private Bitmap image;

    public SpaceObject(int id, Bitmap image, double density, double direction, double velocity,
                       double volume, float startingPositionX, float startingPositionY){
        super(id, startingPositionX, startingPositionY);
        this.density = density;
        this.direction = direction;
        this.velocity = velocity;
        this.volume = volume;
        this.image = Bitmap.createScaledBitmap(image, (int) volume, (int) volume, false);
    }

    public void updateCoordinates(ForceInterference forceInterference, double seconds){
        double forceAcceleration = forceInterference.force / getWeight();
        double forceVelocity = forceAcceleration * seconds;
        double targetX = x, targetY = y;

        targetX += forceVelocity * seconds * Math.cos(Math.toRadians(forceInterference.direction));
        targetY += -forceVelocity * seconds * Math.sin(Math.toRadians(forceInterference.direction));

        targetX += this.velocity * seconds * Math.cos(Math.toRadians(this.direction));
        targetY += -this.velocity * seconds * Math.sin(Math.toRadians(this.direction));

        this.velocity = Math.hypot(x - targetX, y - targetY) / seconds;
        this.direction = -Math.toDegrees(Math.atan2((targetY - y), (targetX - x)));
        if (this.direction < 0) {this.direction += 360;}

        x = (float) targetX;
        y = (float) targetY;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }


    public double getDensity(){return density;}
    public double getVelocity(){return velocity;}
    public double getVolume(){return volume;}
    public Bitmap getImage(){return image;}
    public double[] getCoordinates(){return new double[] {x, y};}
    public double[] getInformation(){return new double[]{density, velocity, volume};}
    public double getWeight() {return density*volume;}
    public void setDensity(double density){this.density = density;}
    public void setVelocity(double velocity){this.velocity = velocity;}
    public void setVolume(double volume){this.volume = volume;}
    public void setImage(Bitmap image){this.image = image;}
}

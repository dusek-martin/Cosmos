package com.soulk.cosmos;

import android.graphics.Bitmap;

public class SpaceObject extends SpacePoint{
    private double acceleration, density, velocity, volume;
    private Bitmap image;

    public SpaceObject(int id, Bitmap image, double acceleration, double density, double velocity,
                       double volume, double startingPositionX, double startingPositionY){
        super(id, startingPositionX, startingPositionY);
        this.acceleration = acceleration;
        this.density = density;
        this.velocity = velocity;
        this.volume = volume;
        this.image = image;
    }

    public void updateCoordinates(double force, double direction, double seconds){

    }

    public double getAcceleration(){return acceleration;}
    public double getDensity(){return density;}
    public double getVelocity(){return velocity;}
    public double getVolume(){return volume;}
    public Bitmap getImage(){return image;}
    public double[] getCoordinates(){return new double[] {super.getX(), super.getY()};}
    public double[] getInformation(){return new double[]{acceleration, density, velocity, volume};}
    public double getWeight() {return density*volume;}
    public void setAcceleration(double acceleration){this.acceleration = acceleration;}
    public void setDensity(double density){this.density = density;}
    public void setVelocity(double velocity){this.velocity = velocity;}
    public void setVolume(double volume){this.volume = volume;}
    public void setImage(Bitmap image){this.image = image;}
}

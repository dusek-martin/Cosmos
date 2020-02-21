package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceObject extends SpacePoint{
    private double density, direction, velocity, volume;
    private Bitmap image;
    private Vector speed;

    public SpaceObject(int id, Bitmap image, double density, double volume,
                       Vector position, Vector speed){
        super(id, position);
        this.image = Bitmap.createScaledBitmap(image, (int) volume, (int) volume, false);
        this.density = density;
        this.volume = volume;
        this.speed = speed;
    }

    public void updatePosition(Vector forceInterference, double seconds){
        Vector targetPosition = position;
        Vector forceSpeed = Vector.scaleVector(forceInterference, (1 / getWeight()));

        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(speed, seconds));
        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(forceSpeed, seconds));

        speed = Vector.scaleVector(Vector.subtractVectors(targetPosition, position), (1 / seconds));

        position = targetPosition;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, (float)(position.x - volume/2), (float)(position.y - volume/2), null);
    }

    public void absorb(SpaceObject so){
        double finWeight = this.getWeight() + so.getWeight();
        setVolume(2 * Math.sqrt(finWeight / (density * Math.PI)));
    }

    public Bitmap getImage(){return image;}
    public double getDensity(){return density;}
    public double getVolume(){return volume;}
    public Vector getSpeed(){return speed;}
    public Vector getPosition(){return this.position;}
    public double getWeight() {return (density * Math.PI * Math.pow(volume / 2, 2));}

    public void setImage(Bitmap image){this.image = image;}
    public void setDensity(double density){this.density = density;}
    public void setVolume(double volume){
        this.volume = volume;
        image = Bitmap.createScaledBitmap(image, (int) volume, (int) volume, false);
    }
    public void setSpeed(Vector speed){this.speed = speed;}
}

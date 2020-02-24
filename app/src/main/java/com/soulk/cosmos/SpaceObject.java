package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SpaceObject extends SpacePoint{
    private double density, direction, velocity, volume;
    private Bitmap image;
    private Vector speed;
    private Paint paint = new Paint();
    float[] colorInHSV;

    public SpaceObject(int id, Bitmap image, double density, double volume,
                       Vector position, Vector speed){
        super(id, position);
        this.image = Bitmap.createScaledBitmap(image, (int) volume, (int) volume, false);
        this.density = density;
        this.volume = volume;
        this.speed = speed;
        colorInHSV = new float[]{60, (float)(volume / 2 / 100), (float)(volume / 2 / 100)};
        paint.setColor(Color.HSVToColor(colorInHSV));
    }

    public SpaceObject(int id, double density, double volume,
                       Vector position, Vector speed){
        super(id, position);
        this.density = density;
        this.volume = volume;
        this.speed = speed;
        paint.setColor(Color.HSVToColor(colorInHSV));
    }

    public void update(Vector forceInterference, double seconds){
        Vector targetPosition = position;
        Vector forceSpeed = Vector.scaleVector(forceInterference, (1 / getWeight()));

        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(speed, seconds));
        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(forceSpeed, seconds));

        speed = Vector.scaleVector(Vector.subtractVectors(targetPosition, position), (1 / seconds));

        position = targetPosition;
    }

    public void draw(Canvas canvas){
        //canvas.drawBitmap(image, (float)(position.x - volume/2), (float)(position.y - volume/2), null);
        canvas.drawCircle(position.x, position.y, (float)(volume / 2), paint);
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
        colorInHSV[1] = (float)(volume / 2 / 100);
        colorInHSV[2] = (float)(volume / 2 / 100);
        paint.setColor(Color.HSVToColor(colorInHSV));

    }
    public void setSpeed(Vector speed){this.speed = speed;}
}

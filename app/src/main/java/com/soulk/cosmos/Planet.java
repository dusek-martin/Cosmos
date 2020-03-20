package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Planet extends SpacePoint implements IGravityObject, ISpaceObject {
    private double density, direction, velocity, radius;
    private Bitmap image;
    private Vector speed;
    private Paint paint = new Paint();
    float[] colorInHSV;

    public Planet(int id, Bitmap image, double density, double radius,
                       Vector position, Vector speed){
        super(id, position);
        this.image = Bitmap.createScaledBitmap(image, (int) radius, (int) (this.radius * 2), false);
        this.density = density;
        this.radius = radius;
        this.speed = speed;
        colorInHSV = new float[]{60, (float)(radius / 100), (float)(radius / 100)};
        paint.setColor(Color.HSVToColor(colorInHSV));
    }

    public Planet(int id, double density, double radius,
                       Vector position, Vector speed){
        super(id, position);
        this.image = null;
        this.density = density;
        this.radius = radius;
        this.speed = speed;
        colorInHSV = new float[]{60, (float)(radius / 100), (float)(radius / 100)};
        paint.setColor(Color.HSVToColor(colorInHSV));
    }

    public void update(Vector forceInterference, Canvas canvas, double seconds){
        updateForceInterference(forceInterference, seconds);
        bounceOfWall(canvas);
        //speed = Vector.scaleVector(speed, Math.pow(.9, seconds));
        if (speed.getSize() > 350){
            speed = new Vector(350, speed.getAngle());
        }
    }

    private void updateForceInterference(Vector forceInterference, double seconds){
        Vector targetPosition = position;
        Vector forceSpeed = Vector.scaleVector(forceInterference, (1 / getWeight()));

        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(speed, seconds));
        targetPosition = Vector.addVectors(targetPosition, Vector.scaleVector(forceSpeed, seconds));

        speed = Vector.scaleVector(Vector.subtractVectors(targetPosition, position), (1 / seconds));

        position = targetPosition;
    }

    public void draw(Canvas canvas){
        if (image != null) {
            canvas.drawBitmap(image, (float) (position.x - radius), (float) (position.y - radius), null);
        } else {
            canvas.drawCircle(position.x, position.y, (float)radius, paint);
        }
    }

    public void absorb(ISpaceObject so){
        double finWeight = this.getWeight() + so.getWeight();
        setRadius(Math.sqrt(finWeight / (density * Math.PI)));
    }

    private void bounceOfWall(Canvas canvas){
        if (getPosition().x + (getRadius() * 0.9) > canvas.getWidth()) {
            position.x = (float)(canvas.getWidth() - (getRadius() * 0.9));
            getSpeed().x = -getSpeed().x;
        } else if (getPosition().x - (getRadius() * 0.9) < 0) {
            position.x = (float)(0 + (getRadius() * 0.9));
            getSpeed().x = -getSpeed().x;
        } else if (getPosition().y + (getRadius() * 0.9) > canvas.getHeight()) {
            position.y = (float)(canvas.getHeight() - (getRadius() * 0.9));
            getSpeed().y = -getSpeed().y;
        } else if (getPosition().y - (getRadius() * 0.9) < 0) {
            position.y = (float)(0 + (getRadius() * 0.9));
            getSpeed().y = -getSpeed().y;
        }
    }

    @Override
    public int getId(){return super.getId();}
    public Bitmap getImage(){return image;}
    public double getDensity(){return density;}
    public double getRadius(){return radius;}
    public Vector getSpeed(){return speed;}
    public Vector getPosition(){return this.position;}
    public double getWeight() {return (density * Math.PI * Math.pow(radius, 2));}

    public void setImage(Bitmap image){this.image = image;}
    public void setDensity(double density){this.density = density;}
    private void setRadius(double radius){
        this.radius = radius;
        if (image != null)
            image = Bitmap.createScaledBitmap(image, (int) (radius * 2), (int) (radius * 2), false);
        colorInHSV[1] = (float)(radius / 100);
        colorInHSV[2] = (float)(radius / 100);
        paint.setColor(Color.HSVToColor(colorInHSV));
    }
    public void setSpeed(Vector speed){this.speed = speed;}
    public void setPosition(Vector position){this.position = position;}
}

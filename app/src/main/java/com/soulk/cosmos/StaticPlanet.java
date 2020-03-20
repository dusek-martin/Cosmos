package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class StaticPlanet extends Planet {

    public StaticPlanet(int id, Bitmap image, double density, double radius,
                        Vector position, Vector speed){
        super(id, image, density, radius, position, speed);
    }

    public StaticPlanet(int id, double density, double radius,
                        Vector position, Vector speed){
        super(id, density, radius, position, speed);
    }

    @Override
    public void update(Vector forceInterference, Canvas canvas, double seconds){
        position = Vector.addVectors(position, Vector.scaleVector(this.getSpeed(), seconds));
    }
}

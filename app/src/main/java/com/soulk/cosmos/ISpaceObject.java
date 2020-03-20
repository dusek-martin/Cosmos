package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface ISpaceObject {

        void update(Vector forceInterference, Canvas canvas, double seconds);
        void draw(Canvas canvas);
        void absorb(ISpaceObject so);

        int getId();
        Bitmap getImage();
        double getDensity();
        double getRadius();
        Vector getSpeed();
        Vector getPosition();
        double getWeight();

        void setImage(Bitmap image);
        void setDensity(double density);
        void setSpeed(Vector speed);
        void setPosition(Vector position);
}




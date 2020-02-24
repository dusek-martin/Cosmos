package com.soulk.cosmos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shot {
    public Vector position, speed;
    private Paint paint = new Paint();

    public Shot(Canvas canvas, Vector position, double angle)
    {
        this.position = position;
        speed = new Vector(450, angle);
        paint.setColor(Color.WHITE);
    }

    public void update(double tickTime)
    {
        position = Vector.addVectors(position, Vector.scaleVector(speed, tickTime));
    }

    public void draw(Canvas canvas)
    {
        Vector shotEnd = Vector.addVectors(position, Vector.scaleVector(speed, 0.05));
        canvas.drawLine(position.x, position.y, shotEnd.x, shotEnd.y, paint);
    }


}

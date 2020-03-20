package com.soulk.cosmos;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameInput {
    public boolean left, right, up, fire;
    public Vector upPosition, firePosition, leftPosition, rightPosition;
    Paint paint = new Paint();
    float height;

    public GameInput(){
        left = false;
        right = false;
        up = false;
        fire = false;

        paint.setColor(Color.DKGRAY);
    }

    public void reset(){
        left = false;
        right = false;
        up = false;
        fire = false;
    }

    public void draw(Canvas canvas){

        upPosition = new Vector((float)(height * 0.1), (float)(height * 0.85));
        firePosition = new Vector((float)(height * 0.20), (float)(height * 0.85));
        leftPosition = new Vector((float)(height * 0.1), (float)(height * 0.1));
        rightPosition = new Vector((float)(height * 0.1), (float)(height * 0.20));

        canvas.drawCircle(upPosition.x, upPosition.y, (float)(height * 0.05), paint);
        canvas.drawCircle(firePosition.x, firePosition.y, (float)(height * 0.05), paint);
        canvas.drawCircle(leftPosition.x, leftPosition.y, (float)(height * 0.05), paint);
        canvas.drawCircle(rightPosition.x, rightPosition.y, (float)(height * 0.05), paint);
    }
}

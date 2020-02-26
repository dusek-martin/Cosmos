package com.soulk.cosmos;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameInput {
    public boolean left, right, up, down, fire;
    public Vector upPosition, firePosition, leftPosition, rightPosition;
    Paint paint = new Paint();


    public GameInput(){
        left = false;
        right = false;
        up = false;
        down = false;
        fire = false;

        upPosition = new Vector((float)(150), (float)(1600));
        firePosition = new Vector((float)(300), (float)(1600));
        leftPosition = new Vector((float)(150), (float)(150));
        rightPosition = new Vector((float)(150), (float)(300));

        paint.setColor(Color.DKGRAY);
    }

    public void reset(){
        left = false;
        right = false;
        up = false;
        down = false;
        fire = false;
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(upPosition.x, upPosition.y, 75, paint);
        canvas.drawCircle(firePosition.x, firePosition.y, 75, paint);
        canvas.drawCircle(leftPosition.x, leftPosition.y, 75, paint);
        canvas.drawCircle(rightPosition.x, rightPosition.y, 75, paint);
    }
}

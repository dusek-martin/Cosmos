package com.soulk.cosmos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap image;
    private int x = 0, y = 0;
    private int xVelocity = 10, yVelocity = 10;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp) {
        image = bmp;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y,null);
    }

    public void updateCoordinates(){
        x += xVelocity;
        y += yVelocity;
        if ((x > screenWidth-image.getWidth()) || (x < 0)){
            xVelocity *= -1;
        }
        if ((y > screenHeight - image.getHeight()) || (y < 0)){
            yVelocity *= -1;
        }
    }
}

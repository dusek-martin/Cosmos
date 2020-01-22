package com.soulk.cosmos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SpaceContinuum spaceContinuum;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    public void update(){spaceContinuum.update(1);}

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null){
            //canvas.drawColor(Color.WHITE);
            spaceContinuum.draw(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        spaceContinuum = new SpaceContinuum();
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 260, 2, 10, 200, 300);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 200, 170, 0, 400, 400, 800);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 180, 2, 10, 800, 1200);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 220, 2, 10, 700, 200);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 70, 2, 10, 600, 100);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 270, 2, 10, 500, 800);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 90, 2, 10, 400, 1200);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 100, 70, 2, 10, 300, 600);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 10, 0, 2, 10, 200, 1200);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 15, 270, 2, 8, 800, 300);
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 50, 70, 2, 17, 200, 1400);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}

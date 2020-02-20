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

    public void update(){spaceContinuum.update(0.5);}

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

        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 300, 120, new Vector(200, 200), new Vector(0, 5));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid1), 300, 220, new Vector(600, 1200), new Vector(0, -5));

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

package com.soulk.cosmos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SpaceContinuum spaceContinuum;
    private Rocket rocket;

    private Vector touchPosition;
    public GameInput input;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        input = new GameInput();
        touchPosition = null;
    }

    public void update(Canvas canvas, GameInput input, double tickTime) {
        rocket.update(canvas, input, tickTime);
        spaceContinuum.update(canvas, tickTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            //canvas.drawColor(Color.LTGRAY);
            rocket.draw(canvas);
            spaceContinuum.draw(canvas);
            input.draw(canvas);
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

        rocket = new Rocket(new Vector(300, 300));

        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 50, new Vector(200, 200), new Vector(0, 8));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 50, new Vector(800, 1400), new Vector(2f, -8));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 30, new Vector(600, 900), new Vector(0, 0));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 30, new Vector(400, 1200), new Vector(0, 0));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 7, new Vector(600, 500), new Vector(-1, 4));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 30, new Vector(800, 100), new Vector(0, 0));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 30, new Vector(100, 800), new Vector(0, 0));
        spaceContinuum.addSpaceObject(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1), 5, 60, new Vector(800, 1000), new Vector(0, 0));

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchPosition = new Vector(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Vector.subtractVectors(touchPosition, input.upPosition).getSize() < 75) {
                    input.up = true;
                }
                if (Vector.subtractVectors(touchPosition, input.firePosition).getSize() < 75) {
                    input.fire = true;
                }
                if (Vector.subtractVectors(touchPosition, input.leftPosition).getSize() < 75){
                    input.left = true;
                } else if (Vector.subtractVectors(touchPosition, input.rightPosition).getSize() < 75){
                    input.right = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                input.reset();
                touchPosition = null;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return true;
    }
}

package com.soulk.cosmos;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    public SpaceContinuum spaceContinuum;
    public Rocket rocket;

    private Vector touchPosition;
    public GameInput input;
    public Random random;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        input = new GameInput();
        touchPosition = null;
        random = new Random();
    }

    public void update(Canvas canvas, double tickTime) {
        input.height = canvas.getHeight();
        Vector forceInterferenceOfRocket = spaceContinuum.calcSOGravityInterference(rocket);
        rocket.update(forceInterferenceOfRocket, canvas, tickTime);
        spaceContinuum.update(canvas, tickTime, rocket.getShots());
        input.fire = false;
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
        spaceContinuum.addPlanet(random.nextInt(20), random.nextInt(70), new Vector(random.nextInt(800), random.nextInt(1600)), new Vector(random.nextInt(10), random.nextInt(10)));
        spaceContinuum.addPlanet(10, 50, new Vector(800, 1400), new Vector(6, -20));
        spaceContinuum.addPlanet(10, 30, new Vector(600, 900), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 40, new Vector(100, 900), new Vector(2, 1));
        spaceContinuum.addPlanet(10, 30, new Vector(400, 800), new Vector(0, -5));
        spaceContinuum.addPlanet(10, 30, new Vector(300, 1200), new Vector(-3, -1));
        spaceContinuum.addPlanet(10, 30, new Vector(400, 100), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(500, 800), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(150, 200), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(400, 1600), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(400, 100), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(600, 1000), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(400, 1200), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(80, 1200), new Vector(0, 0));
        spaceContinuum.addPlanet(10, 30, new Vector(400, 1200), new Vector(0, 0));
        spaceContinuum.addPlanet(15, 7, new Vector(600, 500), new Vector(-1, 4));
        spaceContinuum.addPlanet(5, 30, new Vector(800, 100), new Vector(0, 0));
        spaceContinuum.addPlanet(5, 30, new Vector(100, 800), new Vector(0, 0));
        spaceContinuum.addPlanet(5, 60, new Vector(800, 1000), new Vector(0, 0));

        rocket = new Rocket(new Vector(300, 300), input);
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
                if (Vector.subtractVectors(touchPosition, input.upPosition).getSize() < input.height * 0.05) {
                    input.up = true;
                }
                if (Vector.subtractVectors(touchPosition, input.firePosition).getSize() < input.height * 0.05) {
                    input.fire = true;
                }
                if (Vector.subtractVectors(touchPosition, input.leftPosition).getSize() < input.height * 0.05){
                    input.left = true;
                } else if (Vector.subtractVectors(touchPosition, input.rightPosition).getSize() < input.height * 0.05){
                    input.right = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (Vector.subtractVectors(touchPosition, input.upPosition).getSize() < input.height * 0.05) {
                    input.up = true;
                }
                if (Vector.subtractVectors(touchPosition, input.firePosition).getSize() < input.height * 0.05) {
                    input.fire = true;
                }
                if (Vector.subtractVectors(touchPosition, input.leftPosition).getSize() < input.height * 0.05){
                    input.left = true;
                } else if (Vector.subtractVectors(touchPosition, input.rightPosition).getSize() < input.height * 0.05){
                    input.right = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (Vector.subtractVectors(touchPosition, input.upPosition).getSize() < input.height * 0.05) {
                    input.up = false;
                }
                if (Vector.subtractVectors(touchPosition, input.firePosition).getSize() < input.height * 0.05) {
                    input.fire = false;
                }
                if (Vector.subtractVectors(touchPosition, input.leftPosition).getSize() < input.height * 0.05){
                    input.left = false;
                } else if (Vector.subtractVectors(touchPosition, input.rightPosition).getSize() < input.height * 0.05){
                    input.right = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                input.reset();
                touchPosition = null;
                break;
            //case MotionEvent.ACTION_MOVE:
            //    break;
        }

        return true;
    }

}

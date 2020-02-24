package com.soulk.cosmos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

public class Rocket {
    public Vector position, speed;
    private float angle;
    private float size = 80;
    private float maxSpeed = 350;
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private Paint paint = new Paint();

    public Rocket(Vector position)
    {
        this.position = position;
        speed = new Vector(0, 0);
        angle = 0;
        paint.setColor(Color.LTGRAY);
        //paint.setStyle(Paint.Style.FILL);
    }

    public void update(Canvas canvas, GameInput input, double tickTime)
    {
        //ovládání rakety
        controlRocket(canvas, input, tickTime);
        //upravení pozice
        position = Vector.addVectors(position, Vector.scaleVector(speed, tickTime));
        //teleport na druhou stranu obrazovky
        teleportToOtherSide(canvas);

        //update střel
        for (int i = 0; i < shots.size(); i++)
        {
            shots.get(i).update(tickTime);
            if ((shots.get(i).position.x > canvas.getWidth()) ||
                    (shots.get(i).position.x < 0) ||
                    (shots.get(i).position.y > canvas.getHeight()) ||
                    (shots.get(i).position.y < 0))
                shots.remove(shots.get(i));
        }
    }

    public void draw(Canvas Canvas)
    {
        drawRocket(Canvas, this.position);

        //vykreslení "přesahu" přes okraj
        drawOverlap(Canvas);

        //vykreslení střel
        for (Shot shot: shots)
        {
            shot.draw(Canvas);
        }
    }

    public ArrayList<Shot> getShots() { return shots; }

    private void drawRocket(Canvas canvas, Vector position)
    {
        //určení rohů trojúhelníku
        Vector tipOfRocket = Vector.addVectors(position, Vector.scaleVector(Vector.byAngle(angle), (size * 2 / 3)));
        Vector baseMid = Vector.addVectors(position, Vector.scaleVector(Vector.byAngle(angle + Math.PI), (size / 3)));
        Vector baseRight = Vector.addVectors(baseMid, (Vector.scaleVector(Vector.byAngle(angle + Math.PI / 2), (size / 4))));
        Vector baseLeft = Vector.addVectors(baseMid, (Vector.scaleVector(Vector.byAngle(angle - Math.PI / 2), (size / 4))));

        //tři úsečky otáčené kolem těžiště rakety
        //canvas.drawLine(tipOfRocket.x, tipOfRocket.y, baseRight.x, baseRight.y, paint);
        //canvas.drawLine(tipOfRocket.x, tipOfRocket.y, baseLeft.x, baseLeft.y, paint);
        //canvas.drawLine(baseRight.x, baseRight.y, baseLeft.x, baseLeft.y, paint);

        //plný trojúhelník
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(tipOfRocket.x, tipOfRocket.y);
        path.lineTo(baseLeft.x, baseLeft.y);
        path.lineTo(baseRight.x, baseRight.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    private void teleportToOtherSide(Canvas canvas)
    {
        if (position.x >= canvas.getWidth())
        {
            position.x = 0;
        }
        else if (position.x <= 0)
        {
            position.x = canvas.getWidth();
        }
        if (position.y >= canvas.getHeight())
        {
            position.y = 0;
        }
        else if (position.y <= 0)
        {
            position.y = canvas.getHeight();
        }
    }

    private void drawOverlap(Canvas canvas)
    {
        if (position.x + size >= canvas.getWidth())
        {
            drawRocket(canvas, new Vector(position.x - canvas.getWidth(), position.y));
        }
        else if (position.x - size <= 0)
        {
            drawRocket(canvas, new Vector(position.x + canvas.getWidth(), position.y));
        }
        if (position.y + size >= canvas.getWidth())
        {
            drawRocket(canvas, new Vector(position.x, position.y - canvas.getHeight()));
        }
        else if (position.y - size <= 0)
        {
            drawRocket(canvas, new Vector(position.x, position.y + canvas.getHeight()));
        }
    }

    private void controlRocket(Canvas canvas, GameInput input, double tickTime)
    {
        //reakce na ovladani směru
        if (input.left) angle -= tickTime * 2 * (float)Math.PI * 2 / 3;
        if (input.right) angle += tickTime * 2 * (float)Math.PI * 2 / 3;

        //reakce na ovladani rychlosti
        if (input.up) speed = Vector.addVectors(speed, Vector.scaleVector(Vector.byAngle(angle) , (300 * tickTime)));
        if (input.down) speed = Vector.subtractVectors(speed, Vector.scaleVector(Vector.byAngle(angle), (300 * tickTime)));
        if (!input.up && !input.down) speed = Vector.scaleVector(speed, (float)Math.pow(.3f, tickTime));
        if (speed.getSize() >= maxSpeed) speed = new Vector((double)(maxSpeed), (double)speed.getAngle());

        //reakce na střílení
        Vector tipOfRocket = Vector.addVectors(position, Vector.scaleVector(Vector.byAngle(angle), (size * 2 / 3)));
        if (input.fire)
        {
            if (tipOfRocket.x > canvas.getWidth())
            {
                shots.add(new Shot(canvas, Vector.subtractVectors(tipOfRocket, new Vector(canvas.getWidth(), 0)), angle));
            }
            else if (tipOfRocket.x < 0)
            {
                shots.add(new Shot(canvas, Vector.addVectors(tipOfRocket, new Vector(canvas.getWidth(), 0)), angle));
            }
            else if (tipOfRocket.y > canvas.getHeight())
            {
                shots.add(new Shot(canvas, Vector.subtractVectors(tipOfRocket, new Vector(0, canvas.getHeight())), angle));
            }
            else if (tipOfRocket.y < 0)
            {
                shots.add(new Shot(canvas, Vector.addVectors(tipOfRocket, new Vector(0, canvas.getHeight())), angle));
            }
            else
            {
                shots.add(new Shot(canvas, tipOfRocket, angle));
            }
        }
    }
}

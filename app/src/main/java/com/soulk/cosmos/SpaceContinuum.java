package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class SpaceContinuum {
    private int countId;
    private ArrayList<SpaceObject> spaceObjects;
    public static final double G = 0.1;

    public SpaceContinuum(){
        spaceObjects = new ArrayList<SpaceObject>();
        countId = 1;
    }

    public boolean addSpaceObject(SpaceObject spaceObject){
        countId++;
        return spaceObjects.add(spaceObject);
    }
    public boolean addSpaceObject(Bitmap image, double density, double direction,
                                  double velocity, double volume, float startingPositionX,
                                  float startingPositionY){
        return addSpaceObject(makeSpaceObject(image, density, direction, velocity, volume,
                startingPositionX, startingPositionY));
    }
    public SpaceObject makeSpaceObject(Bitmap image, double density,
                                       double direction, double velocity, double volume,
                                       float startingPositionX, float startingPositionY){
        return new SpaceObject(countId, image, density, direction, velocity, volume,
                startingPositionX, startingPositionY);
    }
    public boolean deleteSpaceObject(SpaceObject spaceObject){return spaceObjects.remove(spaceObject);}

    public void update(double seconds){
        refreshSpaceObjectsCoordinates(seconds);
    }

    public void draw(Canvas canvas){
        for (SpaceObject spaceObject: spaceObjects){
            spaceObject.draw(canvas);
        }
    }

    public SpaceObject findSpaceObject(int id){
        for (SpaceObject so: spaceObjects){
            if (so.getId() == id){
                return so;
            }
        }
        return null;
    }

    public ForceInterference calculateSpaceObjectsInterference(SpaceObject spaceObject){
        ForceInterference forceInterference = new ForceInterference(0, 0);
        double r;
        float forceX = 0, forceY = 0;

        for (SpaceObject so: spaceObjects){
            if (so == spaceObject) continue;

            r = Math.hypot(spaceObject.x - so.x, spaceObject.y - so.y);
            if( r!= 0) forceInterference.force = G * (spaceObject.getWeight() * so.getWeight()) / (r * r);

            forceInterference.direction = -Math.toDegrees(Math.atan2(so.y - spaceObject.y, so.x - spaceObject.x));
            if (forceInterference.direction < 0){forceInterference.direction += 360;}

            forceX += forceInterference.force*Math.cos(Math.toRadians(forceInterference.direction));
            forceY += -forceInterference.force*Math.sin(Math.toRadians(forceInterference.direction));
        }

        forceInterference.force = Math.hypot(forceX, forceY);
        forceInterference.direction = -Math.toDegrees(Math.atan2(forceY , forceX));
        if (forceInterference.direction < 0) {forceInterference.direction += 360;}

        return forceInterference;
    }

    public void refreshSpaceObjectsCoordinates(double seconds){
        ArrayList<ForceInterference> forceInterferences = new ArrayList<>();

        for (SpaceObject spaceObject: spaceObjects){
            forceInterferences.add(calculateSpaceObjectsInterference(spaceObject));
        }

        int i = 0;
        for (SpaceObject spaceObject: spaceObjects){
            spaceObject.updateCoordinates(forceInterferences.get(i), seconds);
            i++;
        }
    }

    public ArrayList<SpaceObject> getSpaceObjects(){return spaceObjects;}
}

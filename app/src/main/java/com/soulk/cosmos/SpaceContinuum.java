package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class SpaceContinuum {
    private int countId;
    private ArrayList<SpaceObject> spaceObjects;
    public static final double G = 1;

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
    public boolean addStaticSpaceObject(Bitmap image, double density, double direction,
                                  double velocity, double volume, float startingPositionX,
                                  float startingPositionY){
        return addSpaceObject(makeStaticSpaceObject(image, density, direction, velocity, volume,
                startingPositionX, startingPositionY));
    }
    public SpaceObject makeSpaceObject(Bitmap image, double density,
                                       double direction, double velocity, double volume,
                                       float startingPositionX, float startingPositionY){
        return new SpaceObject(countId, image, density, direction, velocity, volume,
                startingPositionX, startingPositionY);
    }
    public SpaceObject makeStaticSpaceObject(Bitmap image, double density,
                                       double direction, double velocity, double volume,
                                       float startingPositionX, float startingPositionY){
        return new StaticSpaceObject(countId, image, density, direction, velocity, volume,
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

    public Vector calculateSpaceObjectsGravityInterference(SpaceObject spaceObject){
        Vector gravityForceInterference = new Vector(0, 0);
        // distance between two space objects
        double r;
        double forceX = 0, forceY = 0;

        for (SpaceObject so: spaceObjects){
            if (so == spaceObject) continue;

            r = Math.hypot(spaceObject.x - so.x, spaceObject.y - so.y);
            if( r!= 0)
                gravityForceInterference.size = G * (spaceObject.getWeight() * so.getWeight()) / (r * r);

            gravityForceInterference.direction = -Math.toDegrees(Math.atan2(so.y - spaceObject.y, so.x - spaceObject.x));
            if (gravityForceInterference.direction < 0){gravityForceInterference.direction += 360;}

            forceX += gravityForceInterference.size * Math.cos(Math.toRadians(gravityForceInterference.direction));
            forceY += -gravityForceInterference.size * Math.sin(Math.toRadians(gravityForceInterference.direction));
        }

        gravityForceInterference.setByCoordinates(forceX, forceY);
        return gravityForceInterference;
    }

    public void refreshSpaceObjectsCoordinates(double seconds){
        ArrayList<Vector> forceInterferences = new ArrayList<>();

        for (SpaceObject spaceObject: spaceObjects){
            forceInterferences.add(calculateSpaceObjectsGravityInterference(spaceObject));
        }

        int i = 0;
        for (SpaceObject spaceObject: spaceObjects){
            spaceObject.updateCoordinates(forceInterferences.get(i), seconds);
            i++;
        }
    }

    public ArrayList<SpaceObject> getSpaceObjects(){return spaceObjects;}
}

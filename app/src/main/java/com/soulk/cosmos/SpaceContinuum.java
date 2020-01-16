package com.soulk.cosmos;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class SpaceContinuum {
    private int countId;
    private ArrayList<SpaceObject> spaceObjects;
    public static final double G = 6.67384*Math.pow(10, -11);

    public SpaceContinuum(){
        spaceObjects = new ArrayList<SpaceObject>();
        countId = 1;
    }

    public boolean addSpaceObject(SpaceObject spaceObject){
        countId++;
        return spaceObjects.add(spaceObject);
    }
    public boolean addSpaceObject(Bitmap image, double acceleration, double density,
                                  double velocity, double volume, double startingPositionX,
                                  double startingPositionY){
        return addSpaceObject(makeSpaceObject(image, acceleration, density, velocity, volume,
                startingPositionX, startingPositionY));
    }
    public SpaceObject makeSpaceObject(Bitmap image, double acceleration, double density,
                                       double velocity, double volume,
                                       double startingPositionX, double startingPositionY){
        return new SpaceObject(countId, image, acceleration, density, velocity, volume,
                startingPositionX, startingPositionY);
    }
    public boolean deleteSpaceObject(SpaceObject spaceObject){return spaceObjects.remove(spaceObject);}

    public SpaceObject findSpaceObject(int id){
        for (SpaceObject so: spaceObjects){
            if (so.getId() == id){
                return so;
            }
        }
        return null;
    }

    public double[] calculateSpaceObjectsInterference(SpaceObject spaceObject){
        double[] interference = new double[]{0, 0};
        double r, x, y;
        double a = 0, b = 0;

        for (SpaceObject so: spaceObjects){
            if (so == spaceObject) continue;

            y = spaceObject.getY() - so.getY();
            x = spaceObject.getX() - so.getX();

            r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            if( r!= 0) interference[0] = G * (spaceObject.getWeight() * so.getWeight()) / (r * r);

            interference[1] = Math.atan2(y, x);

            a += interference[0]*Math.cos(interference[1]);
            b += interference[0]*Math.sin(interference[1]);
        }

        interference[0] = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        interference[1] = Math.atan2(b, a);

        return interference;
    }

    public void refreshSpaceObjectsCoordinates(double time){
        ArrayList<double[]> interferences = new ArrayList<double[]>();

        for (SpaceObject so: spaceObjects){
            interferences.add(calculateSpaceObjectsInterference(so));
        }

        int i = 0;
        for (SpaceObject so: spaceObjects){
            so.updateCoordinates(interferences.get(i)[0], interferences.get(i)[1], time);
            i++;
        }
    }

    public ArrayList<SpaceObject> getSpaceObjects(){return spaceObjects;}
}

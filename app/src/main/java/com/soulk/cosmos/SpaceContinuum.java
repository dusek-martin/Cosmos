package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class SpaceContinuum {
    private int countId;
    private ArrayList<SpaceObject> spaceObjects;
    public static final double G = 6.673 * Math.pow(10, -1);

    public SpaceContinuum(){
        spaceObjects = new ArrayList<SpaceObject>();
    }

    public boolean addSpaceObject(SpaceObject spaceObject){
        countId++;
        return spaceObjects.add(spaceObject);
    }

    public boolean addSpaceObject(Bitmap image, double density, double volume,
                                  Vector position, Vector speed){
        return addSpaceObject(makeSpaceObject(image, density, volume,
                position, speed));
    }

    public boolean addStaticSpaceObject(Bitmap image, double density, double volume,
                                        Vector position, Vector speed){
        return addSpaceObject(makeStaticSpaceObject(image, density, volume, position, speed));
    }

    public SpaceObject makeSpaceObject(Bitmap image, double density, double volume,
                                       Vector position, Vector speed){
        return new SpaceObject(++countId, image, density, volume, position, speed);
    }

    public SpaceObject makeStaticSpaceObject(Bitmap image, double density, double volume,
                                       Vector position, Vector speed){
        return new StaticSpaceObject(++countId, image, density, volume, position, speed);
    }

    public boolean deleteSpaceObject(SpaceObject spaceObject){return spaceObjects.remove(spaceObject);}

    public SpaceObject findSpaceObject(int id){
        for (SpaceObject spaceObject: spaceObjects){
            if (spaceObject.getId() == id){
                return spaceObject;
            }
        }
        return null;
    }

    public void update(double seconds){
        //solveCollisions();
        updateSpaceObjects(seconds);
    }

    public void draw(Canvas canvas){
        for (SpaceObject spaceObject: spaceObjects){
            spaceObject.draw(canvas);
        }
    }

    // returns true if there is a collision between these two SpaceObjects
    public boolean checkCollision(SpaceObject spaceObject, SpaceObject so){
        double distance = Math.hypot(spaceObject.position.x - so.position.x, spaceObject.position.y - so.position.y);
        return distance <= ((spaceObject.getVolume()*0.85 + so.getVolume()*0.85) / 2);
    }

    //TODO OPRAVIT
    public void solveCollisions(){
        for (int i = 0; i < spaceObjects.size() - 1; i++){
            SpaceObject spaceObject = spaceObjects.get(i);
            for (int j = i + 1; j < spaceObjects.size(); j++){
                SpaceObject so = spaceObjects.get(j);

                if (checkCollision(spaceObject, so)){
                    Vector distance = Vector.subtractVectors(so.position, spaceObject.position);
                    double impactAngle = spaceObject.getSpeed().getAngle();
                    double outcomeAngle = distance.getAngle() - impactAngle + distance.getAngle() + Math.PI;
                    spaceObject.getSpeed().setAngle(outcomeAngle);

                    distance.setAngle(distance.getAngle() + Math.PI);
                    impactAngle = so.getSpeed().getAngle();
                    outcomeAngle = distance.getAngle() - impactAngle + distance.getAngle() + Math.PI;
                    spaceObject.getSpeed().setAngle(outcomeAngle);
                    so.getSpeed().setAngle(outcomeAngle);
                }
            }
        }
    }

    public Vector calcSOGravityInterference(SpaceObject spaceObject){
        Vector targetGravityForce = new Vector(0, 0);

        for (SpaceObject so: spaceObjects){
            if (so == spaceObject) continue;

            Vector gravityForce = Vector.subtractVectors(so.position, spaceObject.position);
            if (gravityForce.getSize() != 0)
                gravityForce.setSize(G * (spaceObject.getWeight() * so.getWeight()) / Math.pow(gravityForce.getSize(), 2));

            targetGravityForce = Vector.addVectors(targetGravityForce, gravityForce);
        }

        return targetGravityForce;
    }

    public void updateSpaceObjects(double seconds){
        ArrayList<Vector> forceInterferences = new ArrayList<>();

        for (SpaceObject spaceObject: spaceObjects){
            forceInterferences.add(calcSOGravityInterference(spaceObject));
        }

        int i = 0;
        for (SpaceObject spaceObject: spaceObjects){
            spaceObject.updatePosition(forceInterferences.get(i++), seconds);
        }
    }

    public ArrayList<SpaceObject> getSpaceObjects(){return spaceObjects;}
}

package com.soulk.cosmos;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class SpaceContinuum {
    private int countId;
    private ArrayList<Planet> planets;
    public static final double G = 6.673 * Math.pow(10, -1); // G = 6.673 * Math.pow(10, -11); -> s takhle malými čísly to blbě počítá

    public SpaceContinuum(){
        planets = new ArrayList<Planet>();
    }

        public boolean addPlanet (Planet planet){
        return planets.add(planet);
    }

        public boolean addPlanet (Bitmap image,double density, double radius,
        Vector position, Vector speed){
        return addPlanet(makePlanet(image, density, radius,
                position, speed));
    }

        public boolean addPlanet ( double density, double radius,
        Vector position, Vector speed){
        return addPlanet(makePlanet(density, radius,
                position, speed));
    }

        public boolean addStaticSpaceObject (Bitmap image,double density, double radius,
        Vector position, Vector speed){
        return addPlanet(makeStaticPlanet(image, density, radius, position, speed));
    }

        public boolean addStaticSpaceObject ( double density, double radius,
        Vector position, Vector speed){
        return addPlanet(makeStaticPlanet(density, radius, position, speed));
    }

        public Planet makePlanet (Bitmap image,double density, double radius,
        Vector position, Vector speed){
        return new Planet(++countId, image, density, radius, position, speed);
    }

        public Planet makePlanet ( double density, double radius,
        Vector position, Vector speed){
        return new Planet(++countId, density, radius, position, speed);
    }

        public StaticPlanet makeStaticPlanet (Bitmap image,double density, double radius,
        Vector position, Vector speed){
        return new StaticPlanet(++countId, image, density, radius, position, speed);
    }

        public StaticPlanet makeStaticPlanet ( double density, double radius,
        Vector position, Vector speed){
        return new StaticPlanet(++countId, density, radius, position, speed);
    }

    public boolean deleteSpaceObject(IGravityObject spaceObject){return planets.remove(spaceObject);}

    public Planet findPlanet(int id){
        for (Planet planet: planets){
            if (planet.getId() == id){
                return planet;
            }
        }
        return null;
    }

    public void update(Canvas canvas, double seconds, ArrayList<Shot> shots){
        solveAbsorption();
        solvePlanetHit(shots);
        updatePlanets(canvas, seconds);
    }

    public void draw(Canvas canvas){
        for (Planet planet: planets){
            planet.draw(canvas);
        }
    }

    public void updatePlanets(Canvas canvas, double seconds){
        ArrayList<Vector> forceInterferences = new ArrayList<>();

        for (Planet planet: planets){
            forceInterferences.add(calcSOGravityInterference(planet));
        }

        int i = 0;
        for (Planet planet: planets){
            planet.update(forceInterferences.get(i++), canvas, seconds);
        }
    }

    public void solveAbsorption(){
        ArrayList<ISpaceObject> planetsToDel = new ArrayList<ISpaceObject>();
        for (int i = 0; i < planets.size() - 1; i++){
            ISpaceObject spaceObject = planets.get(i);
            for (int j = i + 1; j < planets.size(); j++){
                ISpaceObject so = planets.get(j);

                double distance = Math.hypot(spaceObject.getPosition().x - so.getPosition().x, spaceObject.getPosition().y - so.getPosition().y);
                if (distance <= ((spaceObject.getRadius() * 0.9 + so.getRadius() * 0.9) / 2)){
                    if (spaceObject.getWeight() > so.getWeight()){
                        spaceObject.setSpeed(Vector.addVectors(spaceObject.getSpeed(), Vector.scaleVector(so.getSpeed(), (so.getWeight() / spaceObject.getWeight()))));
                        spaceObject.absorb(so);
                        planetsToDel.add(so);
                    } else {
                        so.setSpeed(Vector.addVectors(so.getSpeed(), Vector.scaleVector(spaceObject.getSpeed(), (spaceObject.getWeight() / so.getWeight()))));
                        so.absorb(spaceObject);
                        planetsToDel.add(spaceObject);
                    }
                }
            }
        }
        for (ISpaceObject so: planetsToDel){
            planets.remove(so);
        }
    }

/*
    // returns true if there is a collision between these two SpaceObjects
    public boolean checkCollision(IGravityObject spaceObject, IGravityObject so){
        double distance = Math.hypot(spaceObject.getPosition().x - so.getPosition().x, spaceObject.getPosition().y - so.getPosition().y);
        return distance <= ((spaceObject.getRadius() * 0.95 + so.getRadius() * 0.95) / 2);
    }

    public void solveCollisions(){
        ArrayList<IGravityObject> soToDel = new ArrayList<IGravityObject>();
        for (int i = 0; i < planets.size() - 1; i++){
            IGravityObject spaceObject = planets.get(i);
            for (int j = i + 1; j < planets.size(); j++){
                IGravityObject so = planets.get(j);

                if (checkCollision(spaceObject, so)){
                    Vector distance = Vector.subtractVectors(so.getPosition(), spaceObject.getPosition());
                    double impactAngle = spaceObject.getSpeed().getAngle();
                    double outcomeAngle = distance.getAngle() - impactAngle + distance.getAngle() + Math.PI;
                    spaceObject.getSpeed().setAngle(outcomeAngle);

                    distance.setAngle(distance.getAngle() + Math.PI);
                    impactAngle = so.getSpeed().getAngle();
                    outcomeAngle = distance.getAngle() - impactAngle + distance.getAngle() + Math.PI;
                    so.getSpeed().setAngle(outcomeAngle);
                }
            }
        }
    }
*/

    public Vector calcSOGravityInterference(IGravityObject spaceObject){
        Vector targetGravityForce = new Vector(0, 0);

        for (IGravityObject go: planets){
            if (go == spaceObject) continue;

            Vector gravityForce = Vector.subtractVectors(go.getPosition(), spaceObject.getPosition());
            if (gravityForce.getSize() != 0)
                gravityForce.setSize(G * (spaceObject.getWeight() * go.getWeight()) / Math.pow(gravityForce.getSize(), 2));

            targetGravityForce = Vector.addVectors(targetGravityForce, gravityForce);
        }

        return targetGravityForce;
    }

    private void solvePlanetHit(ArrayList<Shot> shots){
        ArrayList<Shot> shotsToDel = new ArrayList<Shot>();
        ArrayList<ISpaceObject> soToDel = new ArrayList<ISpaceObject>();
        ArrayList<Planet> planetsToMake = new ArrayList<Planet>();
        for (Shot shot: shots){
            for (ISpaceObject so: planets){
                if (Vector.subtractVectors(shot.position, so.getPosition()).getSize() < so.getRadius() * 0.9){
                    shotsToDel.add(shot);
                    if (!soToDel.contains(so)) {
                        soToDel.add(so);
                        double radiusOfOne = Math.sqrt(so.getWeight() / 2 / (so.getDensity() * Math.PI));
                        planetsToMake.add(makePlanet(so.getDensity(), radiusOfOne, Vector.addVectors(so.getPosition(), new Vector(so.getRadius(), (shot.speed.getAngle() + Math.PI / 2))), Vector.addVectors(new Vector((so.getSpeed().getSize() * 2),(double)(so.getSpeed().getAngle() + Math.PI / 2)), Vector.scaleVector(shot.speed, 0.5))));
                        planetsToMake.add(makePlanet(so.getDensity(), radiusOfOne, Vector.addVectors(so.getPosition(), new Vector(so.getRadius(), (shot.speed.getAngle() - Math.PI / 2))), Vector.addVectors(new Vector((so.getSpeed().getSize() * 2),(double)(so.getSpeed().getAngle() - Math.PI / 2)), Vector.scaleVector(shot.speed, 0.5))));
                    }
                }
            }
        }
        for (Shot shot: shotsToDel){
            shots.remove(shot);
        }
        for (ISpaceObject so: soToDel){
            planets.remove(so);
        }
        for (Planet planet: planetsToMake){
            planets.add(planet);
        }
    }

    public ArrayList<Planet> getPlanets(){return planets;}
}

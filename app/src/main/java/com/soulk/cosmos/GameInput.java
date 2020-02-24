package com.soulk.cosmos;


public class GameInput {
    public boolean left, right, up, down, fire;
    public Vector upPosition, leftPosition, rightPosition;


    public GameInput(){
        left = false;
        right = false;
        up = false;
        down = false;
        fire = false;

        upPosition = new Vector((float)(150), (float)(1600));
        leftPosition = new Vector((float)(150), (float)(150));
        rightPosition = new Vector((float)(150), (float)(300));
    }

    public void reset(){
        left = false;
        right = false;
        up = false;
        down = false;
        fire = false;
    }
}

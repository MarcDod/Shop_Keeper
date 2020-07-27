package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Blocker {

    private final BodyUserData data;
    private final Body body;

    /**
     * Requires body.getUserData() instance Of BodyUserData
     */
    public Blocker(final Body body){
        if(!(body.getUserData() instanceof BodyUserData))
            throw new IllegalArgumentException("body UserData has to be BodyUserData");
        this.body = body;
        this.data = (BodyUserData) body.getUserData();
    }

    public float getWidth(){
        return this.data.width;
    }
    public float getHeight(){
        return this.data.height;
    }

    public Vector2 getPosition(){
        return this.body.getPosition();
    }
}

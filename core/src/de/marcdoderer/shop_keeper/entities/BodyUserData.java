package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class BodyUserData {
    public final BodyDef bodyDef;
    public final FixtureDef fixtureDef;
    public final float width;
    public final float height;

    public BodyUserData(final BodyDef bodyDef, final FixtureDef fixtureDef, final float width, final float height){
        if(bodyDef == null || fixtureDef == null) throw new IllegalArgumentException();
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;
        this.width = width;
        this.height = height;
    }

}

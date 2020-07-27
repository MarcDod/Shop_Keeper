package de.marcdoderer.shop_keeper.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import de.marcdoderer.shop_keeper.entities.BodyUserData;
import de.marcdoderer.shop_keeper.movement.collision.Collision;

import java.util.Set;

public class Util {

    public static Body createBody(final float width, final float height, final float x, final float y, World world){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;
        def.position.set(x, y);
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2,height / 2);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;

        body.createFixture(fix);
        body.setUserData(new BodyUserData(def, fix, width, height));
        shape.dispose();
        return body;
    }

    public static Body createNoShadowBody(final float width, final float height, final float x, final float y, World world){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;
        def.position.set(x, y);
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2,height / 2);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;
        fix.filter.categoryBits = Collision.LIGHT;
        fix.filter.groupIndex = Collision.LIGHT_GROUP;
        fix.filter.maskBits = Collision.MASK_LIGHTS;


        body.createFixture(fix);
        body.setUserData(new BodyUserData(def, fix, width, height));
        shape.dispose();
        return body;
    }

    public static Body crateKinematicBody(final float width, final float height, final float x, final float y, World world){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.KinematicBody;
        def.fixedRotation = true;
        def.position.set(x, y);
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2,height / 2);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;
        fix.isSensor = true;

        body.createFixture(fix);
        body.setUserData(new BodyUserData(def, fix, width, height));
        shape.dispose();
        return body;
    }

    public static Rectangle getBoundingBox(Set<Rectangle> rectangles){
        float x = Float.MAX_VALUE;
        float y = Float.MAX_VALUE;
        float width = -Float.MAX_VALUE;
        float height = -Float.MAX_VALUE;
        for(Rectangle rec : rectangles){
            if(rec.getX() < x) {
                x = rec.getX();
            }
            if(rec.getY() < y)
                y = rec.getY();
            if(rec.getX() + rec.getWidth() > width)
                width = rec.getX() + rec.width;
            if(rec.getY() + rec.getHeight() > height)
                height = rec.getY() + rec.getHeight();
        }
        return new Rectangle(x, y, width - x, height - y);
    }
}

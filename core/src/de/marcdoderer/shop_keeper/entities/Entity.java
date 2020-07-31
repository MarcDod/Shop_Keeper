package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.marcdoderer.shop_keeper.manager.EntityData;


/**
 * A standart Entity with a position and a Texture
 */
public class Entity {

    protected final Body body;
    protected final BodyUserData data;
    protected final Sprite sprite;
    protected EntityFactory.EntityType type;
    private final String entityType;


    /**
     * Requires body.getUserData() instance Of BodyUserData
     */
    public Entity(final Sprite sprite, final Body body, final String entityType){
        if(!(body.getUserData() instanceof BodyUserData))
            throw new IllegalArgumentException("body UserData has to be BodyUserData");
        sprite.setOriginBasedPosition(body.getPosition().x, body.getPosition().y);
        this.sprite = sprite;
        this.body = body;
        this.data = (BodyUserData) body.getUserData();
        this.type = EntityFactory.EntityType.ENTITY;
        this.entityType =  entityType;
    }

    public EntityData getEntityData(){
        EntityData data = new EntityData();
        data.setWidth(getWidth());
        data.setHeight(getHeight());
        data.setPosX(getPosition().x);
        data.setPosY(getPosition().y);
        data.setType(type);
        data.setName(entityType);
        return data;
    }

    public void render(SpriteBatch batch){
        Vector2 position = body.getPosition();
        sprite.setOriginBasedPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void update(float delta){};

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void dispose(){
        this.body.getWorld().destroyBody(this.body);
    }

    public Sprite getSprite(){
        return this.sprite;
    }


    public void teleportTo(Vector2 vec){
        this.body.setTransform(vec, 0);
    }
    public void teleportTo(final float x, final float y){
        teleportTo(new Vector2(x, y));
    }

    public float getWidth(){
        return this.data.width;
    }

    public float getHeight(){
        return this.data.height;
    }
}

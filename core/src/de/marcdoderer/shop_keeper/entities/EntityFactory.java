package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;

public class EntityFactory {
    public enum EntityType{
        ENTITY, ITEM_CARRYING, MOVABLE, MOVABLE_ITEM_CARRYING
    }

    public final static String atlasUrl = "shop/entity/atlas/shopEntity.atlas";
    private final GameState gameState;

    public EntityFactory(final GameState gameState){
        this.gameState = gameState;
    }

    public Entity createEntity(final EntityType type, final Vector2 position, final float width, final float height, final String name, World world){
        return createEntity(type, name, position, width, height, world);
    }

    private Entity createEntity(final EntityType type, final String name, Vector2 position, float width, final float height, World world){
        TextureAtlas atlas = gameState.screen.assetManager.get(atlasUrl);
        TextureRegion region = atlas.findRegion(name);
        Sprite sprite = new Sprite(region);
        sprite.setSize(width, height);
        sprite.setOriginCenter();

        Body body = Util.createBody(width, height, position.x, position.y, world);

        if(type == EntityType.ENTITY)
            return new Entity(sprite, body, name);
        if(type == EntityType.ITEM_CARRYING)
            return new ItemCarryingEntity(sprite, body, name);
        if(type == EntityType.MOVABLE)
            return new MovableEntity(sprite, body, new MoveAnimation(sprite), new IdleAnimation(sprite));
        if(type == EntityType.MOVABLE_ITEM_CARRYING)
            return null;
        return null;
    }
}

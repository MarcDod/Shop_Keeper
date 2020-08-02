package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.entities.specialEntity.Chest;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.manager.HitboxScalarManager;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;

public class EntityFactory {
    public enum EntityType{
        ENTITY, ITEM_CARRYING, MOVABLE, MOVABLE_ITEM_CARRYING, CHEST
    }

    public final static String atlasUrl = "shop/entity/atlas/shopEntity.atlas";
    private final GameState gameState;

    public EntityFactory(final GameState gameState){
        this.gameState = gameState;
        HitboxScalarManager.getHitboxScalarManager(gameState.screen.assetManager).loadScalars();
    }


    public Entity createEntity(final EntityData eData, World world){
        TextureAtlas atlas = gameState.screen.assetManager.get(atlasUrl);
        //TODO: make entity creation dynamic this is just for test purpose

        String name = eData.getName();
        float width = eData.getWidth();
        float height = eData.getHeight();
        float posX = eData.getPosX();
        float posY = eData.getPosY();
        EntityType type = eData.getType();
        HitboxScalarData hData = HitboxScalarManager.getHitboxScalarManager().getScalar(name);
        TextureRegion region = atlas.findRegion(name);

        Sprite sprite = new Sprite(region);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setOrigin(sprite.getOriginX() + sprite.getWidth() * hData.getPosScalarX(),
                sprite.getOriginY() - (height - hData.getScalarY() * height)/2f + sprite.getHeight() * hData.getPosScalarY());


        Body body = Util.createBody(hData.getScalarX() * width, hData.getScalarY() * height,
                posX, posY, world);


        if(type == EntityType.ENTITY)
            return new Entity(sprite, body, name);
        if(type == EntityType.ITEM_CARRYING)
            return new ItemCarryingEntity(sprite, body, name);
        if(type == EntityType.MOVABLE)
            return new MovableEntity(sprite, body, new MoveAnimation(sprite), new IdleAnimation(sprite));
        if(type == EntityType.MOVABLE_ITEM_CARRYING)
            return null;
        if(type == EntityType.CHEST){
            return new Chest(sprite, body, name, (Chest.ChestData) eData);
        }
        return null;
    }
}

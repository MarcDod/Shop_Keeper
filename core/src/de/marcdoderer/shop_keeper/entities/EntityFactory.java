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
import de.marcdoderer.shop_keeper.entities.specialEntity.Tree;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.manager.SpriteDataManager;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Garden;
import de.marcdoderer.shop_keeper.util.Util;

public class EntityFactory {
    public enum EntityType{
        ENTITY, ITEM_CARRYING, MOVABLE, MOVABLE_ITEM_CARRYING, CHEST, TREE
    }

    public final static String atlasUrl = "shop/entity/atlas/shopEntity.atlas";
    private final GameState gameState;

    public EntityFactory(final GameState gameState){
        this.gameState = gameState;
        SpriteDataManager.getSpriteManager(gameState.screen.assetManager).loadSpriteDatas();
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
        SpriteData sData = SpriteDataManager.getSpriteDataManager().getSpriteData(name);
        TextureRegion region = atlas.findRegion(name);

        Sprite sprite = new Sprite(region);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setOrigin(sprite.getOriginX() + sprite.getWidth() * sData.getPosScalarX(),
                sprite.getOriginY() - (height - sData.getScalarY() * height)/2f + sprite.getHeight() * sData.getPosScalarY());


        Body body = Util.createBody(sData.getScalarX() * width, sData.getScalarY() * height,
                posX, posY, world);


        if(type == EntityType.ENTITY)
            return new Entity(sprite, body, name);
        if(type == EntityType.ITEM_CARRYING)
            return new ItemCarryingEntity(sprite, body, name, new Vector2(sData.getItemX(), sData.getItemY()));
        if(type == EntityType.MOVABLE)
            return new MovableEntity(sprite, body, new MoveAnimation(sprite), new IdleAnimation(sprite));
        if(type == EntityType.MOVABLE_ITEM_CARRYING)
            return null;
        if(type == EntityType.CHEST)
            return new Chest(sprite, body, name, (Chest.ChestData) eData);
        if(type == EntityType.TREE) {
            Tree.TreeData tData = (Tree.TreeData) eData;
            TextureRegion bloomingTreeRegion = atlas.findRegion(tData.getBloomingName());
            Sprite bloomingTree = new Sprite(bloomingTreeRegion);
            bloomingTree.setSize(sprite.getWidth(), sprite.getHeight());
            bloomingTree.setOriginCenter();
            bloomingTree.setOrigin(sprite.getOriginX(), sprite.getOriginY());
            return new Tree(sprite, bloomingTree, body, name, new Vector2(sData.getItemX(), sData.getItemY()), gameState, tData);
        }
        return null;
    }
}

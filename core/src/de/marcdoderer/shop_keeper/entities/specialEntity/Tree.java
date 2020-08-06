package de.marcdoderer.shop_keeper.entities.specialEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;

public class Tree extends ItemCarryingEntity {
    private final GameState gameState;
    private final String prodicungItemID;
    private final int placeID;

    //TODO: make a currect time
    private final float spawnTime;
    private float currentTimeInSeconds;

    /**
     * Requires body.getUserData() instance Of BodyDef
     * Requires body.getFixtureList().first().getUserData instance Of FixtureDef
     *
     * @param sprite     sprite of the Entity
     * @param body       body of the Entity
     * @param entityType
     * @param itemOffset
     */
    public Tree(Sprite sprite, Body body, String entityType, Vector2 itemOffset, GameState gameState, TreeData treeData) {
        super(sprite, body, entityType, itemOffset);
        this.type = EntityFactory.EntityType.TREE;
        this.gameState = gameState;
        this.prodicungItemID = treeData.producingItem;
        this.placeID = treeData.placeID;

        this.spawnTime = treeData.spawnTime;
        this.currentTimeInSeconds = treeData.currentTimeInSeconds;
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        if(carriedItem == null)
            this.currentTimeInSeconds += delta * gameState.dayNightCircle.speedUp;

        if(carriedItem == null && this.currentTimeInSeconds > spawnTime){
            Item producedItem = ItemFactory.getItemRegistry().createItemByID(prodicungItemID);
            gameState.getPlace(placeID).addEntity(EntityManager.Layer.ITEM_LAYER, producedItem);
            carryItem(producedItem);
            this.currentTimeInSeconds = 0;
        }
    }

    @Override
    public EntityData getEntityData() {
        EntityData eData = super.getEntityData();
        TreeData treeData = new TreeData();
        treeData.setEntityData(eData);
        treeData.producingItem = prodicungItemID;
        treeData.currentTimeInSeconds = currentTimeInSeconds;
        treeData.placeID = placeID;
        treeData.spawnTime = spawnTime;

        return treeData;
    }

    public static class TreeData extends EntityData{
        private String producingItem;
        private int placeID;
        private float spawnTime;
        private float currentTimeInSeconds;

        private void setEntityData(EntityData eData){
            this.setType(eData.getType());
            this.setName(eData.getName());
            this.setCarriedItemData(eData.getCarriedItemData());
            this.setHeight(eData.getHeight());
            this.setWidth(eData.getWidth());
            this.setPosX(eData.getPosX());
            this.setPosY(eData.getPosY());
        }

        public void setProducingItem(String producingItem){
            this.producingItem = producingItem;
        }

        public void setPlaceID(int placeID){
            this.placeID = placeID;
        }

        public void setSpawnTime(float spawnTime){
            this.spawnTime = spawnTime;
        }

        public void setCurrentTimeInSeconds(float currentTimeInSeconds){
            this.currentTimeInSeconds = currentTimeInSeconds;
        }
    }
}

package de.marcdoderer.shop_keeper.entities.specialEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.manager.EntityData;

import java.util.*;

public class Chest extends ItemCarryingEntity {

    private final Map<Integer, String> itemIDs;
    private int carriedItemPos;

    /**
     * Requires body.getUserData() instance Of BodyDef
     * Requires body.getFixtureList().first().getUserData instance Of FixtureDef
     *
     * @param sprite     sprite of the Entity
     * @param body       body of the Entity
     * @param entityType
     */
    public Chest(Sprite sprite, Body body, String entityType, ChestData cData) {
        super(sprite, body, entityType);
        this.itemIDs = new HashMap<>();
        if(cData.savedItemsIds != null) {
            for (ItemPosTuple tuple : cData.savedItemsIds) {
                itemIDs.put(tuple.pos, tuple.id);
            }
        }
    }

    public Map<Integer, String> getItemIDs(){
        return this.itemIDs;
    }

    @Override
    public void render(SpriteBatch batch) {
        renderWithoutItem(batch);
    }

    @Override
    public void carryItem(Item item) {
        int pos = 0;
        for(int i = 0; i < itemIDs.size(); i++){
            if(itemIDs.containsKey(i)){
                pos = i + 1;
            }else{
                break;
            }
        }
        this.itemIDs.put(pos, item.id);
    }

    public void carryItem(Item item, int pos){
        this.itemIDs.put(pos, item.id);
    }

    public void selectItem(final int index){
        this.carriedItemPos = index;
        this.carriedItem = ItemFactory.getItemRegistry().createItemByID(itemIDs.get(index));
    }

    @Override
    public void removeCarriedItem() {
        this.itemIDs.remove(carriedItemPos);
        super.removeCarriedItem();
    }

    @Override
    public EntityData getEntityData() {
        EntityData eData = super.getEntityData();
        ChestData cData = new ChestData();
        cData.setEntityData(eData);
        if(itemIDs.size() > 0)
            cData.setSavedItems(itemIDs);

        return cData;
    }

    private static class ItemPosTuple{
        private int pos;
        private String id;
        public ItemPosTuple() {

        }
    }

    public static class ChestData extends EntityData{

        private ItemPosTuple[] savedItemsIds;

        private void setSavedItems(final Map<Integer, String> ids){
            savedItemsIds = new ItemPosTuple[ids.size()];
            int i = 0;
            for(int pos : ids.keySet()){
                savedItemsIds[i] = new ItemPosTuple();
                savedItemsIds[i].pos = pos;
                savedItemsIds[i].id = ids.get(pos);
                i++;
            }
        }

        private void setEntityData(final EntityData entityData){
            this.setHeight(entityData.getHeight());
            this.setWidth(entityData.getWidth());
            this.setName(entityData.getName());
            this.setType(entityData.getType());
            this.setPosX(entityData.getPosX());
            this.setPosY(entityData.getPosY());
        }

    }
}

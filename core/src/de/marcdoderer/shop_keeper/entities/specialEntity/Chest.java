package de.marcdoderer.shop_keeper.entities.specialEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.manager.ItemData;

import java.util.*;

public class Chest extends ItemCarryingEntity {

    private final Map<Integer, ItemData> itemDatas;
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
        super(sprite, body, entityType, new Vector2(0, 0));
        this.itemDatas = new HashMap<>();
        if(cData.savedItemsIds != null) {
            for (ItemPosTuple tuple : cData.savedItemsIds) {
                itemDatas.put(tuple.pos, tuple.data);
            }
        }
        this.type = EntityFactory.EntityType.CHEST;
    }

    public Map<Integer, ItemData> getItemDatas(){
        return this.itemDatas;
    }

    @Override
    public void render(SpriteBatch batch) {
        renderWithoutItem(batch);
    }

    @Override
    public void carryItem(Item item) {
        int pos = 0;
        for(int i = 0; i < itemDatas.size(); i++){
            if(itemDatas.containsKey(i)){
                pos = i + 1;
            }else{
                break;
            }
        }
        this.itemDatas.put(pos, item.createItemData());
    }

    public void carryItem(Item item, int pos){
        this.itemDatas.put(pos, item.createItemData());
    }

    public void selectItem(final int index){
        this.carriedItemPos = index;
        this.carriedItem = ItemFactory.getItemRegistry().createItemByID(itemDatas.get(index).itemID);
        carriedItem.setStackCount(itemDatas.get(index).stackCount);
    }

    @Override
    public void removeCarriedItem() {
        this.itemDatas.remove(carriedItemPos);
        super.removeCarriedItem();
    }

    @Override
    public EntityData getEntityData() {
        EntityData eData = super.getEntityData();
        ChestData cData = new ChestData();
        cData.setEntityData(eData);
        if(itemDatas.size() > 0)
            cData.setSavedItems(itemDatas);

        return cData;
    }

    private static class ItemPosTuple{
        private int pos;
        private ItemData data;
        public ItemPosTuple() {

        }
    }

    public static class ChestData extends EntityData{

        private ItemPosTuple[] savedItemsIds;

        private void setSavedItems(final Map<Integer, ItemData> ids){
            savedItemsIds = new ItemPosTuple[ids.size()];
            int i = 0;
            for(int pos : ids.keySet()){
                savedItemsIds[i] = new ItemPosTuple();
                savedItemsIds[i].pos = pos;
                savedItemsIds[i].data = ids.get(pos);
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

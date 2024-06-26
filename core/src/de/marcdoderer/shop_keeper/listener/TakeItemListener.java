package de.marcdoderer.shop_keeper.listener;

import de.marcdoderer.shop_keeper.entities.Character;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.items.ItemCarry;
import de.marcdoderer.shop_keeper.movement.EntityZone;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class TakeItemListener implements ZoneListener {

    private GameState gameState;

    public TakeItemListener(final GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void perform(InteractiveZone source, int eventID, Character interactedCharacter) {
        if(!(source instanceof EntityZone)) throw new IllegalArgumentException("source has to be a EntityZone");
        Entity entity = ((EntityZone) source).getEntity();
        if(!(entity instanceof ItemCarry)) throw new IllegalArgumentException("entity on this has to be a ItemCarry");
        ItemCarry itemCarry = (ItemCarry) entity;


        if(itemCarry.getCarriedItem() == null) return;
        if(interactedCharacter.getCarriedItem() == null){
            interactedCharacter.carryItem(itemCarry.getCarriedItem());
            if(!gameState.getCurrentPlace().hasEntity(itemCarry.getCarriedItem())){
                gameState.getPlace(interactedCharacter.getCurrentPlaceID()).addEntity(EntityManager.Layer.ITEM_LAYER, itemCarry.getCarriedItem());
            }
            itemCarry.removeCarriedItem();
        }else{
            if(interactedCharacter.getCarriedItem().id.equals(itemCarry.getCarriedItem().id)){
                interactedCharacter.getCarriedItem().setStackCount(interactedCharacter.getCarriedItem().getStackCount() + itemCarry.getCarriedItem().getStackCount());
                itemCarry.getCarriedItem().dispose();
                gameState.getPlace(interactedCharacter.getCurrentPlaceID()).removeEntity(EntityManager.Layer.ITEM_LAYER, itemCarry.getCarriedItem());
                itemCarry.removeCarriedItem();
            }
        }

    }
}

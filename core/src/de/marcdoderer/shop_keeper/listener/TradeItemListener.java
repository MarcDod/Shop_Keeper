package de.marcdoderer.shop_keeper.listener;

import de.marcdoderer.shop_keeper.entities.Character;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.Player;
import de.marcdoderer.shop_keeper.movement.EntityZone;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Place;

public class TradeItemListener implements ZoneListener{

    private final GameState gameState;

    public TradeItemListener(final GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void perform(InteractiveZone source, int eventID, Character interactedCharacter) {
        if(!(source instanceof EntityZone)) throw new IllegalArgumentException("zone has to be from EntityZone class");
        final Entity entity = ((EntityZone) source).getEntity();
        if(!(entity instanceof ItemCarryingEntity)) throw new IllegalArgumentException("Entity has to be from ItemCarryingEntity class");
        final ItemCarryingEntity entityWithItem = (ItemCarryingEntity) entity;

        if(entityWithItem.getCarriedItem() != null && interactedCharacter.getCarriedItem() != null || entityWithItem.getCarriedItem() == null && interactedCharacter.getCarriedItem() == null) return;

        if(entityWithItem.getCarriedItem() != null){
            interactedCharacter.carryItem(entityWithItem.getCarriedItem());
            if(!gameState.getPlace(interactedCharacter.getCurrentPlaceID()).hasEntity(entityWithItem.getCarriedItem())) {
                gameState.getPlace(interactedCharacter.getCurrentPlaceID()).addEntity(EntityManager.Layer.ITEM_LAYER, entityWithItem.getCarriedItem());
            }
            entityWithItem.removeCarriedItem();
        }else{
            entityWithItem.carryItem(interactedCharacter.getCarriedItem());
            interactedCharacter.removeCarriedItem();
        }
    }
}

package de.marcdoderer.shop_keeper.listener;

import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.Player;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.movement.ExitZone;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Place;

public class ExitZoneListener implements ZoneListener{

    private final GameState gameState;

    public ExitZoneListener(GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void perform(InteractiveZone source, int eventID) {
        if(!(source instanceof ExitZone)) throw new IllegalArgumentException("source has to be form class ExitZone");
        ExitZone exitZone = (ExitZone) source;

        final Place place = gameState.getCurrentPlace();
        final Place nextPlace = gameState.getPlace(exitZone.nextPlaceID);
        final Player player = gameState.player;
        gameState.setCameraTo(nextPlace.getPosition());
        final Item carriedItem = player.getCarriedItem();

        place.removeEntity(EntityManager.Layer.ENTITY_LAYER, player);
        nextPlace.addEntity(EntityManager.Layer.ENTITY_LAYER, player);

        player.teleportTo(nextPlace.getGraph().getNodeMetaData(exitZone.nextZoneID).getCenter());
        if(carriedItem != null){
            place.removeEntity(EntityManager.Layer.ITEM_LAYER, carriedItem);
            nextPlace.addEntity(EntityManager.Layer.ITEM_LAYER, carriedItem);
            player.carryItem(carriedItem);
        }
        player.setCurrentZoneID(exitZone.nextZoneID);


        gameState.changeCurrentPlace(exitZone.nextPlaceID);
    }
}

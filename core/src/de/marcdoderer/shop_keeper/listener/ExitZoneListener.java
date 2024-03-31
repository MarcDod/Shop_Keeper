package de.marcdoderer.shop_keeper.listener;

import de.marcdoderer.shop_keeper.entities.Character;
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
    public void perform(InteractiveZone source, int eventID, Character interactedCharacter) {
        if(!(source instanceof ExitZone)) throw new IllegalArgumentException("source has to be form class ExitZone");
        ExitZone exitZone = (ExitZone) source;

        final Place place = gameState.getPlace(interactedCharacter.getCurrentPlaceID());
        final Place nextPlace = gameState.getPlace(exitZone.nextPlaceID);

        gameState.setCameraTo(nextPlace.getPosition());
        final Item carriedItem = interactedCharacter.getCarriedItem();

        place.removeEntity(EntityManager.Layer.ENTITY_LAYER, interactedCharacter);
        nextPlace.addEntity(EntityManager.Layer.ENTITY_LAYER, interactedCharacter);

        interactedCharacter.teleportTo(nextPlace.getGraph().getNodeMetaData(exitZone.nextZoneID).getCenter());
        if(carriedItem != null){
            place.removeEntity(EntityManager.Layer.ITEM_LAYER, carriedItem);
            nextPlace.addEntity(EntityManager.Layer.ITEM_LAYER, carriedItem);
            interactedCharacter.carryItem(carriedItem);
        }
        interactedCharacter.setCurrentZoneID(exitZone.nextZoneID);
        interactedCharacter.setCurrentPlaceID(nextPlace.getID());

        if(eventID == PLAYER_WALKED_ONTO || eventID == PLAYER_CLICKED_AGAIN){
            gameState.changeCurrentPlace(exitZone.nextPlaceID);
        }
    }
}

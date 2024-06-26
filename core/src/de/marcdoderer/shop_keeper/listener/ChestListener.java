package de.marcdoderer.shop_keeper.listener;

import com.badlogic.gdx.Gdx;
import de.marcdoderer.shop_keeper.entities.Character;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.specialEntity.Chest;
import de.marcdoderer.shop_keeper.movement.EntityZone;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class ChestListener implements ZoneListener {

    private final GameState gameState;

    public ChestListener(final GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void perform(InteractiveZone source, int eventID, Character interactedCharacter) {
        if(!(source instanceof EntityZone)) throw new IllegalArgumentException("zone has to be from EntityZone class");
        EntityZone eZone = (EntityZone) source;
        if(!(eZone.getEntity() instanceof Chest)) throw new IllegalArgumentException("entity on this zone has to be a chest");

        if(eventID == PLAYER_WALKED_ONTO && interactedCharacter.getCarriedItem() != null) {
            final Item item = interactedCharacter.getCarriedItem();
            gameState.tradeItemListener.perform(source, 0, interactedCharacter);
            gameState.getCurrentPlace().removeEntity(EntityManager.Layer.ITEM_LAYER, item);
            item.dispose();
        }else if(eventID == PLAYER_CLICKED_AGAIN || eventID == PLAYER_WALKED_ONTO){
            gameState.openInventar((Chest) eZone.getEntity());
        }
    }
}

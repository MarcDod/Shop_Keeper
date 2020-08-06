package de.marcdoderer.shop_keeper.listener;

import de.marcdoderer.shop_keeper.entities.Character;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;

public interface ZoneListener {

     public static final int PLAYER_WALKED_ONTO = 0;
     public static final int PLAYER_CLICKED_AGAIN = 1;
     public static final int CHARACTER_WALKED_ONTO = 2;

     void perform(InteractiveZone source, int eventID, Character interactedCharacter);
}

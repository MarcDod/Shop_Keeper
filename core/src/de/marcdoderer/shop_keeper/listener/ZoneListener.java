package de.marcdoderer.shop_keeper.listener;

import de.marcdoderer.shop_keeper.movement.InteractiveZone;

public interface ZoneListener {

     void perform(InteractiveZone source, int eventID);
}

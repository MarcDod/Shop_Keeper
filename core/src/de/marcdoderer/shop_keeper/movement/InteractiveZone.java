package de.marcdoderer.shop_keeper.movement;

import de.marcdoderer.shop_keeper.listener.ZoneListener;

/**
 * a Zone that will start a Event
 */
public class InteractiveZone extends Zone{

    private final ZoneListener zoneListener;

    public InteractiveZone(float x, float y, float width, float height, int zoneID, ZoneListener zoneListener) {
        super(x, y, width, height, zoneID);
        this.zoneListener = zoneListener;
    }

    @Override
    public void startEvent(final int eventID) {
        zoneListener.perform(this, eventID);
    }
}

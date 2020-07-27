package de.marcdoderer.shop_keeper.movement;

import de.marcdoderer.shop_keeper.listener.ExitZoneListener;

/**
 *
 */
public class ExitZone extends InteractiveZone {

    public final int nextPlaceID;
    public final int nextZoneID;

    public ExitZone(float x, float y, float width, float height, int zoneID, final int nextZoneID, final int nextPlaceID, ExitZoneListener listener) {
        super(x, y, width, height, zoneID, listener);
        this.nextZoneID = nextZoneID;
        this.nextPlaceID = nextPlaceID;
    }

}

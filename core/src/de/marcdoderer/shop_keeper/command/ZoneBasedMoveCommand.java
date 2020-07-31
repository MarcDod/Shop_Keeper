package de.marcdoderer.shop_keeper.command;

import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.entities.Character;
import de.marcdoderer.shop_keeper.movement.Zone;

import java.util.LinkedList;
import java.util.List;

public class ZoneBasedMoveCommand extends MoveCommand {

    private final List<Zone> destinationsZones;

    /**
     * the Entity will stay at the exactEndPos
     * @param entity
     * @param destinationsZones
     */
    public ZoneBasedMoveCommand(final Character entity, final List<Zone> destinationsZones) {
        super(entity, null);
        this.destinationsZones = destinationsZones;
        List<Vector2> movePoints = new LinkedList<Vector2>();
        for(Zone destZone : destinationsZones){
            movePoints.add(destZone.getCenter());
        }

        this.destinations = movePoints;
    }

    public Zone getDestinationZone(){
        return this.destinationsZones.get(this.destinationsZones.size() - 1);
    }

    @Override
    protected void reachedADestination(){
        Character character = (Character) entity;
        character.setCurrentZoneID(destinationsZones.get(currentPosition).getZoneID());
        super.reachedADestination();
    }
}

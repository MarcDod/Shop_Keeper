package de.marcdoderer.shop_keeper.movement;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.astar.Position;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.listener.ExitZoneListener;
import de.marcdoderer.shop_keeper.listener.ZoneListener;

/**
 * The Zone is used to lead the characters through the Shop
 *
 * the Zone has no event if the Player stand on it
 *
 */
public class Zone extends Position {

    //position, width and height of the Zone
    private final Rectangle body;
    // the zone id is used to keep track of a zone in one graph
    private final int zoneID;

    /**
     *  Initialize the Zone
     * @param x the x pos of the Zone
     * @param y the y pos of the Zone
     * @param width the width of the Zone
     * @param height the height of the Zone
     * @param zoneID the zoneId of the Zone. One Graph should not have to Zones with the same ID
     */
    public Zone(final float x, final float y, final float width, final float height, final int zoneID) {
        this.body = new Rectangle(x, y, width, height);
        this.zoneID = zoneID;
    }

    /**
     * draws a Rectangle
     * @param shapeRenderer the renderer on witch the Rectangle should be drawn
     */
    public void drawShape(ShapeRenderer shapeRenderer){
        shapeRenderer.rect(body.x, body.y, body.width, body.height);
    }

    /**
     * returns the Center Point of the Rectangle
     * @return the center of the Rectangle
     */
    public Vector2 getCenter(){
        return this.body.getCenter(new Vector2());
    }

    /**
     *
     * calculates weather the point is int the Zone Rectangle or not
     * @param vec the point which should be checked
     * @return true if point in Rectangle else false
     */
    public boolean contains(final Vector2 vec){
        return this.body.contains(vec.x, vec.y);
    }

    @Override
    public int getX() {
        return (int) body.getX();
    }

    @Override
    public int getY() {
        return (int) body.getY();
    }

    public int getZoneID(){
        return this.zoneID;
    }

    /**
     * @return the bottom left corner of the Zone rectangle
     */
    public Vector2 getPosition(){
        return new Vector2(body.getX(), body.getY());
    }

    /**
     * Does nothing in this class
     */
    public void startEvent(){ };

    /**
     * Creats a copy of this Zone. but it is a ExitZone
     * @param nextZoneID the Id of the zone that the exitZone should lead to.
     * @param nextPlaceID the Place id the ExitZone should lead to.
     * @param listener ExitZoneListener
     * @return a copy of this object
     */
    public ExitZone makeExitZoneCopy(final int nextZoneID, final int nextPlaceID, final ExitZoneListener listener){
         return new ExitZone(body.x, body.y, body.width, body.height, zoneID, nextZoneID, nextPlaceID, listener);
    }

    /**
     * Creats a copy of this Zone. but it is a EntityZone
     * @param entity the Entity that can interact with this Zone
     * @param listener the event that will be player if the Player lands on this Zone.
     * @return a copy of this Object
     */
    public EntityZone makeEntityZoneCopy(final Entity entity, final ZoneListener listener){
        return new EntityZone(body.x, body.y, body.width, body.height, zoneID, entity, listener);
    }
}

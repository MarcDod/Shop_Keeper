package de.marcdoderer.shop_keeper.movement;

import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.astar.IEdge;
import de.marcdoderer.shop_keeper.astar.ShortestPath;
import de.marcdoderer.shop_keeper.astar.WeightedGraph;
import de.marcdoderer.shop_keeper.command.ZoneBasedMoveCommand;
import de.marcdoderer.shop_keeper.entities.MovableEntity;
import de.marcdoderer.shop_keeper.entities.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * The PlayerController manages the Player movement and actions.
 *
 * PlayerController moves the Player to a Tile that has be clicked.
 * it also runs the event of the Zone if the Player reached its destination
 */
public class PlayerController {

    //The Player that is controlled.
    private final Player player;
    // if null the player is not getting moved else it will move the player to a Zone.
    private ZoneBasedMoveCommand move;
    // if not null the Player clicked while a move was running on a next Zone
    private Zone clickedRequestZone;
    // The Graph of the current place the player is in. Needs to be updated every time the player changes the place.
    private WeightedGraph<Zone, Integer> graph;
    // enable or disable player control.
    private boolean active;

    /**
     * Initialises the Player, move and graph to null and active to false.
     * @param player the player that should be controlled
     */
    public PlayerController(final Player player){
        this.player = player;
        this.move = null;
        this.graph = null;
        this.active = false;
        this.clickedRequestZone = null;
    }

    public void setActive(final boolean active){
        this.active = active;
    }

    public boolean isActive(){
        return this.active;
    }

    public void setGraph(WeightedGraph<Zone, Integer> graph) {
        this.graph = graph;
    }

    /**
     * Requires graph is the graph of the place the Player is currently in.
     * moves player to the clicked Zone if isActive();
     *
     * if the pos it the Zone the player is standing at the moment the Zone event will be played.
     *
     * creates a Move the moves the Player to the clicked Zone
     * @param pos vec pos
     */
    public void clickEvent(final Vector2 pos){
        if(graph == null) throw new IllegalArgumentException("graph should not be null");
        if(!isActive()) return;

        final Zone clickedZone = getClickedZone(pos);
        if(clickedZone == null) return;
        if(clickedZone.getZoneID() == player.getCurrentZoneID()){
            if(move == null)
                clickedZone.startEvent(1);
            return;
        }


        if(move == null) {
            move = createMove(clickedZone);
        }else{
            if(clickedZone != move.getDestinationZone()) {
                move.requestCancel();
                clickedRequestZone = clickedZone;
            }
        }
    }

    /**
     * Requires destinationZone != player.getCurrentZoneID;
     *
     * Creates a move that will move the Player to the destination Zone.
     * the Player idle animation will be stopped.
     * @param destinationZone the destinationZone the Player should stay at the end of the move.
     */
    private ZoneBasedMoveCommand createMove(final Zone destinationZone){
        if(player.getCurrentZoneID() == destinationZone.getZoneID()) return null;
        ShortestPath<Zone, Integer> shortestPath = ShortestPath.calculateFor(graph, player.getCurrentZoneID(), destinationZone.getZoneID());
        if(!shortestPath.existsPath()) return null;

        Iterable<IEdge<Integer>> itPath = shortestPath.path();
        final List<Zone> moveZones = new LinkedList<Zone>();

        for(IEdge edge: itPath){
            final int v = edge.getDestination();
            moveZones.add(graph.getNodeMetaData(v));
        }

        player.stopAnimation(MovableEntity.IDLE_ANIMATION);
        player.startAnimation(MovableEntity.MOVE_ANIMATION);
        return new ZoneBasedMoveCommand(player, moveZones);
    }

    /**
     * if move != null the move will be executed.
     * if the move is finished move will be set to null.
     * and the zone event will be Played.
     * as well as the player idle animation will be started.
     * @param delta
     */
    public void update(float delta){
        if(move == null) return;

        move.execute();

        if(move.isFinished()) {
            if(clickedRequestZone != null) {
                move = createMove(clickedRequestZone);
                clickedRequestZone = null;
            }else{
                player.stopAnimation(MovableEntity.MOVE_ANIMATION);
                player.startAnimation(MovableEntity.IDLE_ANIMATION);
                final Zone zone = graph.getNodeMetaData(player.getCurrentZoneID());
                zone.startEvent(0);
                move = null;
            }
        }
    }

    /**
     * requires graph != null
     * returns the zone that got clicked;
     * if two zones are on top of each other the zone with the lower ID get returned
     * @param pos vec pos
     * @return null if no zone got clicked else the clicked zone
     */
    private Zone getClickedZone(final Vector2 pos){
        final Vector2 vec = pos;
        for(int i = 0; i < graph.numberOfNodes(); i++){
            final Zone zone = graph.getNodeMetaData(i);
            if(zone.contains(vec)){
                return zone;
            }
        }
        return null;
    }
}

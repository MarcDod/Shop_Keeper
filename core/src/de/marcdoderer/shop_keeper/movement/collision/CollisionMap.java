package de.marcdoderer.shop_keeper.movement.collision;

import com.badlogic.gdx.math.Rectangle;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionMap {

    /**
     * Rectangle that encapsulates all rectangles in the collision map.
     */
    public Rectangle gridRectangle;

    /**
     * A two-dimensional array of {@link java.util.List} serves as the data
     * structure for storing the rectangles. Each element of the array holds a list
     * of rectangles. At the same time, each element of the array is associated with
     * an area of the bounding rectangle {@link CollisionMap} through
     * the transform methods ({@link CollisionMap} and
     * {@link CollisionMap}. These areas are called cells.
     */
    private List<Rectangle>[][] map;

    public CollisionMap(Set<Rectangle> rectangles, int gridResolutionX, int gridResolutionY) throws IllegalArgumentException{
        if(rectangles == null || gridResolutionX < 1 || gridResolutionY < 1){
            throw new IllegalArgumentException();
        }
        gridRectangle = Util.getBoundingBox(rectangles);
        generateCollisionMap(gridResolutionX, gridResolutionY);
        try {
            fillCollisionMap(rectangles);
        } catch (CollisionMapOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private Set<Rectangle> getCollisionCandidates(final Rectangle rec) throws CollisionMapOutOfBoundsException {
        Point bottomLeft = getBottomLeftPoint(rec);
        Point topRight = getTopRightPoint(rec);
        Set<Rectangle> rectangleSet = new HashSet<Rectangle>();
        for(int row = bottomLeft.x; row < topRight.x; row++){
            for(int col = bottomLeft.y; col < topRight.y; col++){
                for(Rectangle rectangle : map[col][row]){
                    rectangleSet.add(rectangle);
                }
            }
        }
        return rectangleSet;
    }

    private void fillCollisionMap(final Set<Rectangle> rectangles) throws CollisionMapOutOfBoundsException {
        for(final Rectangle rec : rectangles){
            Point bottomLeft = getBottomLeftPoint(rec);
            Point topRight = getTopRightPoint(rec);
            for(int row = bottomLeft.x; row < topRight.x; row++){
                for(int col = bottomLeft.y; col < topRight.y; col++){
                    map[col][row].add(rec);
                }
            }
        }
    }

    private Point getBottomLeftPoint(final Rectangle rec) throws CollisionMapOutOfBoundsException{
        int x = (int) transformX(rec.getX());
        int y = (int) transformY(rec.getY());

        return new Point(x, y);
    }

    private Point getTopRightPoint(final Rectangle rec) throws CollisionMapOutOfBoundsException{
        int x = (int) Math.ceil(transformX(rec.getX()+ rec.getWidth()));
        int y = (int) Math.ceil(transformY(rec.getY() + rec.getHeight()));

        return new Point(x, y);
    }

    /**
     * Transform a x coordinate from rectangle space to the internal space of the
     * {@link CollisionMap}. For accessing specific cells of the grid the return
     * value must be rounded and cast appropriately.
     *
     * @param x coordinate of a point
     * @return x coordinate of given point in the internal space
     * @throws CollisionMapOutOfBoundsException
     */
    private float transformX(float x) throws CollisionMapOutOfBoundsException {
        if (x < gridRectangle.x || x > gridRectangle.x + gridRectangle.width) {
            throw new CollisionMapOutOfBoundsException("x coordinate is outside the defined range.");
        } else {
            return ((x - gridRectangle.x) / gridRectangle.width) * map[0].length;
        }
    }

    /**
     * Transform a y coordinate from rectangle space to the internal space of the
     * {@link CollisionMap}. For accessing specific cells of the grid the return
     * value must be rounded and cast appropriately.
     *
     * @param y coordinate of a point
     * @return y coordinate of given point in the internal space
     * @throws CollisionMapOutOfBoundsException
     */
    private float transformY(float y) throws CollisionMapOutOfBoundsException {
        if (y < gridRectangle.y || y > gridRectangle.y + gridRectangle.height) {
            throw new CollisionMapOutOfBoundsException("y coordinate is outside the defined range.");
        } else {
            return ((y - gridRectangle.y) / gridRectangle.height) * map.length;
        }
    }

    public boolean collided(final Rectangle rectangle) throws CollisionMapOutOfBoundsException{
        for(Rectangle rec : getCollisionCandidates(rectangle)){
            if(rectangle.overlaps(rec)){
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void generateCollisionMap(int gridResolutionX, int gridResolutionY){
        map = new ArrayList[gridResolutionY][gridResolutionX];
        for(int y = 0; y < gridResolutionY; y++){
            for(int x = 0; x < gridResolutionX; x++){
                map[y][x] = new ArrayList<Rectangle>();
            }
        }
    }

    private static class Point{
        final int x;
        final int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}

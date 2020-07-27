package de.marcdoderer.shop_keeper.exceptions;

public class CollisionMapOutOfBoundsException extends Exception {
    private static final long serialVersionUID = -2542892939719989802L;

    public CollisionMapOutOfBoundsException(final String message){
        super(message);
    }
}

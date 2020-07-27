package de.marcdoderer.shop_keeper.command;

import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.entities.MovableEntity;

import java.util.List;

/**
 * move a movableEntity through a bunch of vectors
 */
public class MoveCommand implements Command {

    protected MovableEntity entity;
    protected List<Vector2> destinations;

    protected int currentPosition;

    protected boolean requestCancel;
    protected boolean cancel;

    public MoveCommand(final MovableEntity entity, final List<Vector2> destinations){
        this.entity = entity;
        this.destinations = destinations;
        this.currentPosition = 0;
        this.cancel = false;
        this.requestCancel = false;
    }

    protected void reachedADestination(){
        this.currentPosition++;
        cancel = requestCancel;
        if(isFinished()){
            this.entity.stopAnimation(MovableEntity.MOVE_ANIMATION);
        }
    }

    @Override
    public void execute() {
        if(isFinished()) return;
        if(this.entity.moveTo(this.destinations.get(currentPosition))){
            reachedADestination();
        }
    }

    public final boolean isFinished(){
        return this.destinations.size() == this.currentPosition || cancel;
    }

    @Override
    public final void requestCancel(){
        this.requestCancel = true;
    }

}

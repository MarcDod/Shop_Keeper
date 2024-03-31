package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class CharacterFactory {

    public GameState gameState;

    public CharacterFactory(GameState screen){
        this.gameState = screen;
    }

    public Player createPlayer(final String name, final Vector2 position,  final World world, final int currenPlaceID){
        final Body body = createPlayerBody(world, position);
        final Sprite sprite = createPlayerSprite();
        return new Player(sprite, body, new MoveAnimation(sprite), new IdleAnimation(sprite), name, currenPlaceID);
    }

    private Body createPlayerBody(final World world, final Vector2 position){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.KinematicBody;
        def.fixedRotation = true;
        def.position.set(position);
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25f / GameState.SCALE,25f/ GameState.SCALE);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;
        fix.isSensor = true;

        body.createFixture(fix);
        body.setUserData(new BodyUserData(def, fix, 0.83f, 25f/ GameState.SCALE));
        return body;
    }
    private Sprite createPlayerSprite(){
        final TextureAtlas atlas = gameState.screen.assetManager.get("npc/atlas/npc.atlas");
        final Sprite playerSprite = new Sprite(atlas.findRegion("guy0"));
        playerSprite.setSize(160f / GameState.SCALE, 160f / GameState.SCALE);
        playerSprite.setOriginCenter();
        playerSprite.setOrigin(playerSprite.getOriginX(), playerSprite.getOriginY()  - 2f);
        return playerSprite;
    }
}

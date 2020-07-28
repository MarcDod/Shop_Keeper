package de.marcdoderer.shop_keeper.screen.state;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.entities.CharacterFactory;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.MovableEntity;
import de.marcdoderer.shop_keeper.entities.Player;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.listener.ExitZoneListener;
import de.marcdoderer.shop_keeper.listener.TradeItemListener;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.manager.ItemData;
import de.marcdoderer.shop_keeper.manager.PlaceData;
import de.marcdoderer.shop_keeper.manager.PlayerData;
import de.marcdoderer.shop_keeper.movement.PlayerController;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.hud.IngameHud;
import de.marcdoderer.shop_keeper.shop.Basement;
import de.marcdoderer.shop_keeper.shop.Basement2;
import de.marcdoderer.shop_keeper.shop.Place;
import de.marcdoderer.shop_keeper.shop.Shop;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;
import de.marcdoderer.shop_keeper.util.FrameRate;

public class GameState extends State{

    public static final int WIDTH = 48;
    public static final int HEIGHT = 27;
    public static final int SCALE = 30;

    public final OrthographicCamera gameCamera;
    private final IngameHud ingameHud;

    public final World world;
    public final Box2DDebugRenderer debugRenderer;
    private boolean debugOn;

    public final GameScreen screen;

    public final DayNightCircle dayNightCircle;

    private Place[] places;
    private int currentPlace;

    public final PlayerController playerController;
    public final Player player;


    public final ExitZoneListener exitZoneListener;
    public final TradeItemListener tradeItemListener;

    private boolean openMenu;

    public GameState(final GameScreen screen){
        exitZoneListener = new ExitZoneListener(this);
        tradeItemListener = new TradeItemListener(this);
        this.gameCamera = new OrthographicCamera(WIDTH, HEIGHT);
        this.ingameHud = new IngameHud();
        this.world = new World(new Vector2(0,0), false);
        this.debugRenderer = new Box2DDebugRenderer();
        this.dayNightCircle = new DayNightCircle(screen.gameManager.gameData.getTimeInSeconds());
        this.ingameHud.addHudElement(dayNightCircle);
        this.ingameHud.addHudElement(new FrameRate());
        this.debugOn = false;
        this.screen = screen;
        this.currentPlace = screen.gameManager.gameData.getPlayerData().getPlayerPlaceID();

        this.places = initPlaces();
        this.player = initPlayer(screen.gameManager.gameData.getPlayerData());
        this.places[currentPlace].addEntity(EntityManager.Layer.ENTITY_LAYER, player);
        if(player.getCarriedItem() != null){
            this.places[currentPlace].addEntity(EntityManager.Layer.ITEM_LAYER, player.getCarriedItem());
        }
        this.playerController = new PlayerController(player);
        this.playerController.setGraph(this.places[currentPlace].getGraph());
        this.playerController.setActive(true);
        setCameraTo(places[currentPlace].getPosition());
    }

    @Override
    public void render(final SpriteBatch batch) {
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        places[currentPlace].render(batch);
        batch.end();
        if(debugOn){
            debugRenderer.render(world, gameCamera.combined);
        }
        places[currentPlace].renderLight();
        ingameHud.render(batch);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(gameCamera.combined);

        if(debugOn){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            places[currentPlace].drawShape(shapeRenderer);
            shapeRenderer.end();
        }
    }

    @Override
    public void update(float delta) {
        updateWorld(delta);
        playerController.update(delta);
        ingameHud.update(delta);


        for(Place place : places){
            place.update(delta);
        }
    }

    @Override
    public void resize(float width, float height) {
        ingameHud.resize(width, height);
    }

    @Override
    public void dispose() {
        this.world.dispose();
        this.debugRenderer.dispose();
        this.player.dispose();
        this.ingameHud.dispose();
        for(Place place : places){
            place.dispose();
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == Input.Keys.ENTER)
            debugOn = !debugOn;
        //TODO correct keyCode
        else if(keyCode == Input.Keys.TAB){
            ingameHud.setVisible(false);
            render(screen.batch);
            this.screen.stateManager.push(new MenuState(screen));
        }
    }

    @Override
    public void mouseClicked(float x, float y) {
        playerController.clickEvent(new Vector2(x, y));
    }

    @Override
    public void resume() {
        this.ingameHud.setVisible(true);
    }

    @Override
    public OrthographicCamera getCamera() {
        return this.gameCamera;
    }

    public static final float STEP_TIME = 1f/60f;
    private float accumulator = 0;
    private void updateWorld(final float delta){
        accumulator += Math.min(delta, 0.25f);
        while(accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, 6, 2);
        }
    }

    public Place getCurrentPlace(){
        return getPlace(currentPlace);
    }

    public Place getPlace(final int placeID){
        return this.places[placeID];
    }

    public void changeCurrentPlace(final int nextPlaceID){
        this.currentPlace = nextPlaceID;
        this.playerController.setGraph(getCurrentPlace().getGraph());
    }

    public final void setCameraTo(Vector2 position){
        Vector3 cameraVec = gameCamera.position;
        gameCamera.translate(WIDTH / 2f - position.x - cameraVec.x , HEIGHT / 2f + position.y - cameraVec.y);
        gameCamera.update();
    }

    private Place[] initPlaces(){
        final Place[] places = new Place[3];
        final PlaceData[] placeData = screen.gameManager.gameData.getPlaceDatas();
        places[Shop.SHOP_ID] = new Shop(this, dayNightCircle, placeData[Shop.SHOP_ID].getEntityDatas());
        places[Basement.BASEMENT_ID] = new Basement(this, dayNightCircle, placeData[Basement.BASEMENT_ID].getEntityDatas());
        places[Basement2.BASEMENT2_ID] = new Basement2(this, dayNightCircle, placeData[Basement2.BASEMENT2_ID].getEntityDatas());
        return places;
    }

    /**
     * Required initPlaces() has to be called first
     * @param playerData data of the Player
     */
    private Player initPlayer(final PlayerData playerData){
        final CharacterFactory characterFabric = new CharacterFactory(this);
        final ItemFactory itemFactory = new ItemFactory(this);

        final Vector2 position = places[currentPlace].getGraph().getNodeMetaData(playerData.getPlayerZoneID()).getCenter();
        final Player player = characterFabric.createPlayer("Player", position, world);

        player.setCurrentZoneID(playerData.getPlayerZoneID());
        player.startAnimation(MovableEntity.IDLE_ANIMATION);
        if(playerData.getCarriedItem() != null){
            Item item = itemFactory.createItem(playerData.getCarriedItem().getType(), new Vector2(0, 0), world);
            player.carryItem(item);
        }

        return player;
    }

    public PlayerData getPlayerData(){
        final PlayerData playerData = new PlayerData();
        playerData.setPlayerPlaceID(currentPlace);
        playerData.setPlayerZoneID(player.getCurrentZoneID());

        if(player.getCarriedItem() != null){
            final ItemData itemData = new ItemData();
            itemData.setType(player.getCarriedItem().type);
            playerData.setItemData(itemData);
        }
        return playerData;
    }

    public PlaceData[] getPlaceData(){
        PlaceData[] placeData = new PlaceData[3];
        placeData[Shop.SHOP_ID] = places[Shop.SHOP_ID].getPlaceData();
        placeData[Basement.BASEMENT_ID] = places[Basement.BASEMENT_ID].getPlaceData();
        placeData[Basement2.BASEMENT2_ID] = places[Basement2.BASEMENT2_ID].getPlaceData();

        return placeData;
    }

}

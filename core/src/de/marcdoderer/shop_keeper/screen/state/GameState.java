package de.marcdoderer.shop_keeper.screen.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.entities.CharacterFactory;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.MovableEntity;
import de.marcdoderer.shop_keeper.entities.Player;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.entities.specialEntity.Chest;
import de.marcdoderer.shop_keeper.listener.ChestListener;
import de.marcdoderer.shop_keeper.listener.ExitZoneListener;
import de.marcdoderer.shop_keeper.listener.TakeItemListener;
import de.marcdoderer.shop_keeper.listener.TradeItemListener;
import de.marcdoderer.shop_keeper.manager.PlaceData;
import de.marcdoderer.shop_keeper.manager.PlayerData;
import de.marcdoderer.shop_keeper.movement.PlayerController;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.hud.IngameHud;
import de.marcdoderer.shop_keeper.screen.hud.InventoryHud;
import de.marcdoderer.shop_keeper.shop.*;
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

    private final Place[] places;
    private int currentPlace;

    public final PlayerController playerController;
    public final Player player;


    public final ExitZoneListener exitZoneListener;
    public final TradeItemListener tradeItemListener;
    public final ChestListener chestListener;
    public final TakeItemListener takeItemListener;

    private InventoryHud inventoryHud;

    public GameState(final GameScreen screen){
        exitZoneListener = new ExitZoneListener(this);
        tradeItemListener = new TradeItemListener(this);
        chestListener = new ChestListener(this);
        takeItemListener = new TakeItemListener(this);
        this.gameCamera = new OrthographicCamera(WIDTH, HEIGHT);
        this.ingameHud = new IngameHud();
        this.world = new World(new Vector2(0,0), false);
        this.debugRenderer = new Box2DDebugRenderer();
        this.screen = screen;
        this.dayNightCircle = new DayNightCircle(screen.gameManager.gameData.getTimeInSeconds());
        this.ingameHud.addHudElement(dayNightCircle);
        this.ingameHud.addHudElement(new FrameRate());
        this.debugOn = false;
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(inventoryHud != null){
            inventoryHud.drawGrid(shapeRenderer);
        }

        if(debugOn){
            places[currentPlace].drawShape(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void openInventar(final Chest chest){
        if(inventoryHud != null) return;
        inventoryHud = new InventoryHud(this, chest);
        this.ingameHud.addHudElement(inventoryHud);
        this.playerController.setActive(false);
    }
    public void closeInventar(){
        this.ingameHud.removeHudElement(inventoryHud);
        this.playerController.setActive(true);
        this.inventoryHud.dispose();
        this.inventoryHud = null;

    }

    public Zone getPlayerZone(){
        return getCurrentPlace().getGraph().getNodeMetaData(player.getCurrentZoneID());
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
    public void resize(int width, int height) {
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
        else if(keyCode == MenuManager.MENU_KEY){
            ingameHud.setVisible(false);
            render(screen.batch);
            this.screen.stateManager.push(MenuManager.getInGameMenuState(this.screen));
        }
    }

    @Override
    public void mouseClicked(float x, float y) {
        boolean closed = inventoryHud == null;
        playerController.clickEvent(new Vector2(x, y));
        if(inventoryHud != null){
            if(closed)
                inventoryHud.opendWithThisClick();
            inventoryHud.clickEvent(x, y);
        }
    }

    @Override
    public void mouseDragged(float x, float y) {
        if(inventoryHud != null){
            inventoryHud.mouseDragged(x, y);
        }
    }

    @Override
    public void mouseReleased(float x, float y) {
        if(inventoryHud != null){
            inventoryHud.mouseReleased(x, y);
        }
    }

    @Override
    public void resume() {
        this.ingameHud.setVisible(true);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        gameCamera.position.set(position.x + WIDTH / 2f, position.y + HEIGHT / 2f, 0);
        gameCamera.update();
    }

    private Place[] initPlaces(){
        final Place[] places = new Place[4];
        final PlaceData[] placeData = screen.gameManager.gameData.getPlaceDatas();
        places[Shop.SHOP_ID] = new Shop(this, dayNightCircle, placeData[Shop.SHOP_ID].getEntityDatas());
        places[Basement.BASEMENT_ID] = new Basement(this, dayNightCircle, placeData[Basement.BASEMENT_ID].getEntityDatas());
        places[Basement2.BASEMENT2_ID] = new Basement2(this, dayNightCircle, placeData[Basement2.BASEMENT2_ID].getEntityDatas());
        places[Garden.GARDEN_ID] = new Garden(this, dayNightCircle, placeData[Garden.GARDEN_ID].getEntityDatas());
        return places;
    }

    /**
     * Required initPlaces() has to be called first
     * @param playerData data of the Player
     */
    private Player initPlayer(final PlayerData playerData){
        final CharacterFactory characterFabric = new CharacterFactory(this);

        final Vector2 position = places[currentPlace].getGraph().getNodeMetaData(playerData.getPlayerZoneID()).getCenter();
        final Player player = characterFabric.createPlayer("Player", position, world, playerData.getPlayerPlaceID());

        player.setCurrentZoneID(playerData.getPlayerZoneID());
        player.startAnimation(MovableEntity.IDLE_ANIMATION);
        if (playerData.getCarriedItemData() != null) {
            Item carriedItem = ItemFactory.getItemRegistry().createItemByID(playerData.getCarriedItemData().itemID);
            carriedItem.setStackCount(playerData.getCarriedItemData().stackCount);
            player.carryItem(carriedItem);
        }

        return player;
    }

    public PlayerData getPlayerData(){
        final PlayerData playerData = new PlayerData();
        playerData.setPlayerPlaceID(currentPlace);
        playerData.setPlayerZoneID(player.getCurrentZoneID());

        if(player.getCarriedItem() != null){
            playerData.setCarriedItemData(player.getCarriedItem().createItemData());
        }
        return playerData;
    }

    public PlaceData[] getPlaceData(){
        PlaceData[] placeData = new PlaceData[places.length];
        int i = 0;
        for(Place place : places){
            placeData[i] = place.getPlaceData();
            i++;
        }

        return placeData;
    }

}

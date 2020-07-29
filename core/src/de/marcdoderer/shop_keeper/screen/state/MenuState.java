package de.marcdoderer.shop_keeper.screen.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import de.marcdoderer.shop_keeper.screen.GameScreen;

public class MenuState extends State{

    public final static float GRAY_VALUE = 0.5f;
    public final static float WIDTH = GameState.WIDTH * 0.3f;
    public final static float HEIGHT = GameState.HEIGHT * 0.8f;

    public final static float MENU_X = GameState.WIDTH / 2f - WIDTH / 2f;
    public final static float MENU_Y = GameState.HEIGHT / 2f - HEIGHT / 2f;

    public final TextureRegion background;
    public final Texture menuForeground;
    public final GameScreen screen;

    private final OrthographicCamera camera;
    private final ShaderProgram grayShader;

    private final TextButton optionButton;
    private final Stage stage;

    private InputProcessor originInputProcessor;

    public MenuState(final GameScreen screen){
        background = ScreenUtils.getFrameBufferTexture();
        menuForeground = screen.assetManager.get("menu/menu.png");
        this.screen = screen;
        this.camera = new OrthographicCamera(GameState.WIDTH, GameState.HEIGHT);
        this.camera.translate(GameState.WIDTH / 2f, GameState.HEIGHT / 2f);
        this.camera.update();

        grayShader = new ShaderProgram(Gdx.files.internal("shader/vertex.vert"), Gdx.files.internal("shader/fragment.frag"));
        if(grayShader.getLog().length() > 0){
            System.out.println(grayShader.getLog());
        }

        this.stage = new Stage();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        originInputProcessor = Gdx.input.getInputProcessor();

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(originInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        Skin skin = new Skin(Gdx.files.internal("menu/button/uiskin.json"));
        Group group = new Group();
        group.setBounds(MENU_X * GameState.SCALE, MENU_Y * GameState.SCALE, WIDTH * GameState.SCALE, HEIGHT * GameState.SCALE);
        this.optionButton = new TextButton("hallo", skin);
        this.optionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionAction();
            }
        });
        optionButton.setSize(100, 100);

        group.addActor(optionButton);

        this.stage.addActor(group);
    }

    private void optionAction(){

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(grayShader);
        batch.begin();
        batch.getColor();

        grayShader.setUniformf("u_percent", GRAY_VALUE);
        batch.draw(background, 0, 0, GameState.WIDTH, GameState.HEIGHT);

        batch.end();
        batch.setShader(SpriteBatch.createDefaultShader());

        batch.begin();
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, .3f);
        batch.draw(menuForeground, MENU_X, MENU_Y, WIDTH, HEIGHT);
        batch.setColor(c.r, c.g, c.b, 1f);

        batch.end();

        this.stage.draw();

    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(float width, float height) {

    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        grayShader.dispose();
        optionButton.getSkin().dispose();
        this.stage.dispose();
        Gdx.input.setInputProcessor(originInputProcessor);
    }

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == Input.Keys.TAB){
            this.dispose();
            this.screen.stateManager.pop();
        }
    }

    @Override
    public void mouseClicked(float x, float y) {

    }

    @Override
    public void resume() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        originInputProcessor = Gdx.input.getInputProcessor();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(originInputProcessor);
    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }
}

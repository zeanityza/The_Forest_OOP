package Sceens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.forest.Forests;

public class StartGame implements Screen {

	private Viewport viewport;
	private Stage stage;
	private OrthographicCamera cam;
	private Texture img;
	private Skin skin;
	private Game game;
	private Forests games;
	private TextureAtlas buttonAtlas;
	private BitmapFont font;
	private TextButton buttonstart;
	private TextButtonStyle textButtonStyle;


	public StartGame(final Game game, final Forests games) {
		this.games = games;
		this.game = game;
		viewport = new FitViewport(Forests.V_WIDTH, Forests.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((Forests) game).batch);
		
		cam = new OrthographicCamera();

		
		cam.setToOrtho(false, 1600,  1050);
		
		img = new Texture("StartGame/StartGame.png");

		stage = new Stage(new StretchViewport(1920, 1024));
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("button/startbut/gamebutton.pack"));
		skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button1");
		textButtonStyle.down = skin.getDrawable("Button2");
		textButtonStyle.checked = skin.getDrawable("Button2");
		buttonstart = new TextButton("", textButtonStyle);
		stage.addActor(buttonstart);
		buttonstart.setPosition(400, 80);
		
//		skin = new Skin(Gdx.files.internal("button/uiskin.json"));
//
//		TextButton buttonStart = new TextButton("START", skin);
//		buttonStart.setWidth(500);
//		buttonStart.setHeight(100);
//		buttonStart.setPosition(1600 / 2 - 200 / 2, 280);
//
//		stage.addActor(buttonStart);

		buttonstart.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.setScreen(new MainMenuScreen((Forests) game, games));
			}
		});
		

	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		games.batch.setProjectionMatrix(cam.combined);
		games.batch.begin();
		games.batch.draw(img, 0, 0);
		games.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();


	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		img.dispose();
	}

}

package Sceens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forest.Forests;

public class MainMenuScreen implements Screen {

	private Game game;
	private OrthographicCamera cam;
	private Stage stage;
	private Skin skin;
	private Forests games;
	private TextureAtlas buttonAtlas;
	private BitmapFont font;
	private TextButton button;
	private TextButton button_ex;
	private TextButtonStyle textButtonStyle;
	private TextButton button_tul;
	private TextButton screenbut;
	private Viewport viewport;
	private Texture img;

	public MainMenuScreen(final Game game, Forests games) {
		this.game = game;
		this.games = games;

		viewport = new FitViewport(Forests.V_WIDTH, Forests.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((Forests) game).batch);
		
		cam = new OrthographicCamera();

		
		cam.setToOrtho(false, 1300,  800);
		
		img = new Texture("backgroundF/back1.jpg");

		stage = new Stage(new StretchViewport(1920, 1024));
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();

		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("button/GameButton/gamebutton.pack"));
		skin.addRegions(buttonAtlas);
		
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("screensz");
		textButtonStyle.down = skin.getDrawable("screensz");
		textButtonStyle.checked = skin.getDrawable("screensz");
		screenbut = new TextButton("", textButtonStyle);
		stage.addActor(screenbut);
		screenbut.setHeight(1000f);
		screenbut.setPosition(450, 5);
		
		Label forestLabel = new Label("THE FOREST", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		forestLabel.setFontScale((float)4.0);
		forestLabel.setPosition(720, 130);
		stage.addActor(forestLabel);
		
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button1");
		textButtonStyle.down = skin.getDrawable("Button2");
		textButtonStyle.checked = skin.getDrawable("Button3");
		button = new TextButton("", textButtonStyle);
		stage.addActor(button);
		button.setPosition(700, 640);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.setScreen(new PlayScreen((Forests) game));
			}
		});

		// skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button1");
		textButtonStyle.down = skin.getDrawable("Button2");
		textButtonStyle.checked = skin.getDrawable("Button3");
		button_ex = new TextButton("", textButtonStyle);
		stage.addActor(button_ex);
		button_ex.setPosition(700, 210);
		button_ex.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});

		// skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button1");
		textButtonStyle.down = skin.getDrawable("Button2");
		textButtonStyle.checked = skin.getDrawable("Button3");
		button_tul = new TextButton("", textButtonStyle);
		stage.addActor(button_tul);
		button_tul.setPosition(700, 425);
		button_tul.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});

		
		Label menuLabel = new Label("MENU", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		menuLabel.setFontScale((float)3.5);
		menuLabel.setPosition(825, 935);
		stage.addActor(menuLabel);
		
		Label startLabel = new Label("START", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		startLabel.setFontScale((float)3);
		startLabel.setPosition(840, 750);
		stage.addActor(startLabel);
		
		Label tulLabel = new Label("TUTORIAL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		tulLabel.setFontScale((float)3);
		tulLabel.setPosition(805, 530);
		stage.addActor(tulLabel);
		
		Label exitLabel = new Label("EXIT", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		exitLabel.setFontScale((float)3);

		exitLabel.setPosition(860, 320);
		stage.addActor(exitLabel);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
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

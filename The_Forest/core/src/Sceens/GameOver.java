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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.forest.Forests;

public class GameOver implements Screen {

	private Viewport viewport;
	private Stage stage;
	private OrthographicCamera cam;
	private Texture img;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private BitmapFont font;
	private TextButton restart;
	private TextButton menu;
	private TextButtonStyle textButtonStyle;
	private TextButton exit;
	private TextButton screenbut;
	private TextButton scores;
	private TextButton times;
	
	//game score
	private int time_game;
	private int score_game;
	private float total_score;
	
	private Game game;
	private Forests games;

	public GameOver(final Game game, final Forests games, int score, int time) {
		
		this.games = games;
		this.game = game;
		viewport = new FitViewport(Forests.V_WIDTH, Forests.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((Forests) game).batch);
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1800, 1000);
		img = new Texture("backgroundF/back6.jpg");
		
		score_game = score;
		System.out.println(score_game);
		time_game = time;
		System.out.println(time_game);
		
		stage = new Stage(new StretchViewport(1920, 1024));

		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();

		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("button/overbut/overbutton.pack"));
		skin.addRegions(buttonAtlas);
		
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Windows");
		textButtonStyle.down = skin.getDrawable("Windows");
		textButtonStyle.checked = skin.getDrawable("Windows");
		screenbut = new TextButton("", textButtonStyle);
		stage.addActor(screenbut);
		screenbut.setHeight(1000f);
		screenbut.setWidth(1400f);
		screenbut.setPosition(270, 0);
		
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button1");
		textButtonStyle.down = skin.getDrawable("Button2");
		textButtonStyle.checked = skin.getDrawable("Button3");
		restart = new TextButton("", textButtonStyle);
		stage.addActor(restart);
		restart.setPosition(500, 170);
		restart.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.setScreen(new PlayScreen(games));
			}
		});

		// skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button4");
		textButtonStyle.down = skin.getDrawable("Button5");
		textButtonStyle.checked = skin.getDrawable("Button6");
		menu = new TextButton("", textButtonStyle);
		stage.addActor(menu);
		menu.setPosition(780, 170);
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.setScreen(new MainMenuScreen(game,games));
			}
		});

		// skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button10");
		textButtonStyle.down = skin.getDrawable("Button11");
		textButtonStyle.checked = skin.getDrawable("Button12");
		exit = new TextButton("", textButtonStyle);
		stage.addActor(exit);
		exit.setPosition(1030, 165);
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button20");
		textButtonStyle.down = skin.getDrawable("Button20");
		textButtonStyle.checked = skin.getDrawable("Button20");
		scores = new TextButton("", textButtonStyle);
		stage.addActor(scores);
		scores.setHeight(150f);
		scores.setWidth(300f);
		scores.setPosition(500, 550);

		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("Button20");
		textButtonStyle.down = skin.getDrawable("Button20");
		textButtonStyle.checked = skin.getDrawable("Button20");
		times = new TextButton("", textButtonStyle);
		stage.addActor(times);
		times.setHeight(150f);
		times.setWidth(300f);
		times.setPosition(500, 400);


		
		Label menuLabel = new Label("EXIT", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		menuLabel.setFontScale((float)3.5);
		menuLabel.setPosition(1185, 265);
		stage.addActor(menuLabel);
		
		Label scoresLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		scoresLabel.setFontScale((float)3.5);
		scoresLabel.setPosition(550, 620);
		stage.addActor(scoresLabel);
		
		Label timesLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		timesLabel.setFontScale((float)3.5);
		timesLabel.setPosition(575, 470);
		stage.addActor(timesLabel);
		
		Label scoressLabel = new Label(score_game+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoressLabel.setFontScale((float)3.5);
		scoressLabel.setPosition(900, 620);
		stage.addActor(scoressLabel);
		
		Label timessLabel = new Label(time_game+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timessLabel.setFontScale((float)3.5);
		timessLabel.setPosition(900, 470);
		stage.addActor(timessLabel);
		
		Label overLabel = new Label("GAME OVER", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		overLabel.setFontScale((float)3.5);
		overLabel.setPosition(800, 840);
		stage.addActor(overLabel);

	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 1, 0, 1);
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
	
		img.dispose();
		stage.dispose();
		skin.dispose();

	}

}

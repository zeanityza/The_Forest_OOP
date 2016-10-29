package Sceens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forest.Forests;
import Enemy.Enem;
import Enemy.Enemies;
import Player.GunFireBull;
import Player.Soldier;
import Scenes.Sight_Scene;
import Tools.ElementBody;
import Tools.WorldCreator;
import objectScene.CoinGames;
import objectScene.Tile_Object_2;
import objectScene.Brick;
import objectScene.CoinGame;
import java.util.concurrent.LinkedBlockingQueue;
import com.badlogic.gdx.*;

public class PlayScreen implements Screen {

	private MapProperties mapProperties;
	private int mapWidth;
	private int mapHeight;
	private int tilePixelWidth;
	private int tilePixelHeight;
	private int mapPixelWidth;
	private int mapPixelHeight;

	private Forests game;
	private TextureAtlas atlas;

	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Sight_Scene scene_screen;

	// Tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;
	private WorldCreator creator;

	// marios player
	private Soldier player;
	// enemy player
	// private Enemies ene;
	//private Music music;

	// debug clear render
	private boolean debug = false;

	private Brick timebrick;

	private boolean gunfirerate;
	private float countrate;
	private GunFireBull fires;
	
	//Score in game and Time
	private int score_g;
	private int time_g;

	public PlayScreen(Forests game) {
		// set player animation
		// atlas = new TextureAtlas("aaa.pack");
		// atlas = new TextureAtlas("ronnie.pack");
		atlas = new TextureAtlas("sols/sold2/soldier.pack");
		// atlas = new TextureAtlas("ddd/New folder (6)/zzz/ninjasol.pack");

		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(Forests.V_WIDTH / Forests.PPM, Forests.V_HEIGHT / Forests.PPM, gamecam);
		scene_screen = new Sight_Scene(game.batch);

		maploader = new TmxMapLoader();
		// set Map
		map = maploader.load("Forest.tmx");
		//map = maploader.load("The Forest YIM/The Forest YIM.tmx");
		
		
		renderer = new OrthogonalTiledMapRenderer(map, 1 / Forests.PPM);
		mapProperties = map.getProperties();
		mapWidth = mapProperties.get("width", Integer.class);
		mapHeight = mapProperties.get("height", Integer.class);
		tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
		tilePixelHeight = mapProperties.get("tileheight", Integer.class);
		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;

		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();

		creator = new WorldCreator(this);
		// create mario in our game world
		player = new Soldier(this);


		world.setContactListener(new ElementBody());

		// music loop
//		music = Forests.manager.get("music/walk.MP3", Music.class);
//		music.setLooping(true);
//		music.play();

		// enemy goomba
		// ene = new Enemies(this, .32f, .32f);
		// ene = new Enemies(this, 5f, .32f);


	}


	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {

	}

	public void handleInput(float dt) {

		if (player.currentState != Soldier.State.DEAD) {
			if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP))
					&& player.b2body.getLinearVelocity().y == 0)
				player.b2body.applyLinearImpulse(new Vector2(0, 4.5f), player.b2body.getWorldCenter(), true);
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

				if (((player.downright == true || player.walkfire == true) && player.standfire == false)
						&& player.b2body.getLinearVelocity().x <= 0.5f) {

					player.b2body.applyLinearImpulse(new Vector2(0.05f, 0), player.b2body.getWorldCenter(), true);
				} else if ((player.downright == false && player.walkfire == false && player.standfire == false)
						&& player.b2body.getLinearVelocity().x <= 2) {
					player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);
				}
			}

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

				if (((player.downright == true || player.walkfire == true) && player.standfire == false)
						&& player.b2body.getLinearVelocity().x >= -0.5f) {
					player.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), player.b2body.getWorldCenter(), true);
				} else if ((player.downright == false && player.walkfire == false && player.standfire == false)
						&& player.b2body.getLinearVelocity().x >= -2) {
					player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player.b2body.getWorldCenter(), true);
				}
			}
			if ((Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) && (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					&& player.b2body.getLinearVelocity().y == 0) {
				player.walkfire = true;

			} else if ((Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) && (Gdx.input.isKeyPressed(Input.Keys.LEFT))
					&& player.b2body.getLinearVelocity().y == 0) {
				player.walkfire = true;

			} else if ((Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) && (!Gdx.input.isKeyPressed(Input.Keys.LEFT))
					&& (!Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && player.b2body.getLinearVelocity().y == 0) {
				player.standfire = true;
			}

			else {
				player.walkfire = false;
				player.standfire = false;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				toggleDebug();
			}
			if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y == 0)) {
				player.downright = true;

			} else {
				player.downright = false;
			}

			if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && gunfirerate == true) {
				player.GunFire();
				gunfirerate = false;
				countrate = 0;
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.Z))
				player.Knifekilling();

			if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
				score_g = scene_screen.getScore();
				time_g = scene_screen.getTime();
				game.setScreen(new GameOver(game, game, score_g, time_g));
			//	dispose();
				
			}
				

		}

	}

	// update
	public void update(float dt) {
		
		
		countrate += dt;
	//	System.out.println(countrate);
		if(countrate >= 0.8f){
			gunfirerate = true;
		}
		
		// handle user input first
		handleInput(dt);
		// handleSpawningItems();

		world.step(1 / 60f, 6, 2);

		player.update(dt);

		// enemy
		for (Enem enemy : creator.getEnemiess()) {
			enemy.update(dt);
			if (enemy.getX() < player.getX() + 3000 / Forests.PPM)
				enemy.b2body.setActive(true);
		}

		// Coingame
		for (CoinGame coins : creator.getCoins()) {
			coins.update(dt);
			if (coins.getX() < player.getX() + 3000 / Forests.PPM)
				coins.b2body.setActive(true);
		}

		// Coingame
		for (Brick brick : creator.getBricks()) {
			brick.update(dt);
		}

		// Item
		// for(GameItem item : items)
		// item.update(dt);

		// ene.update(dt);
		scene_screen.update(dt);

		if (player.currentState != Soldier.State.DEAD) {
			gamecam.position.x = player.b2body.getPosition().x;
		}

		// if cam out of map
		if (gamecam.position.x - Forests.V_WIDTH / 2 / Forests.PPM < 0)
			gamecam.position.x = Forests.V_WIDTH / 2 / Forests.PPM;

		if (gamecam.position.x + Forests.V_WIDTH / 2 / Forests.PPM > mapPixelWidth / Forests.PPM)
			gamecam.position.x = mapPixelWidth / Forests.PPM - Forests.V_WIDTH / 2 / Forests.PPM;

		if (gamecam.position.y - Forests.V_HEIGHT / 2 / Forests.PPM < 0)
			gamecam.position.y = Forests.V_HEIGHT / 2 / Forests.PPM;

		if (gamecam.position.y + Forests.V_HEIGHT / 2 / Forests.PPM > mapPixelHeight / Forests.PPM)
			gamecam.position.y = mapPixelHeight / Forests.PPM - Forests.V_HEIGHT / 2 / Forests.PPM;

		// gamecam.position.x = player.b2body.getPosition().x;
		// update our gamecam with correct coordinates after changes
		gamecam.update();
		// tell our renderer to draw only what our camera can see in our game
		// world.
		renderer.setView(gamecam);
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// renderer out Box2DDebugLines
		// b2dr.render(world, gamecam.combined);

		renderer.render();

		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		// enemy
		for (Enem enemy : creator.getEnemiess())
			enemy.draw(game.batch);

		for (CoinGame coins : creator.getCoins())
			coins.draw(game.batch);

		// for (GameItem item : items)
		// item.draw(game.batch);

		// ene.draw(game.batch);
		game.batch.end();

		// set our batch to now draw what the hud camera sees.
		game.batch.setProjectionMatrix(scene_screen.stage.getCamera().combined);
		scene_screen.stage.draw();

		// render out game map
		if (debug) {
			b2dr.render(world, gamecam.combined);
		}

		if (gameOver()) {
			score_g = scene_screen.getScore();
			time_g = scene_screen.getTime();
			game.setScreen(new GameOver(game, game, score_g, time_g));
			dispose();
		}
	}

	public boolean gameOver() {
		if (player.currentState == Soldier.State.DEAD && Soldier.timetodead >= 3) {
			Soldier.timetodead = 0;
			return true;
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	public TiledMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
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
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		scene_screen.dispose();
		game.dispose();
		atlas.dispose();
	//	renderer.dispose();
	//	music.dispose();
		
		
		
	}

	public void toggleDebug() {

		if (debug) {
			debug = false;
		} else {
			debug = true;
		}
	}

}

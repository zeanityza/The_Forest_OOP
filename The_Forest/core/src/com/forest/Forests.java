package com.forest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Sceens.GameOver;
import Sceens.MainMenuScreen;
import Sceens.PlayScreen;
import Sceens.StartGame;


public class Forests extends Game {
	public static final int V_WIDTH = 3700;

	public static final int V_HEIGHT = 1900;
	public static final float PPM = 400;
	
	public static final float BIT_BIT = 128;
	
	public static final short NOTHING_BIT = 0;
	public static final short DEF_BIT = 1;
	public static final short SOL_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short DESTROY_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short COINS_BIT = 8;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short SOL_HEAD_BIT = 512;
	public static final short SOL_BODY_BIT = 129;
	public static final short SOL_LEG_BIT = 130;
	public static final short GUNFIRE_BIT = 48;
	public static final short WATER_BIT = 42;
	
	
	public SpriteBatch batch;
	
	//music sound
	public static AssetManager manager;

	@Override
	public void create() {
		
		batch = new SpriteBatch();
		
		//music sound
//		manager = new AssetManager();
//		manager.load("music/walk.MP3", Music.class);
//		manager.load("music/grenade.MP3", Sound.class);
//		manager.load("music/correct_s.MP3", Sound.class);
//		manager.load("music/brick_s.MP3", Sound.class);
//		manager.finishLoading();
		
		
		//setScreen(new MainMenuScreen(this, this));
		setScreen(new StartGame(this, this));
		//setScreen(new GameOver(this,this,this,this));
	}

	@Override
	public void render() {
		super.render();
		
	}


}
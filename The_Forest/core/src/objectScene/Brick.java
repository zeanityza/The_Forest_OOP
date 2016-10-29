package objectScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.forest.Forests;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import Player.Soldier;
import Sceens.PlayScreen;
import Scenes.Sight_Scene;

public class Brick extends Tile_Object_2 {

	private float stateTime = 100f;

	private float timessz;
	private float count;
	private float ccc;
	
	private boolean setToDestroy;

	public Brick(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		fixture2.setUserData(this);
		fixture3.setUserData(this);
		setCategotyFilter(Forests.BRICK_BIT);

	}


	@Override
	public void onHeadHit(Soldier sol) {
		
		setToDestroy = true;

		//Forests.manager.get("music/brick_s.MP3", Sound.class).play();
		Sight_Scene.addScore(50);

	}

	@Override
	public void update(float dt) {
		if (setToDestroy){
			count += dt;
			//extends old in timingdt
			//ccc = getTime();
			//System.out.println("ttt " + ccc);
		}
		
		if (count > 0.5) {
			
			//.out.println(ccc);
			setCategotyFilter(Forests.DESTROY_BIT);
			getCell().setTile(null);
		}
	}

}

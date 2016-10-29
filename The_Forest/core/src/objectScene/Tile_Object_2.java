package objectScene;

import java.io.File;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.forest.Forests;

import Player.Soldier;
import Sceens.PlayScreen;



public abstract class Tile_Object_2{
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	
	protected double count;
	protected Fixture fixture;
	protected Fixture fixture2;
	protected Fixture fixture3;

	
	public Tile_Object_2(PlayScreen screen, Rectangle bounds){
		this.world = screen.getWorld();
		this.map = screen.getMap();
		this.bounds = bounds;

		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ Forests.PPM, (bounds.getY() + bounds.getHeight() /2 )/ Forests.PPM);
		body = world.createBody(bdef);
		
		shape.setAsBox(bounds.getWidth()/2/ Forests.PPM , bounds.getHeight()/2/ Forests.PPM  );
		
		fdef.shape = shape;
		fixture = body.createFixture(fdef);
		fixture2 = body.createFixture(fdef);
		fixture3 = body.createFixture(fdef);
	}
	
	public abstract void onHeadHit(Soldier sol);

	
	public abstract void update(float timecount);
	

	
	public void setCategotyFilter(short filterBit){
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
		fixture2.setFilterData(filter);
		fixture3.setFilterData(filter);
	}
	
	public TiledMapTileLayer.Cell getCell(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("displat");
		return layer.getCell((int)(body.getPosition().x * Forests.PPM / Forests.BIT_BIT), (int)(body.getPosition().y * Forests.PPM/ Forests.BIT_BIT));
	}
}

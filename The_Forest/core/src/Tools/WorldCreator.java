package Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.forest.Forests;

import Enemy.Enemies;
import Sceens.PlayScreen;
import objectScene.Brick;
import objectScene.CoinGames;

public class WorldCreator {
	private Array<Enemies> enemiess;
	private Array<CoinGames> coinss;
	
	private Array<Brick> bricks;
	
	public WorldCreator(PlayScreen screen){
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//create ground bodies/ fixtures;
		for(MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth()/2)/ Forests.PPM, (rect.getY() + rect.getHeight() /2 )/ Forests.PPM);
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth()/2/Forests.PPM, rect.getHeight()/2/ Forests.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		for(MapObject object : map.getLayers().get("GroundPoly").getObjects().getByType(PolygonMapObject.class)){
			//System.out.println(111);
			Polygon polygon = ((PolygonMapObject) object).getPolygon();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(polygon.getX() / Forests.PPM, polygon.getY() / Forests.PPM);
			body = world.createBody(bdef);
			
			float[] vertices =  polygon.getVertices();
			
			for (int i = 0; i < vertices.length; i++){
				vertices[i] /= Forests.PPM;
				//System.out.println(vertices[i]);
			}
			
			
			
			shape.set(vertices);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		for(MapObject object : map.getLayers().get("GroundLine").getObjects().getByType(PolylineMapObject.class)){
			//System.out.println(111);
			Polyline polyline = ((PolylineMapObject) object).getPolyline();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(polyline.getX() / Forests.PPM, polyline.getY() / Forests.PPM);
			body = world.createBody(bdef);
			
			float[] vertices =  polyline.getVertices();
			
			for (int i = 0; i < vertices.length; i++){
				vertices[i] /= Forests.PPM;
				//System.out.println(vertices[i]);
			}
			
			
			
			shape.set(vertices);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//create pipe bodies/fixtures;
		for(MapObject object : map.getLayers().get("Scope").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth()/2)/ Forests.PPM, (rect.getY() + rect.getHeight() /2 )/ Forests.PPM);
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth()/2/Forests.PPM, rect.getHeight()/2/ Forests.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = Forests.OBJECT_BIT;
			body.createFixture(fdef);
		}
		
		for(MapObject object : map.getLayers().get("Waters").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth()/2)/ Forests.PPM, (rect.getY() + rect.getHeight() /2 )/ Forests.PPM);
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth()/2/Forests.PPM, rect.getHeight()/2/ Forests.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = Forests.WATER_BIT;
			body.createFixture(fdef);
		}
		
		//create brick bodies
		bricks = new Array<Brick>();
		for(MapObject object : map.getLayers().get("Displat").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			// new Brick(screen, rect);
			
			bricks.add(new Brick(screen, rect));
		}
		
		
		//create Enemies
		enemiess = new Array<Enemies>();
		for(MapObject object : map.getLayers().get("Enemies").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			enemiess.add(new Enemies(screen, rect.getX() / Forests.PPM, rect.getY() / Forests.PPM));
		}
		
		//create Coins
		coinss = new Array<CoinGames>();
		for(MapObject object : map.getLayers().get("Coins").getObjects().getByType(RectangleMapObject.class)){
			Rectangle coin = ((RectangleMapObject) object).getRectangle();
			coinss.add(new CoinGames(screen, coin.getX() / Forests.PPM, coin.getY() / Forests.PPM));
		}
	}
	
	public Array<CoinGames> getCoins(){
		return  coinss;
	}
	
	public Array<Enemies> getEnemiess(){
		return  enemiess;
	}
	
	public Array<Brick> getBricks(){
		return  bricks;
	}
}

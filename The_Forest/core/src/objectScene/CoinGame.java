package objectScene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import Sceens.PlayScreen;


public abstract class CoinGame extends Sprite{
	protected World world;
	protected PlayScreen screen;
	public Body b2body;
	public Vector2 velocity;
	
	public CoinGame(PlayScreen screen, float x, float y){
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x, y);
		defineCoin();
		velocity = new Vector2(0f,0f);
		b2body.setActive(false);
		
	}
	
	protected abstract void defineCoin();
	public abstract void coinhit();
	public abstract void update(float dt);


	
	
}

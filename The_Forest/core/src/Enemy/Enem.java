package Enemy;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import Sceens.PlayScreen;


public abstract class Enem extends Sprite{
	protected World world;
	protected PlayScreen screen;
	public Body b2body;
	public Vector2 velocity;
	
	public Enem(PlayScreen screen, float x, float y){
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x, y);
		defineEnemy();
		velocity = new Vector2(-0.7f,-2);
		b2body.setActive(false);
		
	}
	
	protected abstract void defineEnemy();
	public abstract void update(float dt);
	public abstract void hitOnHead();
	public abstract void bodyhit();

	
	public void reverseVelocity(boolean x, boolean y){
		if(x){
			velocity.x = -velocity.x;
		}
		if(y)
			velocity.y = -velocity.y;
	}
	
}

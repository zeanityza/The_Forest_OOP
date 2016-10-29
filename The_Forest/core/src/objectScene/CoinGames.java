package objectScene;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.forest.Forests;

import Sceens.PlayScreen;
import Scenes.Sight_Scene;

public class CoinGames extends CoinGame{
	private float stateTime;
	private Animation coinAnimation;
	private Array<TextureRegion> frames;
	private boolean setToDestroy;
	private boolean destroyed;
	private Texture coinTexture;

	public CoinGames(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		coinTexture = new Texture("ring.gif");

		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(coinTexture, 0, 0, 16, 16));
		frames.add(new TextureRegion(coinTexture, 22, 0, 16, 16));
		frames.add(new TextureRegion(coinTexture, 0, 22, 16, 16));
		frames.add(new TextureRegion(coinTexture, 22, 22, 16, 16));
		coinAnimation = new Animation(0.2f, frames);
		frames.clear();

		stateTime = 0;
	
		setBounds(getX(), getY(), 100 / Forests.PPM, 100 / Forests.PPM);
		setToDestroy = false;
		destroyed = false;

	}

	public void update(float dt) {
		stateTime += dt;
		if (setToDestroy && !destroyed) {
			world.destroyBody(b2body);
			destroyed = true;
			setRegion(new TextureRegion(new TextureRegion(coinTexture, 22, 22, 16, 16)));
			stateTime = 0;
		
		} else if (!destroyed) {
			b2body.setLinearVelocity(velocity);
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
			setRegion(coinAnimation.getKeyFrame(stateTime, true));

		}
	}

	@Override
	protected void defineCoin() {
		// enemy
		BodyDef bdef = new BodyDef();
		// System.out.println(getY());
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.StaticBody;
		b2body = world.createBody(bdef);

		// new fdef enemy
		FixtureDef fdef = new FixtureDef();
		CircleShape Shape = new CircleShape();
		Shape.setRadius(40 / Forests.PPM);
		fdef.filter.categoryBits = Forests.COINS_BIT;
		fdef.filter.maskBits =  Forests.SOL_BIT;
		fdef.shape = Shape;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData(this);

	}
	
	public void draw(Batch batch){
		if(!destroyed || stateTime < 0)
			super.draw(batch);
	}

	@Override
	public void coinhit() {
		setToDestroy = true;
		//Forests.manager.get("music/correct_s.MP3", Sound.class).play();
		Sight_Scene.addScore(100);
		
	}
	
	
}

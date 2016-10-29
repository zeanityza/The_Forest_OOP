package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.forest.Forests;

import Sceens.PlayScreen;

public class GunFireBull extends Sprite {

	private PlayScreen screen;
	private World world;
	private Array<TextureRegion> frames;
	private Animation fireAnimation;
	float stateTime;
	boolean destroyed;
	boolean setToDestroy;
	boolean fireRight;
	public Vector2 velocity;

	private Body b2body;
	private BodyDef bdef;
	private FixtureDef fdef;

	private boolean setToSpawn;

	public GunFireBull(PlayScreen screen, float x, float y, boolean fireRight) {
		this.fireRight = fireRight;
		this.screen = screen;
		this.world = screen.getWorld();
		frames = new Array<TextureRegion>();
		for (int i = 1; i <= 13; i++) {
			frames.add(new TextureRegion(screen.getAtlas().findRegion("bull", i), 0, 0, 150, 150));
		}
		fireAnimation = new Animation(0.2f, frames);
		frames.clear();
		setRegion(fireAnimation.getKeyFrame(0));
		setBounds(x, y, 100 / Forests.PPM, 100 / Forests.PPM);
		defineFireBall();
	}

	public void defineFireBall() {
		bdef = new BodyDef();
		bdef.position.set(fireRight ? getX() + 250 / Forests.PPM : getX() - 250 / Forests.PPM, getY());
		// bdef.position.set(1,getY());
		bdef.type = BodyDef.BodyType.KinematicBody;

		// if (!world.isLocked())
		// b2body = world.createBody(bdef);

		fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(50 / Forests.PPM);
		fdef.filter.categoryBits = Forests.GUNFIRE_BIT;
		fdef.filter.maskBits = Forests.DEF_BIT | Forests.COINS_BIT | Forests.BRICK_BIT | Forests.ENEMY_BIT
				| Forests.OBJECT_BIT | Forests.ENEMY_HEAD_BIT;

		fdef.shape = shape;
		// fdef.restitution = 1;
		// fdef.friction = 0;
		fdef.isSensor = false;
		// b2body.createFixture(fdef).setUserData(this);
		// b2body.setLinearVelocity(new Vector2(fireRight ? 5f : -5f, 0));
		// b2body.setLinearVelocity(new Vector2(fireRight ? 2 : -2, 2.5f));
	}

	public void update(float dt) {
		
		// Gdx.app.exit();
		if (setToSpawn) {
			b2body = world.createBody(bdef);

			b2body.createFixture(fdef).setUserData(this);
			b2body.setLinearVelocity(new Vector2(fireRight ? 5f : -5f, 0));

			setToSpawn = false;
		
		} else{
			stateTime += dt;
			setRegion(fireAnimation.getKeyFrame(stateTime, true));
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
			if ((stateTime > 1f || setToDestroy) && !destroyed) {
				world.destroyBody(b2body);
				destroyed = true;
			}
			if (b2body.getLinearVelocity().y > 2f)
				b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
			if ((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0)) {
				 setToDestroy();
			}
		}
	}

	public void setToDestroy() {
		setToDestroy = true;
		
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void reverseVelocity(boolean x, boolean y) {
		if (x) {
			velocity.x = -velocity.x;
		}
		if (y)
			velocity.y = -velocity.y;
	}

	public void setToSpawn() {
		setToSpawn = true;
	}
	
	public boolean isSetToSpawn() {
		return setToSpawn;
	}

}

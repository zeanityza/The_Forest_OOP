package Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.forest.Forests;
import Sceens.PlayScreen;

public class Enemies extends Enem {

	private float stateTime;
	private Animation walkAnimation;
	private Array<TextureRegion> frames;
	private boolean setToDestroy;
	private boolean destroyed;

	public Enemies(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		frames = new Array<TextureRegion>();
		for (int i = 20; i <46; i++) {
			// set Enemy player
			frames.add(new TextureRegion(screen.getAtlas().findRegion("zom", i), 0, 0, 450, 520));
		}
		walkAnimation = new Animation(0.9f, frames);
		stateTime = 0;
		// setBounds(getX(), getY(),200 / Forests.PPM, 200/Forests.PPM);
		setBounds(getX(), getY(), 200 / Forests.PPM, 200 / Forests.PPM);
		setToDestroy = false;
		destroyed = false;

	}

	public void update(float dt) {
		stateTime += dt;
		//System.out.println("test =" + stateTime);
		if (setToDestroy && !destroyed) {
			world.destroyBody(b2body);
			destroyed = true;
			setRegion(new TextureRegion(screen.getAtlas().findRegion("zom", 3), 0, 0, 450, 450));
			stateTime = 0;
		
		} else if (!destroyed) {
			b2body.setLinearVelocity(velocity);
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
			setRegion(walkAnimation.getKeyFrame(stateTime, true));

		}
	}

	@Override
	protected void defineEnemy() {
		// enemy
		BodyDef bdef = new BodyDef();
		// System.out.println(getY());
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		// new fdef enemy
		FixtureDef fdef = new FixtureDef();
		CircleShape Shape = new CircleShape();
		Shape.setRadius(80 / Forests.PPM);
		fdef.filter.categoryBits = Forests.ENEMY_BIT;
		fdef.filter.maskBits = Forests.DEF_BIT | Forests.BRICK_BIT | Forests.ENEMY_BIT
				| Forests.OBJECT_BIT | Forests.SOL_BIT;
		fdef.shape = Shape;
		b2body.createFixture(fdef).setUserData(this);

		// Creatye head hear:
		PolygonShape headd = new PolygonShape();
		Vector2[] vertice = new Vector2[4];
		vertice[0] = new Vector2(-5, 8).scl(1 / Forests.PPM);
		vertice[1] = new Vector2(5, 8).scl(1 / Forests.PPM);
		vertice[2] = new Vector2(-3, 3).scl(1 / Forests.PPM);
		vertice[3] = new Vector2(3, 3).scl(1 / Forests.PPM);
//		vertice[0] = new Vector2(-100, 100).scl(1 / Forests.PPM);
//		vertice[1] = new Vector2(100, 100).scl(1 / Forests.PPM);
//		vertice[2] = new Vector2(-60, 30).scl(1 / Forests.PPM);
//		vertice[3] = new Vector2(60, 30).scl(1 / Forests.PPM);
		headd.set(vertice);

		fdef.shape = headd;
		fdef.restitution = 0.5f;
		fdef.filter.categoryBits = Forests.ENEMY_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);
		
	}
	
	public void draw(Batch batch){
		if(!destroyed || stateTime < 1)
			super.draw(batch);
	}

	@Override
	public void hitOnHead() {
		//Gdx.app.log("SOL", "asdasd");
		setToDestroy = true;

	}

	@Override
	public void bodyhit() {
		setToDestroy = true;		
	}


}

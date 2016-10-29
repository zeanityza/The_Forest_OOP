package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.forest.Forests;

import Sceens.PlayScreen;

public class Soldier extends Sprite {
	public enum State {
		FALLING, JUMPING, STANDING, RUNNING, DOWNING, ATTACK, DEAD, WALKATT
	};

	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	public Animation soldierstand;
	private TextureRegion soldierDead;
	private Animation soldierAttack;
	private Animation soldierRun;
	private Animation soldierJump;
	private Animation soldierDown;
	private Animation soldierWalkATT;
	private float stateTimer;

	private boolean runningRight;
	public boolean downright;
	public boolean walkfire;
	public boolean standfire;
	private boolean SoldierIsDead;
	private PlayScreen screen;

	public static float timetodead = 0;

	private Array<GunFireBull> gunfire;
	private Array<KnifeKill> knife;

	int fireTime = 0;

	public Soldier(PlayScreen screen) {

		// set player billy
		super(screen.getAtlas().findRegion("run"));
		this.screen = screen;
		this.world = screen.getWorld();
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 1; i <= 7; i++) {

			frames.add(new TextureRegion(screen.getAtlas().findRegion("run", i), 0, 0, 300, 300));

		}

		soldierRun = new Animation(0.07f, frames);
		frames.clear();

		for (int j = 1; j <= 5; j++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("fire", j), 0, 0, 300, 300));

		soldierAttack = new Animation(0.05f, frames);
		frames.clear();

		for (int k = 1; k <= 14; k++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("walk_low", k), 0, 0, 300, 300));

		soldierDown = new Animation(0.1f, frames);
		frames.clear();

		for (int kz = 1; kz <= 14; kz++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("walk_fire", kz), 0, 0, 300, 300));

		soldierWalkATT = new Animation(0.1f, frames);
		frames.clear();

		for (int zz = 1; zz <= 5; zz++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("jump", zz), 0, 0, 300, 300));
		// frames.add(new TextureRegion(getTexture(), i * 370,0,370,470));

		soldierJump = new Animation(0.1f, frames);
		frames.clear();

		for (int gg = 1; gg <= 7; gg++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("stand", gg), 0, 0, 300, 300));
		soldierstand = new Animation(0.15f, frames);
		// soldierstand = new
		// TextureRegion(screen.getAtlas().findRegion("stand"), 0, 0, 300, 300);
		frames.clear();

		// frames.add(new TextureRegion(screen.getAtlas().findRegion("Run_",4),
		// i*370,0,370,470));

		soldierDead = new TextureRegion(screen.getAtlas().findRegion("stand", 5), 0, 0, 300, 300);

		defineMario();

		setBounds(0, 0, 300 / Forests.PPM, 300 / Forests.PPM);
		setRegion(soldierstand.getKeyFrame(stateTimer, true));

		// GUN & KNIFE
		gunfire = new Array<GunFireBull>();
		knife = new Array<KnifeKill>();

	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));

		if (SoldierIsDead) {
			timetodead += dt;
			// System.out.println(dt);
		}

		// gun
		for (GunFireBull fire : gunfire) {
			fire.update(dt);
			if (fire.isDestroyed())
				gunfire.removeValue(fire, true);
		}

		// knife
		for (KnifeKill kill : knife) {
			kill.update(dt);
			if (kill.isDestroyed())
				knife.removeValue(kill, true);
		}
	}

	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {

		case DEAD:
			region = soldierDead;
			break;
		case JUMPING:
			region = soldierJump.getKeyFrame(stateTimer, true);
			break;
		case RUNNING:
			region = soldierRun.getKeyFrame(stateTimer, true);
			break;
		case DOWNING:
			region = soldierDown.getKeyFrame(stateTimer, true);
			break;
		case WALKATT:
			region = soldierWalkATT.getKeyFrame(stateTimer, true);
			break;
		case ATTACK:
			region = soldierAttack.getKeyFrame(stateTimer, true);
			break;
		case FALLING:
		case STANDING:
		default:
			region = soldierstand.getKeyFrame(stateTimer, true);
			break;
		}
		if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;

	}

	public State getState() {
		if (SoldierIsDead)

			return State.DEAD;

		if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
			return State.JUMPING;
		else if (standfire)
			return State.ATTACK;

		else if (downright)
			return State.DOWNING;
		else if (walkfire)
			return State.WALKATT;
		else if (b2body.getLinearVelocity().y < 0)
			return State.FALLING;
		else if (b2body.getLinearVelocity().x != 0)
			return State.RUNNING;

		else
			return State.STANDING;
	}

	public void defineMario() {
		BodyDef bdef = new BodyDef();

		// bdef.position.set(32 / Forests.PPM, 32/ Forests.PPM);
		bdef.position.set(500 / Forests.PPM, 500 / Forests.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		// bdef.angularDamping = 30;
		b2body = world.createBody(bdef);

		// new fdef and fdef2
		FixtureDef fdef = new FixtureDef();

		// fdef2.density = 7;

		CircleShape Shape = new CircleShape();

		// Shape.setRadius(6/ Forests.PPM);
		Shape.setRadius(80 / Forests.PPM);

		// part13 put value of mygdxgame to fdef
		fdef.filter.categoryBits = Forests.SOL_BIT;
		fdef.filter.maskBits = Forests.DEF_BIT | Forests.COINS_BIT | Forests.BRICK_BIT | Forests.ENEMY_BIT
				| Forests.ENEMY_HEAD_BIT;

		// fdef shape and fdef2 shape
		fdef.shape = Shape;
		// b2body.createFixture(fdef);
		b2body.createFixture(fdef).setUserData(this);

		// head shape
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-31 / Forests.PPM, -80 / Forests.PPM), new Vector2(31 / Forests.PPM, -80 / Forests.PPM));
		// head.set(new Vector2(-1 / Forests.PPM, 80 / Forests.PPM), new
		// Vector2(1 / Forests.PPM, 80 / Forests.PPM));
		fdef.filter.categoryBits = Forests.SOL_HEAD_BIT;
		fdef.shape = head;
		fdef.isSensor = true;
		// b2body.createFixture(fdef).setUserData("head");
		b2body.createFixture(fdef).setUserData(this);

	}

	public boolean isDead() {
		return SoldierIsDead;
	}

	public void hit() {
		// Gdx.app.log("SOL", "ttt");
		// Forests.manager.get("music/grenade.MP3", Sound.class).play();

		SoldierIsDead = true;
		Filter filter = new Filter();
		filter.maskBits = Forests.NOTHING_BIT;

		for (Fixture fixture : b2body.getFixtureList()) {
			fixture.setFilterData(filter);
		}

		b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);

	}

	// GUN
	public void GunFire() {

		GunFireBull fireBall = new GunFireBull(screen, b2body.getPosition().x, b2body.getPosition().y,
				runningRight ? true : false);
		fireBall.setToSpawn();
		
		
		gunfire.add(fireBall);

		fireTime++;
		System.out.println(fireTime);
	}

	public void Knifekilling() {
		knife.add(new KnifeKill(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
	}

	public void draw(Batch batch) {
		super.draw(batch);
		for (GunFireBull fire : gunfire)
			if (!fire.isSetToSpawn()) {
				fire.draw(batch);
			}

		for (KnifeKill kill : knife)
			kill.draw(batch);
	}

	public void hitWater() {
		SoldierIsDead = true;

	}

}

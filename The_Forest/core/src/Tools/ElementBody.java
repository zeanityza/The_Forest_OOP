package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.forest.Forests;
import Enemy.Enem;
import Player.Soldier;
import objectScene.CoinGame;
import objectScene.Tile_Object_2;

public class ElementBody implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// fixture 1 2 3 head body leg
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch (cDef) {

		case Forests.SOL_HEAD_BIT | Forests.BRICK_BIT:
			if (fixA.getFilterData().categoryBits == Forests.SOL_HEAD_BIT)
				((Tile_Object_2) fixB.getUserData()).onHeadHit((Soldier) fixA.getUserData());
			else
				((Tile_Object_2) fixA.getUserData()).onHeadHit((Soldier) fixB.getUserData());
			break;

		case Forests.ENEMY_HEAD_BIT | Forests.SOL_BIT:
			if (fixA.getFilterData().categoryBits == Forests.ENEMY_HEAD_BIT)
				((Enem) fixA.getUserData()).hitOnHead();
			// else if (fixB.getFilterData().categoryBits ==
			// Forests.ENEMY_HEAD_BIT)
			else
				((Enem) fixB.getUserData()).hitOnHead();
			break;
		case Forests.ENEMY_BIT | Forests.OBJECT_BIT:
			if (fixA.getFilterData().categoryBits == Forests.ENEMY_BIT)
				((Enem) fixA.getUserData()).reverseVelocity(true, false);

			else
				((Enem) fixB.getUserData()).reverseVelocity(true, false);
			break;
		case Forests.ENEMY_BIT | Forests.BRICK_BIT:
			if (fixA.getFilterData().categoryBits == Forests.ENEMY_BIT)
				((Enem) fixA.getUserData()).reverseVelocity(true, false);

			else
				((Enem) fixB.getUserData()).reverseVelocity(true, false);
			break;

		case Forests.ENEMY_BIT | Forests.DEF_BIT:
			if (fixA.getFilterData().categoryBits == Forests.ENEMY_BIT)
				((Enem) fixA.getUserData()).reverseVelocity(true, false);
			else
				((Enem) fixB.getUserData()).reverseVelocity(true, false);
			break;

		case Forests.SOL_BIT | Forests.ENEMY_BIT:

			if (fixA.getFilterData().categoryBits == Forests.SOL_BIT) {
				// Gdx.app.log("SOL", "DIED2");
				((Soldier) fixA.getUserData()).hit();
			} else if (fixB.getFilterData().categoryBits == Forests.SOL_BIT) {
				// Gdx.app.log("SOL", "DIED3");
				((Soldier) fixB.getUserData()).hit();
			}

			break;

		case Forests.ENEMY_BIT | Forests.ENEMY_BIT:
			((Enem) fixA.getUserData()).reverseVelocity(true, false);
			((Enem) fixB.getUserData()).reverseVelocity(true, false);
			break;
		//
		case Forests.ENEMY_HEAD_BIT | Forests.GUNFIRE_BIT:
			Gdx.app.log("SOL", "ss");
			if (fixA.getFilterData().categoryBits == Forests.ENEMY_HEAD_BIT) {
				// Gdx.app.log("SOL", "aaa");
				((Enem) fixA.getUserData()).hitOnHead();
			}

			else {
				// Gdx.app.log("SOL", "bbb");
				((Enem) fixB.getUserData()).hitOnHead();
			}
			break;

		case Forests.ENEMY_BIT | Forests.GUNFIRE_BIT:
			if (fixA.getFilterData().categoryBits == Forests.ENEMY_BIT) {
				// Gdx.app.log("SOL", "ccc");
				((Enem) fixA.getUserData()).bodyhit();
			} else {
				// Gdx.app.log("SOL", "ddd");
				((Enem) fixB.getUserData()).bodyhit();
			}
			break;

		case Forests.COINS_BIT | Forests.SOL_BIT:
			if (fixA.getFilterData().categoryBits == Forests.COINS_BIT)
				((CoinGame) fixA.getUserData()).coinhit();
			// else if (fixB.getFilterData().categoryBits ==
			// Forests.ENEMY_HEAD_BIT)
			else
				((CoinGame) fixB.getUserData()).coinhit();
			break;
			
		case Forests.SOL_BIT | Forests.WATER_BIT:

			if (fixA.getFilterData().categoryBits == Forests.SOL_BIT) {
				// Gdx.app.log("SOL", "DIED2");
				((Soldier) fixA.getUserData()).hitWater();
			} else if (fixB.getFilterData().categoryBits == Forests.SOL_BIT) {
				// Gdx.app.log("SOL", "DIED3");
				((Soldier) fixB.getUserData()).hitWater();
			}

			break;

		}

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}

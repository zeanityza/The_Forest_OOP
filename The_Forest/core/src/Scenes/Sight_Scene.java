package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forest.Forests;


public class Sight_Scene implements Disposable{
	
	public Stage stage;
	private Viewport viewport;
	private Integer worldTimer;
	private float timeCount;
	private static Integer score;
	
	private Label countdownLabel;
	private static Label scoreLabel;
	private Label timeLabel;
	private Label levelLabel;
	private Label worldLabel;
	private Label marioLabel;
	
	public Sight_Scene(SpriteBatch sb){
		worldTimer = 0;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(Forests.V_WIDTH, Forests.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.RED));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.RED));
		timeLabel = new Label("Survival Time:", new Label.LabelStyle(new BitmapFont(), Color.RED));
		levelLabel = new Label("Forest 1", new Label.LabelStyle(new BitmapFont(), Color.RED));
		worldLabel = new Label("Ronnie", new Label.LabelStyle(new BitmapFont(), Color.RED));
		marioLabel = new Label("Score:", new Label.LabelStyle(new BitmapFont(), Color.RED));
		

		countdownLabel.setFontScale((float)6.0);
		scoreLabel.setFontScale((float)6.0);
		timeLabel.setFontScale((float)6.0);
		levelLabel.setFontScale((float)6.0);
		worldLabel.setFontScale((float)6.0);
		marioLabel.setFontScale((float)6.0);
		
		table.add(worldLabel).expandX().padTop(10);
		table.add(marioLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row();
		table.add(levelLabel).expandX();
		table.add(scoreLabel).expandX();
		table.add(countdownLabel).expandX();
		
		stage.addActor(table);
		
}
	
	public void update(float dt){
		timeCount += dt;
		if(timeCount >= 1){
			worldTimer ++;
			countdownLabel.setText(String.format("%03d", worldTimer));
			timeCount = 0;
		}
	}
	
	public static void addScore(int value){
		score += value;
		scoreLabel.setText(String.format("%06d", score));
	}
	
	public int getScore(){
		
		return score;
	}
	
public int getTime(){
		
		return worldTimer;
	}
	
	@Override
	public void dispose(){
		stage.dispose();
	}
	
	
}
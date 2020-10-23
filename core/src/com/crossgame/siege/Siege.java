package com.crossgame.siege;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.crossgame.siege.GSM.Help;
import com.crossgame.siege.GSM.MainMenuScreen;

public class Siege extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int  HEIGHT = 480;
	public static final String TITLE = "Siege";

	private SpriteBatch batch;
	private MainMenuScreen mainScreen;//Главное меню
	private Help help;
	private StageScreen stageScreen;//Поле бытвы

    private void bolleanswitch(){
		//Для корректного отображения некоторых значений в игре
		if(mainScreen.isMainActive ()){
			stageScreen.setScoreCount ( 0 );
			stageScreen.setEnemiesDiedCount ( 0 );
		}
        if(!mainScreen.isMainActive ()){
            stageScreen.setStageActive ( true );
        }
        if(!stageScreen.isStageActive ()){
			mainScreen.setMainActive ( true );
		}
        if(mainScreen.isMainActive ()){
			Gdx.input.setInputProcessor ( mainScreen.getMenuStage () );
		}
		if(stageScreen.isStageActive ()){
			Gdx.input.setInputProcessor ( stageScreen.getStageScreenStage () );
		}
		if(mainScreen.isJoysticActive ()){
			stageScreen.setActivateTouchpad ( true );
		}
		if(!mainScreen.isJoysticActive ()){
			stageScreen.setActivateTouchpad ( false );
		}
		if(mainScreen.isHelpActive ()){
			help.setHelpActive ( true );
			if(Gdx.input.isTouched ()){
				help.setHelpActive ( false );
				mainScreen.setHelpActive ( false );
			}
		}

    }

    private void musicSwitch(){
		/**Играет музыка в главном меню*/
		if(mainScreen.isMainActive ()){
			stageScreen.getBattleMusic ().stop ();
			stageScreen.getBossBattleMusic ().stop ();
			stageScreen.getSafeZoneMusic ().stop ();
			mainScreen.getThemeMusic ().play ();
		}else if(!mainScreen.isMainActive ()){
			mainScreen.getThemeMusic ().stop ();
		}

		if(stageScreen.isStageActive () && !stageScreen.isSafe () && stageScreen.getLevelCount () != 9) {
			stageScreen.getBattleMusic ().play ();
		}
		if(stageScreen.isStageActive () && stageScreen.getLevelCount () == 9 && !stageScreen.isSafe ()){
			stageScreen.getBattleMusic ().stop ();
			stageScreen.getBossBattleMusic ().play ();
		}else if(!stageScreen.isStageActive () || stageScreen.getLevelCount () != 9){
			stageScreen.getBossBattleMusic ().stop ();
		}

		if(stageScreen.isStageActive () && stageScreen.isSafe () && stageScreen.getLevelCount () != 10){
			stageScreen.getBattleMusic ().stop ();
			stageScreen.getBossBattleMusic ().stop ();
			stageScreen.getSafeZoneMusic ().play ();
		}else if(stageScreen.isStageActive () && !stageScreen.isSafe ()){
			stageScreen.getSafeZoneMusic ().stop();
		}
		if(stageScreen.isStageActive () && stageScreen.getLevelCount () == 10){
			stageScreen.getBattleMusic ().stop ();
			stageScreen.getBossBattleMusic ().stop ();
			stageScreen.getFinishMusic ().play ();
		}
		if(!stageScreen.isStageActive ()){
			stageScreen.getBattleMusic ().stop ();
			stageScreen.getBossBattleMusic ().stop ();
			stageScreen.getSafeZoneMusic ().stop ();
			stageScreen.getFinishMusic ().stop ();
		}
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		mainScreen = new MainMenuScreen ();
		help = new Help ();
		stageScreen = new StageScreen ();
	}

	@Override
	public void render () {
		update ();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (mainScreen.isMainActive () && !mainScreen.isHelpActive ()){
			mainScreen.render(batch);		//menu + menuBackground
		}
		if(help.isHelpActive () && mainScreen.isMainActive ()){
			help.render ( batch );
		}
		if (stageScreen.isStageActive ()){
			//System.out.println("stage is true");
			stageScreen.render(batch);
		}

		batch.end();

		if(mainScreen.isMainActive () && !stageScreen.isStageActive () && !mainScreen.isHelpActive ()){
			mainScreen.stage ();
		}
		if(stageScreen.isStageActive ()){
			stageScreen.renderStage ();
		}
	}

	public void update() {
		mainScreen.update();
		help.update ();
        bolleanswitch ();
        musicSwitch ();


		stageScreen.update();

	}
	
	@Override
	public void dispose () {
		super.dispose ();
		batch.dispose();
		mainScreen.dispose ();
		stageScreen.dispose ();
	}
}

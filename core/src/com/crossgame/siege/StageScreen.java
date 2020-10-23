package com.crossgame.siege;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.crossgame.siege.Enemies.EnemyBat;
import com.crossgame.siege.Enemies.EnemyBossDeath;
import com.crossgame.siege.Enemies.EnemyDarkLord;
import com.crossgame.siege.Enemies.EnemyGhost;
import com.crossgame.siege.Enemies.EnemyImp;
import com.crossgame.siege.Enemies.EnemyMinotaur;
import com.crossgame.siege.Enemies.EnemyOrc;
import com.crossgame.siege.Enemies.EnemySkeleton;
import com.crossgame.siege.Enemies.EnemySlime;
import com.crossgame.siege.Hero.FireBall;
import com.crossgame.siege.Hero.Player;

/**
 * Created by Fenrir on 22.01.2017.
 */

public class StageScreen extends GameCamera {

    //Музыка
    private Music battleMusic;//обычный бой
    private Music bossBattleMusic;// бой с босом
    private Music safeZoneMusic;//безопасная зона
    private Music finishMusic;//победная музыка

    //Гетеры
    public Music getBattleMusic () {
        return battleMusic;
    }

    public Music getBossBattleMusic () {
        return bossBattleMusic;
    }

    public Music getSafeZoneMusic() {
        return safeZoneMusic;
    }

    public Music getFinishMusic() {
        return finishMusic;
    }

    //Кнопки управления

    private Stage stageScreenStage;//немного тафталогии)

    public Stage getStageScreenStage () {
        return stageScreenStage;
    }

    SpriteBatch batch;

    //Тачпад для передвижения игрока
    private Touchpad playerTouchpad;
    private TouchpadStyle playerTouchpadStyle;
    private Skin playerTouchpadSkin;
    private Drawable playerTouchBackground;
    private Drawable playerTouchKnob;

    //Тачпад для управления огнеными шарами
    private Touchpad fireTouchpad;
    private TouchpadStyle fireTouchpadStyle;
    private Skin fireTouchpadSkin;
    private Drawable fireTouchBackground;
    private Drawable fireTouchKnob;

    private Vector2 midle;/**чтобы поместить игрока в центр экрана*/
    private Vector2 dispos;

    private Player player;

    private static FireBall[] fireBalls;

    private static EnemySlime[] slimes;
    private static EnemyBat[] bats;
    private static EnemyOrc[] orcs;
    private static EnemyImp[] imps;
    private static EnemyMinotaur[] minotaurs;
    private static EnemySkeleton[] skeletons;
    private static EnemyGhost[] ghosts;
    private static EnemyDarkLord[] darkLords;
    private static EnemyBossDeath[] deaths;

    /**Для текста на экране*/
    private ImageButton hud;
    private Texture texHud;
    private TextureRegion regHud;
    private TextureRegionDrawable drHud;

    private BitmapFont playerHP;
    private BitmapFont score;
    private BitmapFont level;
    private BitmapFont enemiesDied;

    private Vector2 posHud;
    private Vector2 posPlayerHP;
    private Vector2 posScore;
    private Vector2 posLevel;
    private Vector2 posEnemiesDied;

    private int scoreCount;

    public void setScoreCount ( int scoreCount ) {
        this.scoreCount = scoreCount;
    }

    private int levelCount;

    public int getLevelCount () {
        return levelCount;
    }

    private int safeCount;
    public int getSafeCount () {
        return safeCount;
    }

    private int enemiesDiedCount;

    public void setEnemiesDiedCount ( int enemiesDiedCount ) {
        this.enemiesDiedCount = enemiesDiedCount;
    }

    /**Для перехода на следующие уровни (сложности?)*/
    private final int LEVEL2 = 15;
    private int LEVEL3 = LEVEL2 + 15;
    private int LEVEL4 = LEVEL3 + 15;
    private int LEVEL5 = LEVEL4 + 15;
    private int LEVEL6 = LEVEL5 + 15;
    private int LEVEL7 = LEVEL6 + 15;
    private int LEVEL8 = LEVEL7 + 15;
    private int LEVEL9 = LEVEL8 + 4;
    private int FINISH = LEVEL9 + 1;

    private boolean stageActive;/**Для управления активностью Сцены*/
    public boolean isStageActive () {
        return stageActive;
    }

    public void setStageActive ( boolean stageActive ) {
        this.stageActive = stageActive;
    }

    private boolean activateTouchpad;
    public boolean isActivateTouchpad() { return activateTouchpad; }

    public void setActivateTouchpad ( boolean activateTouchpad ) {
        this.activateTouchpad = activateTouchpad;
    }

    private boolean safe;
    public boolean isSafe () { return safe; }

    private boolean gameOver;
    public boolean isGameOver(){
        return gameOver;
    }

    /**ля текстур заднего фона сцены
     * котоая делится на 9 уровней*/
    private static Texture texSafeZone;
    private static Texture texSlimeDungeon;
    private static Texture texBatDungeon;
    private static Texture texOrcDungeon;
    private static Texture texImpDungeon;
    private static Texture texMinotaurDungeon;
    private static Texture texSkeletonDungeon;
    private static Texture texGhostDungeon;
    private static Texture texDarkLordDungeon;
    private static Texture texDeathDungeon;
    private static Texture texFinish;
    private static Texture texGameOver;

    private final int FIREBALL_COUNTS = 20;

    private float fireballstart[];

    /**Для стрельбы Огнеными шарами*/
    private int fireCounterRight;
    private int fireCounterLeft;
    private int fireCounterUp;
    private int fireCounterDown;
    private int fireRate;

    /**Максимальное колличество врагов на сцене*/
    private final int SLIME_COUNT = 15;
    private final int BAT_COUNT = 15;
    private final int ORC_COUNT = 15;
    private final int IMP_COUNT = 15;
    private final int MINOTAUR_COUNT = 15;
    private final int SKELETON_COUNT = 15;
    private final int GHOST_COUNT = 15;
    private final int DARKLORD_COUNT = 5;
    private final int DEATH_COUNT = 1;

    public StageScreen() {
        stageActive = false;
        activateTouchpad = true;

        safe = false;
        gameOver = false;
        safeCount = 0;

        battleMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "Audio/bgm/Battle1.ogg" ) );
        battleMusic.setLooping ( true );
        bossBattleMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "Audio/bgm/Battle3.ogg" ) );
        bossBattleMusic.setLooping ( true );
        safeZoneMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "Audio/bgm/Dungeon1.ogg" ) );
        safeZoneMusic.setLooping ( true );
        finishMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "Audio/bgm/Theme1.ogg" ) );
        finishMusic.setLooping ( true );

        //camera
        camera.setToOrtho ( false, Siege.WIDTH, Siege.HEIGHT );
        //camera.setToOrtho ( false, Gdx.graphics.getWidth (), Gdx.graphics.getHeight () );

        midle = new Vector2 ( Siege.WIDTH/2, Siege.HEIGHT/2 );// для тестов
        dispos = new Vector2 ( - 1000, - 1000 );

        texSafeZone = new Texture ( Gdx.files.internal ( "Backgrounds/SafeZone.png" ) );
        texSlimeDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/SlimeDungeon.png" ) );
        texBatDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/BatDungeon.png" ) );
        texOrcDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/OrcDungeon.png" ) );
        texImpDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/ImpDungeon.png" ) );
        texMinotaurDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/MinotaurDungeon.png" ) );
        texSkeletonDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/SkeletonDungeon.png" ) );
        texGhostDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/GhostDungeon.png" ) );
        texDarkLordDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/DarkLordDungeon.png" ) );
        texDeathDungeon = new Texture ( Gdx.files.internal ( "Backgrounds/DeathDungeon.png" ) );
        texFinish = new Texture ( Gdx.files.internal ( "Backgrounds/Finish.png" ) );
        texGameOver = new Texture ( Gdx.files.internal ( "Backgrounds/GameOver.png" ) );

        //для сенсорного управления
        batch = new SpriteBatch (  );


        /**Для управления персонажем*/
        //create a touchpad skin
        playerTouchpadSkin = new Skin ();
        //set background image
        playerTouchpadSkin.add ( "touchBackground", new Texture ( Gdx.files.internal ( "ActionButtons/touchBackground.png" ) ) );
        //set knob  image
        playerTouchpadSkin.add ( "touchKnob", new Texture ( Gdx.files.internal ( "ActionButtons/touchKnob.png" ) ) );
        //create touchpad Style
        playerTouchpadStyle = new TouchpadStyle (  );
        //Create Drawable's from TouchPad skin
        playerTouchBackground = playerTouchpadSkin.getDrawable ( "touchBackground" );
        playerTouchKnob = playerTouchpadSkin.getDrawable ( "touchKnob" );
        //Apply the Drawables to the TouchPad Style
        playerTouchpadStyle.background = playerTouchBackground;
        playerTouchpadStyle.knob = playerTouchKnob;
        //Create new TouchPad with the created style
        playerTouchpad = new Touchpad ( 10, playerTouchpadStyle );
        //setBounds(x,y,width,height)
        playerTouchpad.setBounds ( 0, 0, 200, 200 );

        /**Для управления огнём*/
        //create a touchpad skin
        fireTouchpadSkin = new Skin ();
        //set background image
        fireTouchpadSkin.add ( "touchBackground", new Texture ( Gdx.files.internal ( "ActionButtons/touchBackground.png" ) ) );
        //set knob  image
        fireTouchpadSkin.add ( "touchKnob", new Texture ( Gdx.files.internal ( "ActionButtons/touchKnob.png" ) ) );
        //create touchpad Style
        fireTouchpadStyle = new TouchpadStyle (  );
        //Create Drawable's from TouchPad skin
        fireTouchBackground = fireTouchpadSkin.getDrawable ( "touchBackground" );
        fireTouchKnob = fireTouchpadSkin.getDrawable ( "touchKnob" );
        //Apply the Drawables to the TouchPad Style
        fireTouchpadStyle.background = fireTouchBackground;
        fireTouchpadStyle.knob = fireTouchKnob;
        //Create new TouchPad with the created style
        fireTouchpad = new Touchpad ( 10, fireTouchpadStyle );
        //setBounds(x,y,width,height)
        fireTouchpad.setBounds ( Gdx.graphics.getWidth () - 200, 0, 200, 200 );

        //Create a Stage and add TouchPad
        stageScreenStage = new Stage ( new ScreenViewport (  ) );

        if(activateTouchpad) {
            stageScreenStage.addActor ( playerTouchpad );
            stageScreenStage.addActor ( fireTouchpad );
        }

        //Данные на экране
        texHud = new Texture ( Gdx.files.internal ( "sprites.Player/HUD1.png" ) );
        playerHP = new BitmapFont ();
        score = new BitmapFont ();
        level = new BitmapFont ();
        enemiesDied = new BitmapFont ();

        scoreCount = 0;
        levelCount = 1;
        enemiesDiedCount = 0;


        fireRate = 20;

        fireballstart = new float[ FIREBALL_COUNTS ];
        /**Герой*/
        player = new Player ();
        fireBalls = new FireBall[ FIREBALL_COUNTS ];
        for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
            fireBalls[ i ] = new FireBall ();
        }
        /**Враги*/
        if ( !safe ) {
            slimes = new EnemySlime[ SLIME_COUNT ];
            for ( int i = 0; i < SLIME_COUNT; i++ ) {
                slimes[ i ] = new EnemySlime ();
            }

            bats = new EnemyBat[ BAT_COUNT ];
            for ( int i = 0; i < BAT_COUNT; i++ ) {
                bats[ i ] = new EnemyBat ();
            }

            orcs = new EnemyOrc[ ORC_COUNT ];
            for ( int i = 0; i < ORC_COUNT; i++ ) {
                orcs[ i ] = new EnemyOrc ();
            }

            imps = new EnemyImp[ IMP_COUNT ];
            for ( int i = 0; i < IMP_COUNT; i++ ) {
                imps[ i ] = new EnemyImp ();
            }

            minotaurs = new EnemyMinotaur[ MINOTAUR_COUNT ];
            for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
                minotaurs[ i ] = new EnemyMinotaur ();
            }

            skeletons = new EnemySkeleton[ SKELETON_COUNT ];
            for ( int i = 0; i < SKELETON_COUNT; i++ ) {
                skeletons[ i ] = new EnemySkeleton ();
            }

            ghosts = new EnemyGhost[ GHOST_COUNT ];
            for ( int i = 0; i < GHOST_COUNT; i++ ) {
                ghosts[ i ] = new EnemyGhost ();
            }

            darkLords = new EnemyDarkLord[ DARKLORD_COUNT ];
            for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
                darkLords[ i ] = new EnemyDarkLord ();
            }

            deaths = new EnemyBossDeath[ DEATH_COUNT ];
            for ( int i = 0; i < DEATH_COUNT; i++ ) {
                deaths[ i ] = new EnemyBossDeath ();
            }
        }
    }

    @Override
    protected void handleInput () {
        fireFireBalls ();

        playerMove ();
    }

    /**Пререндер))*/

    /**Для отрисовки перемещения Героя*/
    private void renderPlayer(SpriteBatch batch){

        if(!Gdx.input.isKeyPressed ( Input.Keys.S )&&!Gdx.input.isKeyPressed ( Input.Keys.W )&&!Gdx.input.isKeyPressed ( Input.Keys.A )&&!Gdx.input.isKeyPressed ( Input.Keys.D )){
            player.playerDoNothing (batch);
        }

        if (Gdx.input.isKeyPressed (Input.Keys.S)){
            player.renderPlayerDown ( batch );
        }else if (Gdx.input.isKeyPressed (Input.Keys.W)) {
            player.renderPlayerUp ( batch );
        }
        if (Gdx.input.isKeyPressed (Input.Keys.A)){
            player.renderPlayerLeft ( batch );
        }else if (Gdx.input.isKeyPressed (Input.Keys.D)){
            player.renderPlayerRight ( batch );
        }

        if(activateTouchpad) {
            if ( playerTouchpad.getKnobX () > 140 ) {
                player.renderPlayerRight ( batch );
            }
            if ( playerTouchpad.getKnobX () < 60 ) {
                player.renderPlayerLeft ( batch );
            }
            if ( playerTouchpad.getKnobY () > 140 ) {
                player.renderPlayerUp ( batch );
            }
            if ( playerTouchpad.getKnobY () < 60 ) {
                player.renderPlayerDown ( batch );
            }
        }
    }

    private void renderSlime(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < SLIME_COUNT; i++ ) {
            //Справа от Героя
            if(slimes[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(slimes[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(slimes[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(slimes[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(slimes[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(slimes[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(slimes[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(slimes[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && slimes[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                slimes[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderBat(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < BAT_COUNT; i++ ) {
            //Справа от Героя
            if(bats[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(bats[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(bats[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(bats[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(bats[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(bats[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(bats[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(bats[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && bats[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                bats[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderOrc(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < ORC_COUNT; i++ ) {
            //Справа от Героя
            if(orcs[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(orcs[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(orcs[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(orcs[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(orcs[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(orcs[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(orcs[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(orcs[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && orcs[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                orcs[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderImp(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < IMP_COUNT; i++ ) {
            //Справа от Героя
            if(imps[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(imps[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(imps[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(imps[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(imps[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(imps[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(imps[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(imps[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && imps[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                imps[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderMinotaur(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
            //Справа от Героя
            if(minotaurs[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(minotaurs[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(minotaurs[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(minotaurs[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(minotaurs[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(minotaurs[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(minotaurs[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(minotaurs[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && minotaurs[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                minotaurs[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderSkeleton(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < SKELETON_COUNT; i++ ) {
            //Справа от Героя
            if(skeletons[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(skeletons[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(skeletons[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(skeletons[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(skeletons[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(skeletons[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(skeletons[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(skeletons[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && skeletons[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                skeletons[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderGhost(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < GHOST_COUNT; i++ ) {
            //Справа от Героя
            if(ghosts[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(ghosts[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(ghosts[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(ghosts[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(ghosts[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(ghosts[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(ghosts[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(ghosts[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && ghosts[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                ghosts[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderDarkLord(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
            //Справа от Героя
            if(darkLords[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(darkLords[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(darkLords[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(darkLords[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(darkLords[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(darkLords[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(darkLords[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(darkLords[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && darkLords[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                darkLords[i].renderEnemiesLeft ( batch );
            }
        }
    }

    private void renderDeath(SpriteBatch batch){
        /**Отрисовка анимации для перемещения к Герою*/

        for ( int i = 0; i < DEATH_COUNT; i++ ) {
            //Справа от Героя
            if(deaths[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesLeft ( batch );
            }
            //Справа внизу от Героя
            if(deaths[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesLeft ( batch );
            }
            //Внизу от Героя
            if(deaths[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesUp ( batch );
            }
            //Слева внизу от Героя
            if(deaths[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesRight ( batch );
            }
            //Слева от Героя
            if(deaths[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesRight ( batch );
            }
            //Слева вверху от Героя
            if(deaths[i].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesRight ( batch );
            }
            //Вверху от Героя
            if(deaths[i].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesDown ( batch );
            }
            //Справа вверху от Героя
            if(deaths[i].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && deaths[i].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y){
                deaths[i].renderEnemiesLeft ( batch );
            }
        }
    }

    /**Для тэстов. Задаём начальную позицию*/
/**    public void tests(){
        for ( int i = 0; i < SLIME_COUNT; i++ ) {
            slimes[i].setPositionOfEnemies ( test );
        }
        for ( int i = 0; i < BAT_COUNT; i++ ) {
            bats[i].setPositionOfEnemies ( test );
        }
    }
*/
    /**Логика*/
    private void slimeMoveToPlayer() {
        for ( int i = 0; i < SLIME_COUNT; i++ ) {
            //Справа от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().x -= slimes[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().x -= slimes[ i ].getSpeed ();
                slimes[ i ].getPositionOfEnemies ().y += slimes[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().y += slimes[ i ].getSpeed ();
            }
            //Слева внизу от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().x += slimes[ i ].getSpeed ();
                slimes[ i ].getPositionOfEnemies ().y += slimes[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().x += slimes[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().x += slimes[ i ].getSpeed ();
                slimes[ i ].getPositionOfEnemies ().y -= slimes[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().y -= slimes[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( slimes[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && slimes[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                slimes[ i ].getPositionOfEnemies ().x -= slimes[ i ].getSpeed ();
                slimes[ i ].getPositionOfEnemies ().y -= slimes[ i ].getSpeed ();
            }
        }
    }

    private void batMoveToPlayer() {
        for ( int i = 0; i < BAT_COUNT; i++ ) {
            //Справа от Героя
            if ( bats[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().x -= bats[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( bats[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().x -= bats[ i ].getSpeed ();
                bats[ i ].getPositionOfEnemies ().y += bats[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( bats[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().y += bats[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( bats[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().x += bats[ i ].getSpeed ();
                bats[ i ].getPositionOfEnemies ().y += bats[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( bats[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().x += bats[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( bats[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().x += bats[ i ].getSpeed ();
                bats[ i ].getPositionOfEnemies ().y -= bats[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( bats[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().y -= bats[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( bats[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && bats[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                bats[ i ].getPositionOfEnemies ().x -= bats[ i ].getSpeed ();
                bats[ i ].getPositionOfEnemies ().y -= bats[ i ].getSpeed ();
            }
        }
    }

    private void orcMoveToPlayer() {
        for ( int i = 0; i < ORC_COUNT; i++ ) {
            //Справа от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().x -= orcs[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().x -= orcs[ i ].getSpeed ();
                orcs[ i ].getPositionOfEnemies ().y += orcs[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().y += orcs[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().x += orcs[ i ].getSpeed ();
                orcs[ i ].getPositionOfEnemies ().y += orcs[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().x += orcs[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().x += orcs[ i ].getSpeed ();
                orcs[ i ].getPositionOfEnemies ().y -= orcs[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().y -= orcs[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( orcs[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && orcs[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                orcs[ i ].getPositionOfEnemies ().x -= orcs[ i ].getSpeed ();
                orcs[ i ].getPositionOfEnemies ().y -= orcs[ i ].getSpeed ();
            }
        }
    }

    private void impMoveToPlayer() {
        for ( int i = 0; i < IMP_COUNT; i++ ) {
            //Справа от Героя
            if ( imps[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().x -= imps[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( imps[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().x -= imps[ i ].getSpeed ();
                imps[ i ].getPositionOfEnemies ().y += imps[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( imps[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().y += imps[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( imps[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().x += imps[ i ].getSpeed ();
                imps[ i ].getPositionOfEnemies ().y += imps[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( imps[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().x += imps[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( imps[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().x += imps[ i ].getSpeed ();
                imps[ i ].getPositionOfEnemies ().y -= imps[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( imps[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().y -= imps[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( imps[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && imps[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                imps[ i ].getPositionOfEnemies ().x -= imps[ i ].getSpeed ();
                imps[ i ].getPositionOfEnemies ().y -= imps[ i ].getSpeed ();
            }
        }
    }

    private void minotaurMoveToPlayer() {
        for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
            //Справа от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().x -= minotaurs[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().x -= minotaurs[ i ].getSpeed ();
                minotaurs[ i ].getPositionOfEnemies ().y += minotaurs[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().y += minotaurs[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().x += minotaurs[ i ].getSpeed ();
                minotaurs[ i ].getPositionOfEnemies ().y += minotaurs[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().x += minotaurs[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().x += minotaurs[ i ].getSpeed ();
                minotaurs[ i ].getPositionOfEnemies ().y -= minotaurs[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().y -= minotaurs[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( minotaurs[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && minotaurs[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                minotaurs[ i ].getPositionOfEnemies ().x -= minotaurs[ i ].getSpeed ();
                minotaurs[ i ].getPositionOfEnemies ().y -= minotaurs[ i ].getSpeed ();
            }
        }
    }

    private void skeletonMoveToPlayer() {
        for ( int i = 0; i < SKELETON_COUNT; i++ ) {
            //Справа от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().x -= skeletons[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().x -= skeletons[ i ].getSpeed ();
                skeletons[ i ].getPositionOfEnemies ().y += skeletons[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().y += skeletons[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().x += skeletons[ i ].getSpeed ();
                skeletons[ i ].getPositionOfEnemies ().y += skeletons[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().x += skeletons[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().x += skeletons[ i ].getSpeed ();
                skeletons[ i ].getPositionOfEnemies ().y -= skeletons[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().y -= skeletons[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( skeletons[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && skeletons[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                skeletons[ i ].getPositionOfEnemies ().x -= skeletons[ i ].getSpeed ();
                skeletons[ i ].getPositionOfEnemies ().y -= skeletons[ i ].getSpeed ();
            }
        }
    }

    private void ghostMoveToPlayer() {
        for ( int i = 0; i < GHOST_COUNT; i++ ) {
            //Справа от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().x -= ghosts[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().x -= ghosts[ i ].getSpeed ();
                ghosts[ i ].getPositionOfEnemies ().y += ghosts[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().y += ghosts[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().x += ghosts[ i ].getSpeed ();
                ghosts[ i ].getPositionOfEnemies ().y += ghosts[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().x += ghosts[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().x += ghosts[ i ].getSpeed ();
                ghosts[ i ].getPositionOfEnemies ().y -= ghosts[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().y -= ghosts[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( ghosts[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && ghosts[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                ghosts[ i ].getPositionOfEnemies ().x -= ghosts[ i ].getSpeed ();
                ghosts[ i ].getPositionOfEnemies ().y -= ghosts[ i ].getSpeed ();
            }
        }
    }

    private void darkLordMoveToPlayer() {
        for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
            //Справа от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().x -= darkLords[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().x -= darkLords[ i ].getSpeed ();
                darkLords[ i ].getPositionOfEnemies ().y += darkLords[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().y += darkLords[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().x += darkLords[ i ].getSpeed ();
                darkLords[ i ].getPositionOfEnemies ().y += darkLords[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().x += darkLords[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().x += darkLords[ i ].getSpeed ();
                darkLords[ i ].getPositionOfEnemies ().y -= darkLords[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().y -= darkLords[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( darkLords[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && darkLords[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                darkLords[ i ].getPositionOfEnemies ().x -= darkLords[ i ].getSpeed ();
                darkLords[ i ].getPositionOfEnemies ().y -= darkLords[ i ].getSpeed ();
            }
        }
    }

    private void deathMoveToPlayer() {
        for ( int i = 0; i < DEATH_COUNT; i++ ) {
            //Справа от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().x -= deaths[ i ].getSpeed ();
            }
            //Справа внизу от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().x -= deaths[ i ].getSpeed ();
                deaths[ i ].getPositionOfEnemies ().y += deaths[ i ].getSpeed ();
            }
            //Внизу от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().y += deaths[ i ].getSpeed ();

            }
            //Слева внизу от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y < player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().x += deaths[ i ].getSpeed ();
                deaths[ i ].getPositionOfEnemies ().y += deaths[ i ].getSpeed ();
            }
            //Слева от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y == player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().x += deaths[ i ].getSpeed ();
            }
            //Слева вверху от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x < player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().x += deaths[ i ].getSpeed ();
                deaths[ i ].getPositionOfEnemies ().y -= deaths[ i ].getSpeed ();
            }
            //Вверху от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x == player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().y -= deaths[ i ].getSpeed ();
            }
            //Справа вверху от Героя
            if ( deaths[ i ].getPositionOfEnemies ().x > player.getPositionOfPlayer ().x && deaths[ i ].getPositionOfEnemies ().y > player.getPositionOfPlayer ().y ) {
                deaths[ i ].getPositionOfEnemies ().x -= deaths[ i ].getSpeed ();
                deaths[ i ].getPositionOfEnemies ().y -= deaths[ i ].getSpeed ();
            }
        }
    }

    private void playerMove(){
        //System.out.println ("knob x: " + playerTouchpad.getKnobX ());

        if(activateTouchpad) {
            if ( playerTouchpad.getKnobX () > 140 ) {
                player.right ();
            }
            if ( playerTouchpad.getKnobX () < 60 ) {
                player.left ();
            }
            if ( playerTouchpad.getKnobY () > 140 ) {
                player.up ();
            }
            if ( playerTouchpad.getKnobY () < 60 ) {
                player.down ();
            }
        }
        if (Gdx.input.isKeyPressed (Input.Keys.S)){
            player.down ();
        }
        if (Gdx.input.isKeyPressed (Input.Keys.A)){
            player.left ();
        }
        if (Gdx.input.isKeyPressed (Input.Keys.D)){
            player.right ();
        }
        if (Gdx.input.isKeyPressed (Input.Keys.W)){
            player.up ();
        }
    }

    /**Для стрельбы Огненными шарами*/
    private void fireFireBalls(){

        if(activateTouchpad) {
            if ( fireTouchpad.getKnobX () > 140 ) {
                fireCounterRight++;
                if ( fireCounterRight > fireRate ) {
                    fireCounterRight = 0;
                    fireballActivate ( 0, 0, 10 );
                }
            }
            if ( fireTouchpad.getKnobX () < 60 ) {
                fireCounterLeft++;
                if ( fireCounterLeft > fireRate ) {
                    fireCounterLeft = 0;
                    fireballActivate ( 0, 0, 10 );
                }
            }
            if ( fireTouchpad.getKnobY () > 140 ) {
                fireCounterUp++;
                if ( fireCounterUp > fireRate ) {
                    fireCounterUp = 0;
                    fireballActivate ( 0, 0, 10 );
                }
            }
            if ( fireTouchpad.getKnobY () < 60 ) {
                fireCounterDown++;
                if ( fireCounterDown > fireRate ) {
                    fireCounterDown = 0;
                    fireballActivate ( 0, 0, 10 );
                }
            }
        }
        if( Gdx.input.isKeyPressed ( Input.Keys.RIGHT )){
            fireCounterRight++;
            if(fireCounterRight > fireRate){
                fireCounterRight = 0;
                fireballActivate ( 0, 0, 10 );
            }
        }else {
            if ( Gdx.input.isKeyPressed ( Input.Keys.LEFT ) ) {
                fireCounterLeft++;
                if(fireCounterLeft > fireRate){
                    fireCounterLeft = 0;
                    fireballActivate ( 0, 0, 10 );
                }
            }
        }
        if( Gdx.input.isKeyPressed ( Input.Keys.UP )){
            fireCounterUp++;
            if(fireCounterUp > fireRate){
                fireCounterUp = 0;
                fireballActivate ( 0, 0, 10 );
            }
        }else {
            if ( Gdx.input.isKeyPressed ( Input.Keys.DOWN ) ) {
                fireCounterDown++;
                if(fireCounterDown > fireRate){
                    fireCounterDown = 0;
                    fireballActivate ( 0, 0, 10 );
                }
            }
        }
    }

    private void fireballActivate(float dx, float dy, float speed){

        for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
            if(!fireBalls[i].isActive ()){
                fireBalls[i].getFireSound ().play ();
                fireBalls[i].setup ( player.getPositionOfPlayer ().x + dx, player.getPositionOfPlayer ().y + dy, speed );
                break;
            }
        }
    }

    private void fireballupdate(){

        if(activateTouchpad) {
            if ( fireTouchpad.getKnobX () > 140 ) {
                for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                    fireBalls[ i ].fireRight ();
                }
            }
            if ( fireTouchpad.getKnobX () < 60 ) {
                for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                    fireBalls[ i ].fireLeft ();
                }
            }
            if ( fireTouchpad.getKnobY () > 140 ) {
                for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                    fireBalls[ i ].fireUp ();
                }
            }
            if ( fireTouchpad.getKnobY () < 60 ) {
                for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                    fireBalls[ i ].fireDown ();
                }
            }
        }
        if( Gdx.input.isKeyPressed ( Input.Keys.RIGHT )){
            //System.out.println ("RIGHT");
            for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                fireBalls[i].fireRight ();
            }
        }else{
            if(Gdx.input.isKeyPressed ( Input.Keys.LEFT )){
                for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                    fireBalls[i].fireLeft ();
                }
            }
        }
        if( Gdx.input.isKeyPressed ( Input.Keys.UP )){
            for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                fireBalls[i].fireUp ();
            }
        }else{
            if(Gdx.input.isKeyPressed ( Input.Keys.DOWN )){
                for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                    fireBalls[i].fireDown ();
                }
            }
        }
    }
/** Конец стрелбы огнеными шарами */

    /**Коллизия врагов с персонажем*/
    private void playerCollision() {

        if ( !safe ) {
            for ( int i = 0; i < SLIME_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( slimes[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( slimes[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < BAT_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( bats[ i ].getRectOfEnemies () ) ) {
                    //System.out.println ("Ай");
                    player.getTakeDamage ().play ();
                    player.getDamage ( bats[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < ORC_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( orcs[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( orcs[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < IMP_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( imps[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( imps[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( minotaurs[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( minotaurs[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < SKELETON_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( skeletons[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( skeletons[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < GHOST_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( ghosts[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( ghosts[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( darkLords[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( darkLords[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }

            for ( int i = 0; i < DEATH_COUNT; i++ ) {
                if ( player.getRectOfPlayer ().overlaps ( deaths[ i ].getRectOfEnemies () ) ) {
                    player.getTakeDamage ().play ();
                    player.getDamage ( deaths[ i ].getDamage () );
                    player.teleport ();
                    break;
                }
            }
        }
    }

    /**Коллизия врагов с огнеными шарами*/
    private void fireballsCollision() {

        if ( !safe ) {
            if ( levelCount == 1 ) {
                for ( int i = 0; i < SLIME_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( slimes[ i ].getRectOfEnemies () ) ) {
                            slimes[ i ].getDamage ( player.getDamage () );
                            if ( slimes[ i ].isRecreate () ) {
                                enemiesDiedCount++;
                                scoreCount += slimes[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 2 ) {
                for ( int i = 0; i < BAT_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( bats[ i ].getRectOfEnemies () ) ) {
                            bats[ i ].getDamage ( player.getDamage () );
                            if ( bats[ i ].isRecreate () ) {
                                bats[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += bats[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 3 ) {
                for ( int i = 0; i < ORC_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( orcs[ i ].getRectOfEnemies () ) ) {
                            orcs[ i ].getDamage ( player.getDamage () );
                            if ( orcs[ i ].isRecreate () ) {
                                orcs[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += orcs[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 4 ) {
                for ( int i = 0; i < IMP_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( imps[ i ].getRectOfEnemies () ) ) {
                            imps[ i ].getDamage ( player.getDamage () );
                            if ( imps[ i ].isRecreate () ) {
                                imps[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += imps[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 5 ) {
                for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( minotaurs[ i ].getRectOfEnemies () ) ) {
                            minotaurs[ i ].getDamage ( player.getDamage () );
                            if ( minotaurs[ i ].isRecreate () ) {
                                minotaurs[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += minotaurs[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 6 ) {
                for ( int i = 0; i < SKELETON_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( skeletons[ i ].getRectOfEnemies () ) ) {
                            skeletons[ i ].getDamage ( player.getDamage () );
                            if ( skeletons[ i ].isRecreate () ) {
                                skeletons[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += skeletons[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 7 ) {
                for ( int i = 0; i < GHOST_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( ghosts[ i ].getRectOfEnemies () ) ) {
                            ghosts[ i ].getDamage ( player.getDamage () );
                            if ( ghosts[ i ].isRecreate () ) {
                                ghosts[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += ghosts[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 8 ) {
                for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( darkLords[ i ].getRectOfEnemies () ) ) {
                            darkLords[ i ].getDamage ( player.getDamage () );
                            if ( darkLords[ i ].isRecreate () ) {
                                darkLords[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += darkLords[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }

            if ( levelCount == 9 ) {
                for ( int i = 0; i < DEATH_COUNT; i++ ) {
                    for ( int j = 0; j < FIREBALL_COUNTS; j++ ) {
                        if ( fireBalls[ j ].getRectangleOfFireball ().overlaps ( deaths[ i ].getRectOfEnemies () ) ) {
                            deaths[ i ].getDamage ( player.getDamage () );
                            if ( deaths[ i ].isRecreate () ) {
                                deaths[ i ].getDead ().play ();
                                enemiesDiedCount++;
                                scoreCount += deaths[ i ].getScore ();
                            }
                            fireBalls[ j ].destroy ();
                            break;
                        }
                    }
                }
            }
        }
    }
    private void fireballsNoAtak(){
        for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
            if(fireBalls[i].isActive ()){
                fireballstart[i] += 1;
                if(fireballstart[i] > 150){/**100*/
                    fireBalls[i].destroy ();
                    fireballstart[i] = 0;
                }
            }
        }
    }

    /**Переключение между уровнями*/
    private void levels(){

        if(enemiesDiedCount == LEVEL2 && safeCount == 0){
            safeCount = 1;
        }
        if(safeCount == 1){
            for ( int i = 0; i < SLIME_COUNT; i++ ) {
                slimes[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96) {
                player.setPositionOfPlayer ( midle );
                safeCount = 2;
                safe = false;
                levelCount = 2;
            }
        }

        if(enemiesDiedCount == LEVEL3 && safeCount == 2){
            safeCount = 3;
        }
        if(safeCount == 3){
            for ( int i = 0; i < BAT_COUNT; i++ ) {
                bats[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 4;
                safe = false;
                levelCount = 3;
            }
        }

        if(enemiesDiedCount == LEVEL4 && safeCount == 4){
            safeCount = 5;
        }
        if(safeCount == 5){
            for ( int i = 0; i < ORC_COUNT; i++ ) {
                orcs[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 6;
                safe = false;
                levelCount = 4;
            }
        }

        if(enemiesDiedCount == LEVEL5 && safeCount == 6){
            safeCount = 7;
        }
        if(safeCount == 7){
            for ( int i = 0; i < IMP_COUNT; i++ ) {
                imps[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 8;
                safe = false;
                levelCount = 5;
            }
        }

        if(enemiesDiedCount == LEVEL6 && safeCount == 8){
            safeCount = 9;
        }
        if(safeCount == 9){
            for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
                minotaurs[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 10;
                safe = false;
                levelCount = 6;
            }
        }

        if(enemiesDiedCount == LEVEL7 && safeCount == 10){
            safeCount = 11;
        }
        if(safeCount == 11){
            for ( int i = 0; i < SKELETON_COUNT; i++ ) {
                skeletons[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 12;
                safe = false;
                levelCount = 7;
            }
        }

        if(enemiesDiedCount == LEVEL8 && safeCount == 12){
            safeCount = 13;
        }
        if(safeCount == 13){
            for ( int i = 0; i < GHOST_COUNT; i++ ) {
                ghosts[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 14;
                safe = false;
                levelCount = 8;
            }
        }

        if(enemiesDiedCount == LEVEL9 && safeCount == 14){
            safeCount = 15;
        }
        if(safeCount == 15){
            for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
                darkLords[i].setPositionOfEnemies ( dispos );
            }
            player.setHp ( 100 );
            safe = true;
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                player.setPositionOfPlayer ( midle );
                safeCount = 16;
                safe = false;
                levelCount = 9;
            }
        }

        if(enemiesDiedCount == FINISH && safeCount == 16){
            safeCount = 17;
        }
        if(enemiesDiedCount == FINISH && safeCount == 17){
            for ( int i = 0; i < DEATH_COUNT; i++ ) {
                deaths[i].setPositionOfEnemies ( dispos );
            }
            levelCount = 10;
            player.setHp ( 100 );
            safe = true;
            System.out.println ("You win");
            if(player.getPositionOfPlayer ().x > Siege.WIDTH/2 - 48 && player.getPositionOfPlayer ().x < (Siege.WIDTH/2 ) &&
                    player.getPositionOfPlayer ().y > Siege.HEIGHT - 96){
                Gdx.app.exit ();
            }
        }
    }

    private void appout(){
        if(player.getHp () <= 0){
            gameOver = true;
            System.out.println ("You Dead");
            if(Gdx.input.isTouched ()) {
                Gdx.app.exit ();
            }
        }
    }

    public void render(SpriteBatch batch){

        batch.setProjectionMatrix ( camera.combined );
        camera.update ();

        if(stageActive) {
            if(safeCount == 1 || safeCount == 3 || safeCount == 5 || safeCount == 7 || safeCount == 9 || safeCount == 11 || safeCount == 13 || safeCount == 15){
                batch.draw ( texSafeZone, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 1 && safeCount == 0) {
                batch.draw ( texSlimeDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 2 && safeCount == 2) {
                batch.draw ( texBatDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 3 && safeCount == 4) {
                batch.draw ( texOrcDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 4 && safeCount == 6) {
                batch.draw ( texImpDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 5 && safeCount == 8) {
                batch.draw ( texMinotaurDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 6 && safeCount == 10) {
                batch.draw ( texSkeletonDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 7 && safeCount == 12) {
                batch.draw ( texGhostDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 8 && safeCount == 14) {
                batch.draw ( texDarkLordDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 9 && safeCount == 16) {
                batch.draw ( texDeathDungeon, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }
            if ( levelCount == 10 && safeCount == 17) {
                batch.draw ( texFinish, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }

            renderPlayer (batch);

            for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
                if ( fireBalls[ i ].isActive () ) {
                    fireBalls[ i ].fireBallAnimation ( batch );
                }
            }

            if ( levelCount == 1 && !safe) {
                renderSlime ( batch );
            }
            if ( levelCount == 2 && !safe) {
                renderBat ( batch );
            }
            if ( levelCount == 3 && !safe) {
                renderOrc ( batch );
            }
            if ( levelCount == 4 && !safe) {
                renderImp ( batch );
            }
            if ( levelCount == 5 && !safe) {
                renderMinotaur ( batch );
            }
            if ( levelCount == 6 && !safe) {
                renderSkeleton ( batch );
            }
            if ( levelCount == 7 && !safe) {
                renderGhost ( batch );
            }
            if ( levelCount == 8 && !safe) {
                renderDarkLord ( batch );
            }
            if ( levelCount == 9 && !safe) {
                renderDeath ( batch );
            }


            batch.draw (texHud, Siege.WIDTH/2 - texHud.getWidth ()/2, 0);
            playerHP.draw ( batch, "Hit Points: " + player.getHp (), Siege.WIDTH/2, 144 );
            score.draw ( batch, "Score: " + scoreCount, Siege.WIDTH/2, 124 );
            level.draw ( batch, "Level: " + levelCount, Siege.WIDTH/2, 104 );
            enemiesDied.draw ( batch, "Enemies died: " + enemiesDiedCount, Siege.WIDTH/2, 84 );

            if(gameOver){
                batch.draw ( texGameOver, 0, 0, Siege.WIDTH, Siege.HEIGHT );
            }

            /**отрисовую ещё раз HUD, так как без этого не отображается enemesDied.draw*/
            //playerHP.draw ( batch, "asdadsad",0,0 );
            //stageScreenStage.draw ();
        }
    }

    public void renderStage(){
        if(activateTouchpad) {
            stageScreenStage.draw ();
        }
    }

    public void update(){
        if(stageActive) {

            if(!gameOver) {
                if ( Gdx.input.justTouched () ) {
                    System.out.println ( "lvl " + levelCount );
                    System.out.println ( "Width " + Gdx.graphics.getWidth () );
                }

                //tests ();

                levels ();

                player.update ();

                handleInput ();

                fireballupdate ();

/**Логика движения врагов*/
                if ( levelCount == 1 && ! safe ) {
                    slimeMoveToPlayer ();
                }
                for ( int i = 0; i < SLIME_COUNT; i++ ) {
                    slimes[ i ].update ();
                }

                if ( levelCount == 2 && ! safe ) {
                    batMoveToPlayer ();
                    for ( int i = 0; i < SLIME_COUNT; i++ ) {
                        slimes[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < BAT_COUNT; i++ ) {
                    bats[ i ].update ();
                }

                if ( levelCount == 3 && ! safe ) {
                    orcMoveToPlayer ();
                    for ( int i = 0; i < BAT_COUNT; i++ ) {
                        bats[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < ORC_COUNT; i++ ) {
                    orcs[ i ].update ();
                }

                if ( levelCount == 4 && ! safe ) {
                    impMoveToPlayer ();
                    for ( int i = 0; i < ORC_COUNT; i++ ) {
                        orcs[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < IMP_COUNT; i++ ) {
                    imps[ i ].update ();
                }

                if ( levelCount == 5 && ! safe ) {
                    minotaurMoveToPlayer ();
                    for ( int i = 0; i < IMP_COUNT; i++ ) {
                        imps[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
                    minotaurs[ i ].update ();
                }

                if ( levelCount == 6 && ! safe ) {
                    skeletonMoveToPlayer ();
                    for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
                        minotaurs[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < SKELETON_COUNT; i++ ) {
                    skeletons[ i ].update ();
                }

                if ( levelCount == 7 && ! safe ) {
                    ghostMoveToPlayer ();
                    for ( int i = 0; i < SKELETON_COUNT; i++ ) {
                        skeletons[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < GHOST_COUNT; i++ ) {
                    ghosts[ i ].update ();
                }

                if ( levelCount == 8 && ! safe ) {
                    darkLordMoveToPlayer ();
                    for ( int i = 0; i < GHOST_COUNT; i++ ) {
                        ghosts[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
                    darkLords[ i ].update ();
                }

                if ( levelCount == 9 && ! safe ) {
                    deathMoveToPlayer ();
                    for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
                        darkLords[ i ].setPositionOfEnemies ( dispos );
                    }
                }
                for ( int i = 0; i < DEATH_COUNT; i++ ) {
                    deaths[ i ].update ();
                }

                /**Проверка коллизиq*/
                playerCollision ();
                fireballsCollision ();

                /**"Время" нахождения огненых шаров на экране*/
                fireballsNoAtak ();
            }
            appout ();
        }
    }

    public void dispose(){
        //batch.dispose ();
        battleMusic.dispose ();
        bossBattleMusic.dispose ();
        safeZoneMusic.dispose ();
        finishMusic.dispose ();
        texHud.dispose ();
        texSlimeDungeon.dispose ();
        texBatDungeon.dispose ();
        texOrcDungeon.dispose ();
        texImpDungeon.dispose ();
        texMinotaurDungeon.dispose ();
        texSkeletonDungeon.dispose ();
        texGhostDungeon.dispose ();
        texDarkLordDungeon.dispose ();
        texDeathDungeon.dispose ();
        texSafeZone.dispose ();
        for ( int i = 0; i < FIREBALL_COUNTS; i++ ) {
            fireBalls[i].dispose ();
        }
        for ( int i = 0; i < SLIME_COUNT; i++ ) {
            slimes[i].dispose ();
        }
        for ( int i = 0; i < BAT_COUNT; i++ ) {
            bats[i].dispose ();
        }
        for ( int i = 0; i < ORC_COUNT; i++ ) {
            orcs[i].dispose ();
        }
        for ( int i = 0; i < IMP_COUNT; i++ ) {
            imps[i].dispose ();
        }
        for ( int i = 0; i < MINOTAUR_COUNT; i++ ) {
            minotaurs[i].dispose ();
        }
        for ( int i = 0; i < SKELETON_COUNT; i++ ) {
            skeletons[i].dispose ();
        }
        for ( int i = 0; i < GHOST_COUNT; i++ ) {
            ghosts[i].dispose ();
        }
        for ( int i = 0; i < DARKLORD_COUNT; i++ ) {
            darkLords[i].dispose ();
        }
        for ( int i = 0; i < DEATH_COUNT; i++ ) {
            deaths[i].dispose ();
        }
        playerHP.dispose ();
        score.dispose ();
        level.dispose ();
        enemiesDied.dispose ();
    }

}
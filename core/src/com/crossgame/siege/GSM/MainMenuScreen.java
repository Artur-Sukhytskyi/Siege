package com.crossgame.siege.GSM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.crossgame.siege.GameCamera;
import com.crossgame.siege.Siege;
import com.crossgame.siege.StageScreen;

/**
 * Created by Fenrir on 22.01.2017.
 */

public class MainMenuScreen extends GameCamera{

/**Главное меню*/
    private boolean mainActive;//Для управления активностью главного меню

    public boolean isMainActive () {
        return mainActive;
    }

    public void setMainActive ( boolean mainActive ) {
        this.mainActive = mainActive;
    }

    private Music themeMusic;//Фоновая музыка главного меню

    public Music getThemeMusic () {
        return themeMusic;
    }

    private Texture texMenuBackground;//Для Текстуры заднего фона

    private Vector2 posNewGameButton;
    private Vector2 posJoysicActive;
    private Vector2 posHelpButton;
    private Vector2 posExitButton;

    private Stage menuStage;
    public Stage getMenuStage () {
        return menuStage;
    }

    private ImageButton newGameButton;
    private ImageButton joysticAvtiveButton;
    private ImageButton helpButton;
    private ImageButton exitButton;
    private ImageButton goToMenu;
    private ImageButton on;
    private ImageButton off;

    private TextureRegion regNewGame;
    private TextureRegion regJoysticActive;
    private TextureRegion regHelp;
    private TextureRegion regExit;
    private TextureRegion regOn;
    private TextureRegion regOff;

    private TextureRegionDrawable drNewGame;
    private TextureRegionDrawable drJoysticActive;
    private TextureRegionDrawable drHelp;
    private TextureRegionDrawable drExit;
    private TextureRegionDrawable drOn;
    private TextureRegionDrawable drOff;

    //Для текстур кнопок
    private static Texture texNewGameButton;
    private static Texture texJoysticActive;
    private static Texture texHelpButton;
    private static Texture texExitButton;
    private static Texture On;
    private static Texture Off;

    /**Включение/выключение джойстика*/
    private boolean joysticActive;
    public boolean isJoysticActive(){
        return joysticActive;
    }

    private BitmapFont joysticOn;
    private BitmapFont joysticOff;

    /**Помощь*/
    private boolean helpActive;

    public boolean isHelpActive () {
        return helpActive;
    }

    public void setHelpActive ( boolean helpActive ) {
        this.helpActive = helpActive;
    }

    private BitmapFont help;

    public MainMenuScreen() {

        /**Меню*/
        mainActive = true;
        helpActive = false;

        themeMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "Audio/bgm/Theme1.ogg" ) );
        themeMusic.setLooping ( true );

        camera.setToOrtho ( false, Siege.WIDTH, Siege.HEIGHT );
        //camera.setToOrtho ( false, Gdx.graphics.getWidth (), Gdx.graphics.getHeight () );

        //Загружаем задний фон
        texMenuBackground = new Texture("Backgrounds/Menu.png");

        menuStage = new Stage ( new ScreenViewport () );


        //Загружаем текстуры кнопок
        texNewGameButton = new Texture(Gdx.files.internal ( "ActionButtons/NewGame.png"));
        texJoysticActive = new Texture(Gdx.files.internal ( "ActionButtons/Joystics.png"));
        texHelpButton = new Texture (Gdx.files.internal ( "ActionButtons/Help.png" ));
        texExitButton = new Texture(Gdx.files.internal ( "ActionButtons/Exit.png"));

        On = new Texture ( Gdx.files.internal ( "ActionButtons/On.png" ) );
        Off = new Texture ( Gdx.files.internal ( "ActionButtons/Off.png" ) );

        regNewGame = new TextureRegion ( texNewGameButton );
        regJoysticActive = new TextureRegion ( texJoysticActive );
        regHelp = new TextureRegion ( texHelpButton );
        regExit = new TextureRegion ( texExitButton );
        regOn = new TextureRegion ( On );
        regOff = new TextureRegion ( Off );

        drNewGame = new TextureRegionDrawable ( regNewGame );
        drJoysticActive = new TextureRegionDrawable ( regJoysticActive );
        drHelp = new TextureRegionDrawable ( regHelp );
        drExit = new TextureRegionDrawable ( regExit );
        drOn = new TextureRegionDrawable ( regOn );
        drOff = new TextureRegionDrawable ( regOff );

        posNewGameButton = new Vector2 ( Gdx.graphics.getWidth ()/2 - (texNewGameButton.getWidth ()/2), 350 );
        posJoysicActive = new Vector2 ( Gdx.graphics.getWidth ()/2 - (texJoysticActive.getWidth ()/2), 250 );
        posHelpButton = new Vector2 ( Gdx.graphics.getWidth ()/2 - (texHelpButton.getWidth ()/2), 150 );
        posExitButton = new Vector2 ( Gdx.graphics.getWidth ()/2 - (texExitButton.getWidth ()/2), 50 );

        newGameButton = new ImageButton ( drNewGame );
        joysticAvtiveButton = new ImageButton ( drJoysticActive );
        helpButton = new ImageButton ( drHelp );
        exitButton = new ImageButton ( drExit );
        on = new ImageButton ( drOn );
        off = new ImageButton ( drOff );

        joysticActive = false;

        newGameButton.setPosition ( posNewGameButton.x, posNewGameButton.y );
        joysticAvtiveButton.setPosition ( posJoysicActive.x, posJoysicActive.y );
        helpButton.setPosition ( posHelpButton.x, posHelpButton.y );
        exitButton.setPosition ( posExitButton.x, posExitButton.y );
        on.setPosition ( posJoysicActive.x + texJoysticActive.getWidth (), 245 );
        off.setPosition ( posJoysicActive.x + texJoysticActive.getWidth (), 245 );

        if(mainActive) {
            menuStage.addActor ( newGameButton );
            menuStage.addActor ( joysticAvtiveButton );
            menuStage.addActor ( helpButton );
            menuStage.addActor ( exitButton );
            /**
            menuStage.addActor ( on );
            menuStage.addActor ( off );
             */
        }
        handleInput ();
    }

    //Управление в Главном меню

    private void handleInputNewGame(){
        newGameButton.addListener ( new ClickListener (  ){
            public void clicked(InputEvent event, float x, float y){
                if(mainActive == true && !helpActive) {
                    mainActive = false;
                }
            }
        } );
    }

    private void handleInputJoystic(){
        joysticAvtiveButton.addListener ( new ClickListener (  ){
            public void clicked(InputEvent event, float x, float y){
                if(mainActive == true && !helpActive) {
                    if(!joysticActive) {
                        joysticActive = true;
                    }else if(joysticActive && !helpActive){
                        joysticActive = false;
                    }
                }
            }
        } );
    }

    private void handleInputHelp(){
        helpButton.addListener ( new ClickListener (  ){
            public void clicked(InputEvent event, float x, float y){
                if(mainActive && !helpActive) {
                    helpActive = true;
                }
            }
        } );
    }

    private void handleInputExit(){
        exitButton.addListener ( new ClickListener (  ){
            public void clicked(InputEvent event, float x, float y){
                if(mainActive == true && !helpActive) {
                    Gdx.app.exit ();
                }
            }
        } );
    }

    public void handleInput (){

        if(mainActive == true) {

            handleInputNewGame ();
            handleInputJoystic ();
            handleInputHelp ();
            handleInputExit ();
        }
    }

    public void joyactive(SpriteBatch batch){
        if(joysticActive){
            //batch.draw ( On, posJoysicActive.x + texJoysticActive.getWidth (), posJoysicActive.y -5 );
            menuStage.addActor ( on );
        }else if(!joysticActive){
            //batch.draw ( Off, posJoysicActive.x + texJoysticActive.getWidth (), posJoysicActive.y -5 );
            menuStage.addActor ( off );
        }
    }
        //else System.out.println("main is false");

    public void render (SpriteBatch batch) {

        if(mainActive == true) {
            camera.update ();
            batch.draw ( texMenuBackground, 0, 0, Siege.WIDTH, Siege.HEIGHT );

            batch.setProjectionMatrix ( camera.combined );

            joyactive ( batch );
            //menuStage.draw ();

        }

    }

    public void stage(){
        menuStage.draw ();
    }

    public void update() {
        if(Gdx.input.justTouched ()){
            //System.out.println ("i = " + );
        }
        //handleInput ();
    }

    public void dispose() {
        themeMusic.dispose ();
        texMenuBackground.dispose ();
        texNewGameButton.dispose ();
        texJoysticActive.dispose ();
        texHelpButton.dispose ();
        texExitButton.dispose ();
        On.dispose ();
        Off.dispose ();
    }
}

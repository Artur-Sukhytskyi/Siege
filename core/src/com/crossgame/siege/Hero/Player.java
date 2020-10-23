package com.crossgame.siege.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.crossgame.siege.Siege;


/**
 * Created by Fenrir on 22.01.2017.
 */

public class Player {

    private static final int FRAME_COLS = 3;//Ширина атласа
    private static final int FRAME_ROWS = 1;////Длина атласа

    //Для анимаций перемещения
    private Animation playerWalkAnimationDown;//
    private Animation playerWalkAnimationLeft;//
    private Animation playerWalkAnimationRight;//
    private Animation playerWalkAnimationUp;//
    private Animation playerTeleportAnimation;//

    //Для атласов
    private Texture playerWalkDown;
    private Texture playerWalkLeft;
    private Texture playerWalkRight;
    private Texture playerWalkUp;
    private Texture playerTeleport;

    //Для бездействия Героя
    private TextureRegion playerStayDown;
    private TextureRegion playerStayLeft;
    private TextureRegion playerStayRight;
    private TextureRegion playerStayUp;

    //Масивы для хранения кадров анимации
    private TextureRegion[] playerWalkFramesDown;
    private TextureRegion[] playerWalkFramesLeft;
    private TextureRegion[] playerWalkFramesRight;
    private TextureRegion[] playerWalkFramesUp;
    private TextureRegion[] playerTeleportFrames;

    //Для выделения кадра из атласа
    private TextureRegion playerCurrentFrameDown;
    private TextureRegion playerCurrentFrameLeft;
    private TextureRegion playerCurrentFrameRight;
    private TextureRegion playerCurrentFrameUp;
    private TextureRegion playerCurrentFrameTeleport;

    //Переменные для анимации
    private float playerStateTimeDown;
    private float playerStateTimeLeft;
    private float playerStateTimeRight;
    private float playerStateTimeUp;
    private float playerStateTimeTeleport;

    private Sound takeDamage;
    public Sound getTakeDamage(){
        return takeDamage;
    }
    private Sound heal;
    public Sound getHeal(){
        return heal;
    }

    private boolean DownisActive;
    private boolean LeftisActive;
    private boolean RightisActive;
    private boolean UpisActive;

    private SpriteBatch batch;

    private Vector2 positionOfPlayer;//Позиция Героя

    private Rectangle rectOfPlayer;//Хитбокс Героя

    //Геттеры
    public Rectangle getRectOfPlayer(){
        return rectOfPlayer;
    }

    public Vector2 getPositionOfPlayer(){
        return positionOfPlayer;
    }

    public int getHp(){
        return hp;
    }

    public int getDamage(){
        return damage;
    }

    //Сеттеры
    public Vector2 setPositionOfPlayer(Vector2 pos){
        this.positionOfPlayer = pos;
        return positionOfPlayer;
    }

    public void setHp ( int hp ) {
        this.hp = hp;
    }

    //Характеристика Героя
    private float speed;//Скорость перемещения Героя
    private int hp;//HitPoints of Player
    private int damage;//Урон Героя
    //private int fireCounterRight;
    //private int fireCounterLeft;
    //private int fireCounterUp;
    //private int fireCounterDown;
    //private int fireRate;

    public Player(){
        //Задаём скорость персонажа
        speed = 4.0f;
        hp = 100;
        damage = 2;
        //fireRate = 10;

        //Задаём начальную позицию Героя
        positionOfPlayer = new Vector2 ( Siege.WIDTH/2,Siege.HEIGHT/2 );

        //Создаём хитбокс Героя
        rectOfPlayer = new Rectangle ( positionOfPlayer.x, positionOfPlayer.y, 48, 48 );

        //Загружаём атласы      PlayerGoDown.png
        playerWalkDown = new Texture ( "sprites.Player/PlayerGoDown.png" );
        playerWalkLeft = new Texture ( "sprites.Player/PlayerGoLeft.png" );
        playerWalkRight = new Texture ( "sprites.Player/PlayerGoRight.png" );
        playerWalkUp = new Texture ( "sprites.Player/PlayerGoUp.png" );
        playerTeleport = new Texture ( "sprites.Player/Teleport.png" );

        takeDamage = Gdx.audio.newSound ( Gdx.files.internal ( "Audio/se/Damage3.ogg" ) );
        heal = Gdx.audio.newSound ( Gdx.files.internal ( "Audio/se/Heal1.ogg" ) );

        //Вырезаем кадры из атласов для бездействия Героя
        playerStayDown = new TextureRegion (  playerWalkDown, 48,0,  playerWalkDown.getWidth ()/FRAME_COLS,  playerWalkDown.getHeight ()/FRAME_ROWS);
        //playerStayLeft = new TextureRegion ( playerWalkLeft, 48,0, playerWalkLeft.getWidth ()/FRAME_COLS, playerWalkLeft.getHeight ()/FRAME_ROWS);
        //playerStayRight = new TextureRegion ( playerWalkRight, 48,0, playerWalkRight.getWidth ()/FRAME_COLS, playerWalkRight.getHeight ()/FRAME_ROWS);
        //playerStayUp = new TextureRegion ( playerWalkUp, 48,0, playerWalkUp.getWidth ()/FRAME_COLS, playerWalkUp.getHeight ()/FRAME_ROWS);

        //playerWalkDown    //Для анимаций перемещения вниз
        TextureRegion[][] playerWD = TextureRegion.split (playerWalkDown, playerWalkDown.getWidth()/FRAME_COLS, playerWalkDown.getHeight()/FRAME_ROWS);
        playerWalkFramesDown = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexDown = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    playerWalkFramesDown[ indexDown++ ] = playerWD[ i ][ j ];
                    //System.out.println (index);
                }
            }
        }
        playerWalkAnimationDown = new Animation ( 1/10f, playerWalkFramesDown );
        batch = new SpriteBatch ();
        playerStateTimeDown = 0f;

        //playerWalkLeft    //Для анимаций перемещения Влево
        TextureRegion[][] playerWL = TextureRegion.split (playerWalkLeft, playerWalkLeft.getWidth()/FRAME_COLS, playerWalkLeft.getHeight()/FRAME_ROWS);
        playerWalkFramesLeft = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexLeft = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    playerWalkFramesLeft[ indexLeft++ ] = playerWL[ i ][ j ];
                    //System.out.println (index);
                }
            }
        }
        playerWalkAnimationLeft = new Animation ( 1/10f, playerWalkFramesLeft );
        batch = new SpriteBatch ();
        playerStateTimeLeft = 0f;

        //playerWalkRight   //Для анимаций перемещения Вправо
        TextureRegion[][] playerWR = TextureRegion.split (playerWalkRight, playerWalkRight.getWidth()/FRAME_COLS, playerWalkRight.getHeight()/FRAME_ROWS);
        playerWalkFramesRight = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexRight = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    playerWalkFramesRight[ indexRight++ ] = playerWR[ i ][ j ];
                    //System.out.println (index);
                }
            }
        }
        playerWalkAnimationRight = new Animation ( 1/10f, playerWalkFramesRight );
        batch = new SpriteBatch ();
        playerStateTimeRight = 0f;

        //playerWalkUp  //Для анимаций перемещения Вверх
        TextureRegion[][] playerWU = TextureRegion.split (playerWalkUp, playerWalkUp.getWidth()/FRAME_COLS, playerWalkUp.getHeight()/FRAME_ROWS);
        playerWalkFramesUp = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexUp = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    playerWalkFramesUp[ indexUp++ ] = playerWU[ i ][ j ];
                    //System.out.println (index);
                }
            }
        }
        playerWalkAnimationUp = new Animation ( 1/10f, playerWalkFramesUp );
        batch = new SpriteBatch ();
        playerStateTimeUp = 0f;

        //playerTeleport
        /*TextureRegion[][] playerTP = TextureRegion.split ( playerTeleport, playerTeleport.getWidth ()/FRAME_COLS, playerTeleport.getHeight ()/FRAME_ROWS );
        playerTeleportFrames = new TextureRegion[FRAME_COLS * FRAME_COLS];
        int indexTeleport = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0){
                    playerTeleportFrames[indexTeleport++] = playerTP[i][j];
                }

            }
        }
        playerTeleportAnimation = new Animation ( 1/10, playerTeleportFrames );
        batch = new SpriteBatch (  );
        playerStateTimeTeleport = 0f;*/
    }

    /*public void teleportAnimation(SpriteBatch batch){
        playerStateTimeTeleport += Gdx.graphics.getDeltaTime ();
        playerCurrentFrameTeleport = ( TextureRegion ) playerTeleportAnimation.getKeyFrame ( playerStateTimeTeleport, false );
        batch.draw ( playerCurrentFrameTeleport, positionOfPlayer.x, positionOfPlayer.y);
    }*/

    public void teleport(){
        positionOfPlayer = new Vector2 ( (float)Math.random () * Siege.WIDTH, (float)Math.random () * Siege.HEIGHT);
    }

    public void getDamage(int dmg){
        hp -= dmg;
    }

    //Для организации управления Героем

    public void down(){
            positionOfPlayer.y -= speed;
            if(positionOfPlayer.y < -48){
                positionOfPlayer.y = Siege.HEIGHT + 48;
            }
    }

    public void left(){
            positionOfPlayer.x -= speed;
            if(positionOfPlayer.x < -48){
                positionOfPlayer.x = Siege.WIDTH +48;
            }
    }

    public void right(){
        positionOfPlayer.x += speed;
        if(positionOfPlayer.x > Siege.WIDTH + 48){
            positionOfPlayer.x = -48;
        }
    }

    public void up(){
            positionOfPlayer.y += speed;
            if(positionOfPlayer.y >Siege.HEIGHT +48){
                positionOfPlayer.y = -48;
            }
    }

    private void Input(){
        //down ();
        //left ();
        //right ();
        //up ();
    }

    public void playerDoNothing(SpriteBatch batch){
        //Для отрисовки бездействия Героя
        batch.draw ( playerStayDown, positionOfPlayer.x, positionOfPlayer.y );
    }

    public void renderPlayerDown(SpriteBatch batch){
        playerStateTimeDown += Gdx.graphics.getDeltaTime ();
        playerCurrentFrameDown = ( TextureRegion ) playerWalkAnimationDown.getKeyFrame ( playerStateTimeDown,true );
        //Input ();
        batch.draw ( playerCurrentFrameDown, positionOfPlayer.x, positionOfPlayer.y);
    }

    public void renderPlayerLeft(SpriteBatch batch){
        playerStateTimeLeft += Gdx.graphics.getDeltaTime ();
        playerCurrentFrameLeft = ( TextureRegion ) playerWalkAnimationLeft.getKeyFrame ( playerStateTimeLeft,true );
        //Input ();
        batch.draw ( playerCurrentFrameLeft, positionOfPlayer.x, positionOfPlayer.y);
    }

    public void renderPlayerRight(SpriteBatch batch){
        playerStateTimeRight += Gdx.graphics.getDeltaTime ();
        playerCurrentFrameRight = ( TextureRegion ) playerWalkAnimationRight.getKeyFrame ( playerStateTimeRight,true );
        //Input ();
        batch.draw ( playerCurrentFrameRight, positionOfPlayer.x, positionOfPlayer.y);
    }

    public void renderPlayerUp(SpriteBatch batch){
        playerStateTimeUp += Gdx.graphics.getDeltaTime ();
        playerCurrentFrameUp = ( TextureRegion ) playerWalkAnimationUp.getKeyFrame ( playerStateTimeUp, true );
        //Input ();
        batch.draw ( playerCurrentFrameUp, positionOfPlayer.x, positionOfPlayer.y );
    }

    public void render(SpriteBatch batch){

        if(!Gdx.input.isKeyPressed ( Input.Keys.S )&&!Gdx.input.isKeyPressed ( Input.Keys.W )&&!Gdx.input.isKeyPressed ( Input.Keys.A )&&!Gdx.input.isKeyPressed ( Input.Keys.D )){
            playerDoNothing (batch);
        }
    }

    public void update() {
        //Input ();
        rectOfPlayer.x = positionOfPlayer.x;
        rectOfPlayer.y = positionOfPlayer.y;
    }

    public void dispose(){
        takeDamage.dispose ();
        heal.dispose ();
    }

}
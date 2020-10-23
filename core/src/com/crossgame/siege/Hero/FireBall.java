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
 * Created by Fenrir on 07.02.2017.
 */

public class FireBall {

    private static final int FRAME_COLS = 3;//Ширина атласа
    private static final int FRAME_ROWS = 1;////Длина атласа

    //Для анимации огненного шара
    private static Animation fireballAnimation;

    //Для атласа
    private static Texture fireball;

    ////Масив для хранения кадров анимации
    private static TextureRegion[] fireballFrames;

    //Для выделения кадра из атласа
    private static TextureRegion fireballCurrentFrame;

    //Переменные для анимации
    private float fireballStateTime;

    private Sound fireSound;
    public Sound getFireSound(){
        return fireSound;
    }

    private SpriteBatch batch;

    private Vector2 positionOfFireball;

    private Rectangle rectangleOfFireball;

    //Геттеры
    public Vector2 getPositionOfFireball(){
        return positionOfFireball;
    }

    public Rectangle getRectangleOfFireball(){
        return rectangleOfFireball;
    }

    public boolean isActive(){
        return active;
    }

    private float speed;
    private boolean active;

    public FireBall(){
        positionOfFireball = new Vector2 ( 0.0f, 0.0f );
        rectangleOfFireball = new Rectangle ( positionOfFireball.x, positionOfFireball.y, 48,48 );
        speed = 0.1f;
        active = false;

        fireball = new Texture ( Gdx.files.internal ( "sprites.Player/FireBall.png" ) );

        fireSound = Gdx.audio.newSound ( Gdx.files.internal ( "Audio/se/Fire1.ogg" ) );

        TextureRegion[][] fireB = TextureRegion.split ( fireball, fireball.getWidth ()/FRAME_COLS,fireball.getHeight ()/FRAME_ROWS );
        fireballFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexFireBall = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0){
                    fireballFrames[indexFireBall++] = fireB[i][j];
                }
            }
        }
        fireballAnimation = new Animation ( 1/10f, fireballFrames );
        batch = new SpriteBatch (  );
        fireballStateTime = 0f;
    }

    public void destroy(){
        active = false;
        rectangleOfFireball.x = - 5000;
        rectangleOfFireball.y = - 5000;
    }

    public void setup(float x, float y, float speed){
        positionOfFireball.x = x;
        positionOfFireball.y = y;
        rectangleOfFireball.x = positionOfFireball.x;
        rectangleOfFireball.y = positionOfFireball.y;
        this.speed = speed;
        active = true;
    }

    public void fireLeft(){
        positionOfFireball.x -= speed;
        rectangleOfFireball.x = positionOfFireball.x;
        if(positionOfFireball.x < - 48){
            destroy ();
        }
    }
    public void fireRight(){
        positionOfFireball.x += speed;
        rectangleOfFireball.x = positionOfFireball.x;
        if(positionOfFireball.x > Siege.WIDTH + 48){
            //System.out.println ("Destroy");
            destroy ();
        }
    }
    public void fireUp(){
        positionOfFireball.y += speed;
        rectangleOfFireball.y = positionOfFireball.y;
        if(positionOfFireball.y > Siege.HEIGHT + 48){
            destroy ();
        }
    }
    public void fireDown(){
        positionOfFireball.y -= speed;
        rectangleOfFireball.y = positionOfFireball.y;
        if(positionOfFireball.y < - 48){
            destroy ();
        }
    }

    public void fireBallAnimation(SpriteBatch batch){
        fireballStateTime += Gdx.graphics.getDeltaTime ();
        fireballCurrentFrame = ( TextureRegion ) fireballAnimation.getKeyFrame ( fireballStateTime, true );
        batch.draw ( fireballCurrentFrame, positionOfFireball.x, positionOfFireball.y );
    }

    public void render(SpriteBatch batch){
        if( Gdx.input.isKeyPressed ( Input.Keys.RIGHT )){
            fireBallAnimation(batch);
        }else{
            if(Gdx.input.isKeyPressed ( Input.Keys.LEFT )){
                fireBallAnimation(batch);
            }
        }
        if( Gdx.input.isKeyPressed ( Input.Keys.UP )){
            fireBallAnimation(batch);
        }else{
            if(Gdx.input.isKeyPressed ( Input.Keys.DOWN )){
                fireBallAnimation(batch);
            }
        }
    }

    public void dispose(){
        fireSound.dispose ();
    }

}
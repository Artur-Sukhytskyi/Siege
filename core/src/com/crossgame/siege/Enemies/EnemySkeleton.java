package com.crossgame.siege.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.crossgame.siege.Siege;

/**
 * Created by Fenrir on 24.02.2017.
 */

public class EnemySkeleton extends Enemies{

    public EnemySkeleton(){
        speed = 0.50f + (float)Math.random () * 0.5f;
        hp = 1;
        damage = 2;
        score = 2;
        recreate = false;

        //Загружаем атлассы
        enemiesWalkDown = new Texture ( Gdx.files.internal ( "sprites.Enemies/Skeleton/SkeletonGoDown.png" ) );
        enemiesWalkLeft = new Texture ( Gdx.files.internal ( "sprites.Enemies/Skeleton/SkeletonGoLeft.png" ) );
        enemiesWalkRight = new Texture ( Gdx.files.internal ( "sprites.Enemies/Skeleton/SkeletonGoRight.png" ) );
        enemiesWalkUp = new Texture ( Gdx.files.internal ( "sprites.Enemies/Skeleton/SkeletonGoUp.png" ) );

        dead = Gdx.audio.newSound ( Gdx.files.internal ( "Audio/se/Monster6.ogg" ) );

        //Создаём хитбокс для Skeleton
        rectOfEnemies = new Rectangle ( positionOfEnemies.x, positionOfEnemies.y, enemiesWalkDown.getWidth ()/3,enemiesWalkDown.getHeight () );

        //skeletonWalkDown   Анимация движения вниз
        TextureRegion[][] skeletonWD = TextureRegion.split (enemiesWalkDown, enemiesWalkDown.getWidth()/FRAME_COLS, enemiesWalkDown.getHeight()/FRAME_ROWS);
        enemiesWalkFramesDown = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexDown = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesDown[ indexDown++ ] = skeletonWD[ i ][ j ];
                }
            }
        }
        enemiesWalkAnimationDown = new Animation ( 1/10f, enemiesWalkFramesDown );
        batch = new SpriteBatch ();
        enemiesStateTimeDown = 0f;

        //skeletonWalkLeft   Анимация движения влево
        TextureRegion[][] skeletonWL = TextureRegion.split (enemiesWalkLeft, enemiesWalkLeft.getWidth()/FRAME_COLS, enemiesWalkLeft.getHeight()/FRAME_ROWS);
        enemiesWalkFramesLeft = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexLeft = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesLeft[ indexLeft++ ] = skeletonWL[ i ][ j ];
                }
            }
        }
        enemiesWalkAnimationLeft = new Animation ( 1/10f, enemiesWalkFramesLeft );
        batch = new SpriteBatch ();
        enemiesStateTimeLeft = 0f;

        //skeletonWalkRight  Анимация движения вправо
        TextureRegion[][] skeletonWR = TextureRegion.split (enemiesWalkRight, enemiesWalkRight.getWidth()/FRAME_COLS, enemiesWalkRight.getHeight()/FRAME_ROWS);
        enemiesWalkFramesRight = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexRight = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesRight[ indexRight++ ] = skeletonWR[ i ][ j ];
                }
            }
        }
        enemiesWalkAnimationRight = new Animation ( 1/10f, enemiesWalkFramesRight );
        batch = new SpriteBatch ();
        enemiesStateTimeRight = 0f;

        //skeletonWalkUp     Анимация движения вверх
        TextureRegion[][] skeletonWU = TextureRegion.split (enemiesWalkUp, enemiesWalkUp.getWidth()/FRAME_COLS, enemiesWalkUp.getHeight()/FRAME_ROWS);
        enemiesWalkFramesUp = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexUp = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesUp[ indexUp++ ] = skeletonWU[ i ][ j ];
                    //System.out.println (index);
                }
            }
        }
        enemiesWalkAnimationUp = new Animation ( 1/10f, enemiesWalkFramesUp );
        batch = new SpriteBatch ();
        enemiesStateTimeUp = 0f;

    }

    @Override
    public void recreateEnemies () {
        recreate = true;
        enemiesPosition = (float) (Math.random () * 16);
        if(enemiesPosition >= 0 && enemiesPosition <= 4){
            //Справа от экрана
            positionOfEnemies = new Vector2 ( Siege.WIDTH + (float)Math.random () * 600, (float)Math.random () * Siege.HEIGHT );
        }else {
            if(enemiesPosition > 4 && enemiesPosition <= 8){
                //Внизу от экрана
                positionOfEnemies = new Vector2 ( (float)Math.random () * Siege.WIDTH, (float)Math.random () * (-600) );
            }else{
                if(enemiesPosition > 8 && enemiesPosition <= 12){
                    //Слева от экрана
                    positionOfEnemies = new Vector2 ( (float)Math.random () * (-600), (float)Math.random () * Siege.HEIGHT );
                }
                else{
                    if(enemiesPosition > 12 && enemiesPosition <= 16){
                        //Вверху от экрана
                        positionOfEnemies = new Vector2 ( (float)Math.random () * Siege.WIDTH, Siege.HEIGHT + (float)Math.random () * 600 );
                    }
                }
            }
        }
    }

    @Override
    public void getDamage(int dmg){
        hp -= dmg;
        if(hp <= 0) recreateEnemies ();
    }

    @Override
    public void renderEnemiesLeft ( SpriteBatch batch ) {
        enemiesStateTimeLeft += Gdx.graphics.getDeltaTime ();
        enemiesCurrentFrameLeft = ( TextureRegion ) enemiesWalkAnimationLeft.getKeyFrame ( enemiesStateTimeLeft,true );
        batch.draw ( enemiesCurrentFrameLeft, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void renderEnemiesUp ( SpriteBatch batch ) {
        enemiesStateTimeUp += Gdx.graphics.getDeltaTime ();
        enemiesCurrentFrameUp = ( TextureRegion ) enemiesWalkAnimationUp.getKeyFrame ( enemiesStateTimeUp,true );
        batch.draw ( enemiesCurrentFrameUp, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void renderEnemiesRight ( SpriteBatch batch ) {
        enemiesStateTimeRight += Gdx.graphics.getDeltaTime ();
        enemiesCurrentFrameRight = ( TextureRegion ) enemiesWalkAnimationRight.getKeyFrame ( enemiesStateTimeRight,true );
        batch.draw ( enemiesCurrentFrameRight, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void renderEnemiesDown ( SpriteBatch batch ) {
        enemiesStateTimeDown += Gdx.graphics.getDeltaTime ();
        enemiesCurrentFrameDown = ( TextureRegion ) enemiesWalkAnimationDown.getKeyFrame ( enemiesStateTimeDown,true );
        batch.draw ( enemiesCurrentFrameDown, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void update(){
        rectOfEnemies.x = positionOfEnemies.x;
        rectOfEnemies.y = positionOfEnemies.y;
    }

    @Override
    public void dispose(){
        dead.dispose ();
    }

}

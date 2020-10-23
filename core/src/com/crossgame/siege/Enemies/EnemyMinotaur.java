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

public class EnemyMinotaur extends Enemies{

    public EnemyMinotaur(){
        speed = 0.45f + (float)Math.random () * 0.6f;
        hp = 5;
        damage = 4;
        score = 5;
        recreate = false;

        //Загружаем атлассы
        enemiesWalkDown = new Texture ( Gdx.files.internal ( "sprites.Enemies/Minotaur/MinotaurGoDown.png" ) );
        enemiesWalkLeft = new Texture ( Gdx.files.internal ( "sprites.Enemies/Minotaur/MinotaurGoLeft.png" ) );
        enemiesWalkRight = new Texture ( Gdx.files.internal ( "sprites.Enemies/Minotaur/MinotaurGoRight.png" ) );
        enemiesWalkUp = new Texture ( Gdx.files.internal ( "sprites.Enemies/Minotaur/MinotaurGoUp.png" ) );

        dead = Gdx.audio.newSound ( Gdx.files.internal ( "Audio/se/Monster4.ogg" ) );

        //Создаём хитбокс для Minotaur
        rectOfEnemies = new Rectangle ( positionOfEnemies.x, positionOfEnemies.y, enemiesWalkDown.getWidth ()/3,enemiesWalkDown.getHeight () );

        //minotaurWalkDown   Анимация движения вниз
        TextureRegion[][] minotaurWD = TextureRegion.split (enemiesWalkDown, enemiesWalkDown.getWidth()/FRAME_COLS, enemiesWalkDown.getHeight()/FRAME_ROWS);
        enemiesWalkFramesDown = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexDown = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesDown[ indexDown++ ] = minotaurWD[ i ][ j ];
                }
            }
        }
        enemiesWalkAnimationDown = new Animation ( 1/10f, enemiesWalkFramesDown );
        batch = new SpriteBatch ();
        enemiesStateTimeDown = 0f;

        //minotaurWalkLeft   Анимация движения влево
        TextureRegion[][] minotaurWL = TextureRegion.split (enemiesWalkLeft, enemiesWalkLeft.getWidth()/FRAME_COLS, enemiesWalkLeft.getHeight()/FRAME_ROWS);
        enemiesWalkFramesLeft = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexLeft = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesLeft[ indexLeft++ ] = minotaurWL[ i ][ j ];
                }
            }
        }
        enemiesWalkAnimationLeft = new Animation ( 1/10f, enemiesWalkFramesLeft );
        batch = new SpriteBatch ();
        enemiesStateTimeLeft = 0f;

        //minotaurWalkRight  Анимация движения вправо
        TextureRegion[][] minotaurWR = TextureRegion.split (enemiesWalkRight, enemiesWalkRight.getWidth()/FRAME_COLS, enemiesWalkRight.getHeight()/FRAME_ROWS);
        enemiesWalkFramesRight = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexRight = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesRight[ indexRight++ ] = minotaurWR[ i ][ j ];
                }
            }
        }
        enemiesWalkAnimationRight = new Animation ( 1/10f, enemiesWalkFramesRight );
        batch = new SpriteBatch ();
        enemiesStateTimeRight = 0f;

        //minotaurWalkUp     Анимация движения вверх
        TextureRegion[][] minotaurWU = TextureRegion.split (enemiesWalkUp, enemiesWalkUp.getWidth()/FRAME_COLS, enemiesWalkUp.getHeight()/FRAME_ROWS);
        enemiesWalkFramesUp = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexUp = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    enemiesWalkFramesUp[ indexUp++ ] = minotaurWU[ i ][ j ];
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
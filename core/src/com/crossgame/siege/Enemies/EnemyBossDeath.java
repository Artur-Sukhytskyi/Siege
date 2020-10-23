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

public class EnemyBossDeath extends Enemies{

    private static Animation deathWalkAnimation;//Для анимаций перемещения

    private static Texture deathWalk;//Для атласов

    private static TextureRegion[] deathWalkFrames;//Массив для хранения кадров

    private static TextureRegion deathCurrentFrame;//Для выделения кадра из атласса

    private float deathStateTime;//Переменные для анимации

    public EnemyBossDeath(){
        speed = 1.80f;
        hp = 100;
        damage = 25;
        score = 999;
        recreate = false;

        //Загружаем атлассы
        deathWalk = new Texture ( Gdx.files.internal ( "sprites.Enemies/Death/Death.png" ) );

        dead = Gdx.audio.newSound ( Gdx.files.internal ( "Audio/se/Monster5.ogg" ) );

        //Создаём хитбокс для Death
        rectOfEnemies = new Rectangle ( positionOfEnemies.x, positionOfEnemies.y, deathWalk.getWidth ()/3,deathWalk.getHeight () );

        //deathWalk   Анимация движения
        TextureRegion[][] death = TextureRegion.split (deathWalk, deathWalk.getWidth()/FRAME_COLS, deathWalk.getHeight()/FRAME_ROWS);
        deathWalkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexDown = 0;
        for ( int i = 0; i < FRAME_ROWS; i++ ) {
            for ( int j = 0; j < FRAME_COLS; j++ ) {
                if(i == 0 ) {
                    deathWalkFrames[ indexDown++ ] = death[ i ][ j ];
                }
            }
        }
        deathWalkAnimation = new Animation ( 1/10f, deathWalkFrames );
        batch = new SpriteBatch ();
        deathStateTime = 0f;
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
        deathStateTime += Gdx.graphics.getDeltaTime ();
        deathCurrentFrame = ( TextureRegion ) deathWalkAnimation.getKeyFrame ( deathStateTime,true );
        batch.draw ( deathCurrentFrame, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void renderEnemiesUp ( SpriteBatch batch ) {
        deathStateTime += Gdx.graphics.getDeltaTime ();
        deathCurrentFrame = ( TextureRegion ) deathWalkAnimation.getKeyFrame ( deathStateTime,true );
        batch.draw ( deathCurrentFrame, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void renderEnemiesRight ( SpriteBatch batch ) {
        deathStateTime += Gdx.graphics.getDeltaTime ();
        deathCurrentFrame = ( TextureRegion ) deathWalkAnimation.getKeyFrame ( deathStateTime,true );
        batch.draw ( deathCurrentFrame, positionOfEnemies.x, positionOfEnemies.y);
    }

    @Override
    public void renderEnemiesDown ( SpriteBatch batch ) {
        deathStateTime += Gdx.graphics.getDeltaTime ();
        deathCurrentFrame = ( TextureRegion ) deathWalkAnimation.getKeyFrame ( deathStateTime,true );
        batch.draw ( deathCurrentFrame, positionOfEnemies.x, positionOfEnemies.y);
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

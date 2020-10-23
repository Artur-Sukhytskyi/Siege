package com.crossgame.siege.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.crossgame.siege.Siege;

/**
 * Created by Fenrir on 20.03.2017.
 */

public abstract class Enemies {
    protected static final int FRAME_COLS = 3;//ширина атласа
    protected static final int FRAME_ROWS = 1;//длина атласа

    protected Animation enemiesWalkAnimationDown;//Для анимаций
    protected Animation enemiesWalkAnimationLeft;//перемещения
    protected Animation enemiesWalkAnimationRight;//Вниз, Влево,
    protected Animation enemiesWalkAnimationUp;//Вправо, Вверх соответственно

    protected static Texture enemiesWalkDown;//Для атласов
    protected static Texture enemiesWalkLeft;//Для атласов
    protected static Texture enemiesWalkRight;//Для атласов
    protected static Texture enemiesWalkUp;//Для атласов

    protected static TextureRegion[] enemiesWalkFramesDown;//Массив для хранения кадров
    protected static TextureRegion[] enemiesWalkFramesLeft;//Массив для хранения кадров
    protected static TextureRegion[] enemiesWalkFramesRight;//Массив для хранения кадров
    protected static TextureRegion[] enemiesWalkFramesUp;//Массив для хранения кадров

    protected static TextureRegion enemiesCurrentFrameDown;//Для выделения кадра из атласса
    protected static TextureRegion enemiesCurrentFrameLeft;//Для выделения кадра из атласса
    protected static TextureRegion enemiesCurrentFrameRight;//Для выделения кадра из атласса
    protected static TextureRegion enemiesCurrentFrameUp;//Для выделения кадра из атласса

    protected float enemiesStateTimeDown;//Переменные
    protected float enemiesStateTimeLeft;//для
    protected float enemiesStateTimeRight;//анимации
    protected float enemiesStateTimeUp;//

    protected Sound dead;

    public Sound getDead () {
        return dead;
    }

    protected SpriteBatch batch;

    protected Vector2 positionOfEnemies;//Позиция Летучей мыши

    protected float enemiesPosition;//Для генерации места положения Летучей мыши

    protected Rectangle rectOfEnemies;//Хитбокс для Летучей мыши

    //Геттеры
    public Rectangle getRectOfEnemies () {
        return rectOfEnemies;
    }//получение хитбокса для других классов

    public Vector2 getPositionOfEnemies () {
        return positionOfEnemies;
    }//получение позиции для других классов

    public float getSpeed () {
        return speed;
    }

    public int getDamage () {
        return damage;
    }

    public int getHp () {
        return hp;
    }

    public int getScore () {
        return score;
    }

    public boolean isRecreate () {
        return recreate;
    }

    //Сэттеры

    public void setPositionOfEnemies ( Vector2 positionOfEnemies ) {
        this.positionOfEnemies = positionOfEnemies;
    }

    //Характеристика Enemy
    protected float speed;//Скорость перемещения Enemy
    protected int hp;//HitPoints of Enemy
    protected int damage;//урон от Enemy
    protected int score;
    protected boolean recreate;


    public Enemies() {
        //Генерируем начальное местоположение Летучей мыши
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

    public abstract void recreateEnemies();
    public abstract void getDamage(int dmg);

    public abstract void renderEnemiesLeft(SpriteBatch batch);
    public abstract void renderEnemiesUp(SpriteBatch batch);
    public abstract void renderEnemiesRight(SpriteBatch batch);
    public abstract void renderEnemiesDown(SpriteBatch batch);

    public abstract void update();
    public abstract void dispose();
}

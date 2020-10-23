package com.crossgame.siege;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Fenrir on 20.02.2017.
 */

public abstract class GameCamera {

    protected OrthographicCamera camera;
    protected Vector3 mouse;

    public GameCamera(){
        camera = new OrthographicCamera (  );
        mouse = new Vector3 (  );
    }

    protected abstract void handleInput();
}

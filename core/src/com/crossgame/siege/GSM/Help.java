package com.crossgame.siege.GSM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.crossgame.siege.Siege;

/**
 * Created by Fenrir on 27.03.2017.
 */

public class Help {

    private boolean helpActive;

    public boolean isHelpActive () {
        return helpActive;
    }

    public void setHelpActive ( boolean helpActive ) {
        this.helpActive = helpActive;
    }

    private boolean exitfromhelp;

    public boolean isExitfromhelp () {
        return exitfromhelp;
    }

    public void setExitfromhelp ( boolean exitfromhelp ) {
        this.exitfromhelp = exitfromhelp;
    }

    private static Texture bckgd;

    private BitmapFont help_1;
    private BitmapFont help_2;
    private BitmapFont help_3;
    private BitmapFont help_4;
    private BitmapFont help_5;
    private BitmapFont help_6;

    public Help(){
        helpActive = false;
        exitfromhelp = false;
        bckgd = new Texture ( Gdx.files.internal ( "Backgrounds/Menu.png" ) );
        help_1 = new BitmapFont (  );
        help_2 = new BitmapFont (  );
        help_3 = new BitmapFont (  );
        help_4 = new BitmapFont (  );
        help_5 = new BitmapFont (  );
        help_6 = new BitmapFont (  );
    }

    public void render( SpriteBatch batch){
        batch.draw ( bckgd, 0, 0, Siege.WIDTH, Siege.HEIGHT );
        help_1.draw ( batch, "WASD - move player", 0, Siege.HEIGHT - 30 );
        help_2.draw ( batch, "arows: left, right, up, down - fire", 0, Siege.HEIGHT - 60 );
        help_3.draw ( batch, "left joystic - move player", 0, Siege.HEIGHT - 90 );
        help_4.draw ( batch, "right joystic - fire", 0, Siege.HEIGHT - 120 );
        help_5.draw ( batch, "if hero died, touch too screen to exit app", 0, Siege.HEIGHT - 150 );
        help_6.draw ( batch, "To go to menu just touch !ME! ^_^", 0, Siege.HEIGHT - 180 );
    }

    public void update(){
        if(Gdx.input.isTouched ()){
            //helpActive = false;
            if(helpActive){
                System.out.println ("true in help");
            }
        }
    }

    public void dispose(){
        bckgd.dispose ();
        help_1.dispose ();
        help_2.dispose ();
        help_3.dispose ();
        help_4.dispose ();
        help_5.dispose ();
        help_6.dispose ();
    }
}

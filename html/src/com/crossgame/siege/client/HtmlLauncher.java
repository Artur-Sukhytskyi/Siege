package com.crossgame.siege.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.crossgame.siege.Siege;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Siege.WIDTH, Siege.HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Siege();
        }
}
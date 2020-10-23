package com.crossgame.siege.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crossgame.siege.Siege;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Siege.TITLE;
		config.width = Siege.WIDTH;
		config.height = Siege.HEIGHT;
		config.resizable = false;
		//config.fullscreen = false;
		//config.fullscreen = true;
		new LwjglApplication(new Siege(), config);
	}
}

package com.supermario.game.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.supermario.game.SuperMario;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = SuperMario.HEIGHT;
        config.width = SuperMario.WIDTH;
        config.title = "СУПЕР ПРЕЗИДЕНТ";
        //config.resizable = false;
		new LwjglApplication(new SuperMario(), config);
	}
}

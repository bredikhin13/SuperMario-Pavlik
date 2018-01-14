package com.supermario.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supermario.game.model.Map;
import com.supermario.game.model.Player;
import com.supermario.game.screens.MainMenuScreen;

public class SuperMario extends Game {


    public static final int WIDTH = 600; //размеры окна - ширина
    public static final int HEIGHT = 420; //размеры окна - высота
    public static int currentLevel = 1;
    public static int playerPoint = 0;
    public static int AllPlayerPoints = 0;

	@Override
	public void create () {
        this.setScreen(new MainMenuScreen(this)); // создание начального экрана
	}

}

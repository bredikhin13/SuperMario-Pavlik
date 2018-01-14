package com.supermario.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.Res.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Анна on 30.11.2014.
 */
public abstract class SuperMarioScreen implements Screen {

    protected Game game;

    public static ArrayList<Result> results = new ArrayList<Result>();

    protected SpriteBatch batch;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

        private static Scanner in;
    public static void getResults() {
        try {
            in = new Scanner(new File("assets/result.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
            ArrayList<String> strings = new ArrayList<String>();
            while (in.hasNextLine()) {
                strings.add(in.nextLine());
            }
            in.close();
            for (int i = 0; i < 10; i++) {
                results.add(new Result(strings.get(i), strings.get(i + 10)));
            }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

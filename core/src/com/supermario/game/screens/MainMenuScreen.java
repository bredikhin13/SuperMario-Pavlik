package com.supermario.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.supermario.game.SuperMario;

import java.awt.*;

/**
 * Created by Анна on 30.11.2014.
 */

//экран начала игры

public class MainMenuScreen extends SuperMarioScreen {

    private Sprite hello;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        hello.draw(batch); //загрузка изображения начала игры
        batch.end();
    }

    @Override
    public void show() {
        super.show();
        hello = new Sprite(new Texture(Gdx.files.internal("assets/hellowindow.png"))) {{
            setX(0);
            setY(0);
        }};

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = SuperMario.HEIGHT - screenY; //инверсия координаты у

                if (new Rectangle(20, 340, 210, 80).contains(screenX, screenY)) { //нажали на начало игры

                    game.setScreen(new GameScreen(game));
                    Gdx.input.setInputProcessor(null);

                }
                if (new Rectangle(245, 0, 355, 120).contains(screenX, screenY)) {  //нажали на таблицу лучших результатов
                    game.setScreen(new StoreScreen(game));
//                    Gdx.input.setInputProcessor(null);
                }

                return true;
            }
        });
    }
}

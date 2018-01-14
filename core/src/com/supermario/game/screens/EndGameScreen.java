package com.supermario.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.SuperMario;

import java.awt.*;

/**
 * Created by Анна on 30.11.2014.
 */

//закончились жизни или уровни пройдены
public class EndGameScreen extends SuperMarioScreen {

    Sprite sprite;

    private BitmapFont font = new BitmapFont(Gdx.files.internal("font/3.fnt"), new Sprite(new Texture("font/3.png")), false);
    private com.badlogic.gdx.scenes.scene2d.ui.Label label;
    public EndGameScreen(Game g) {
        this.game = g;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
//        batch.draw(sprite.getTexture(),0,0,Window.WIDTH,Window.HEIGHT);
        sprite.draw(batch);
        label.draw(batch,10);
//        batch.draw(sprite.getTexture(),0,0);
        batch.end();
    }

    @Override
    public void show() {
        super.show();
        sprite = new Sprite(new Texture(Gdx.files.internal("assets/loose.png"))) {
            {
                setX(0);
                setY(0);
            }
        };

        label = new Label(Integer.toString(SuperMario.AllPlayerPoints), new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.LIGHT_GRAY)) {
            {
                setX(470);
                setY(320);
            }
        };

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = SuperMario.HEIGHT - screenY; //инверсия координаты у
                    if (new Rectangle(208, 420-347, 198, 54).contains(screenX, screenY)) { //нажали на начало игры
                        game.setScreen(new GameScreen(game));
                        Gdx.input.setInputProcessor(null);
                    }
                if (new Rectangle(207, 420-405, 231, 41).contains(screenX, screenY)) { //нажали на начало игры
                    game.setScreen(new MainMenuScreen(game));
//                    Gdx.input.setInputProcessor(null);
                }

                return true;
            }
        });
    }
}

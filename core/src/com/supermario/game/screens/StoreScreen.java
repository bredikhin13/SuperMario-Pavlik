package com.supermario.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.Res.Result;
import com.supermario.game.SuperMario;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by Анна on 04.12.2014.
 */
public class StoreScreen extends SuperMarioScreen {
    private BitmapFont font = new BitmapFont(Gdx.files.internal("font/1.fnt"), new Sprite(new Texture("font/1.png")), false);

    Label label = new Label("name", new Label.LabelStyle(font, Color.LIGHT_GRAY));
    //    ArrayList<Result> results = new ArrayList<Result>();
    ArrayList<Label> labels = new ArrayList<Label>();
    Sprite sprite;

    Scanner in;


    public StoreScreen(Game g) {
    this.game = g;
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
            final int finalI = i;
            labels.add(new Label(strings.get(finalI), new Label.LabelStyle(font, Color.LIGHT_GRAY)) {
                {
                    setX(100);
                    setY(320 - finalI * 30);
                }
            });
            labels.add(new Label(strings.get(finalI + 10), new Label.LabelStyle(font, Color.LIGHT_GRAY)) {
                {
                    setX(300);
                    setY(320 - finalI * 30);

                }
            });
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        sprite.draw(batch);
        for (Label l : labels) {
            l.draw(batch, 10);
        }
        batch.end();
    }

    @Override
    public void show() {
        super.show();
        sprite = new Sprite(new Texture("assets/5.png")) {
            {
                setX(0);
                setY(0);
            }
        };
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = SuperMario.HEIGHT - screenY; //инверсия координаты у
                if (new Rectangle(484, 420-390, 116, 38).contains(screenX, screenY)) {  //нажали на таблицу лучших результатов
                    game.setScreen(new MainMenuScreen(game));
//                    Gdx.input.setInputProcessor(null);
                }
                return true;
            }
        });
    }
}

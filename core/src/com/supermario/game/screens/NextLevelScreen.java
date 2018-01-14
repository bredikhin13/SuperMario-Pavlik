package com.supermario.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.Res.Result;
import com.supermario.game.SuperMario;

import java.awt.*;
import java.io.*;

/**
 * Created by User on 26.12.2014.
 */
public class NextLevelScreen extends SuperMarioScreen {
    Sprite sprite;
    private BitmapFont font = new BitmapFont(Gdx.files.internal("font/3.fnt"), new Sprite(new Texture("font/3.png")), false);
    private com.badlogic.gdx.scenes.scene2d.ui.Label label;

    public NextLevelScreen(Game g) {
        this.game = g;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        sprite.draw(batch);
        label.draw(batch,10);
        batch.end();


    }

    @Override
    public void show() {

        super.show();
        sprite = new Sprite(new Texture(Gdx.files.internal("assets/" + SuperMario.currentLevel + ".png"))) {
            {
                setX(0);
                setY(0);
            }
        };

        label = new Label(Integer.toString(SuperMario.AllPlayerPoints), new Label.LabelStyle(font, Color.LIGHT_GRAY)) {
            {
                setX(470);
                setY(320);
            }
        };

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = SuperMario.HEIGHT - screenY; //инверсия координаты у
                if (SuperMario.currentLevel == 2) {
                    if (new Rectangle(56, 420 - 78, 170, 39).contains(screenX, screenY)) { //нажали на начало игры

                        game.setScreen(new GameScreen(game));
                        Gdx.input.setInputProcessor(null);

                    }
                    if (new Rectangle(52, 420 - 153, 244, 41).contains(screenX, screenY)) {  //нажали на таблицу лучших результатов
                        game.setScreen(new MainMenuScreen(game));
                        Gdx.input.setInputProcessor(null);
                    }
                }
                if (SuperMario.currentLevel == 3) {
                    if (new Rectangle(16, 420 - 67, 172, 45).contains(screenX, screenY)) { //нажали на начало игры

                        game.setScreen(new GameScreen(game));
                        Gdx.input.setInputProcessor(null);

                    }
                    if (new Rectangle(16, 420 - 134, 222, 41).contains(screenX, screenY)) {  //нажали на таблицу лучших результатов
                        game.setScreen(new MainMenuScreen(game));
                        Gdx.input.setInputProcessor(null);
                    }

                }
                if(SuperMario.currentLevel == 4){
                    if (new Rectangle(156, 420 - 376, 324, 58).contains(screenX, screenY)) { //нажали на начало игры

                        game.setScreen(new MainMenuScreen(game));
//                        Gdx.input.setInputProcessor(null);

                    }
                }
                return true;
            }
        });
        if(SuperMario.currentLevel ==4){
            getResults();
            int k =0;
            for(Result result:results){
                if(result.getPoint()<SuperMario.AllPlayerPoints || k>10){
                    break;
                }
                k++;
            }
            if(k<11)
                results.add(k,new Result("Putin",Integer.toString(SuperMario.AllPlayerPoints)));

            try {

                FileWriter writer = new FileWriter("assets/result.txt");
                BufferedWriter writer1 = new BufferedWriter(writer);
                writer1.write("");
                for (int i = 0;i<10;i++){
                    writer.write(results.get(i).getName());
                    writer.write(System.getProperty("line.separator"));
                }
                for (int i = 0;i<10;i++){
                    writer.write((Integer.toString(results.get(i).getPoint())));
                    writer.write(System.getProperty("line.separator"));
                }
                writer1.close();
                writer.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }


        }
    }
}

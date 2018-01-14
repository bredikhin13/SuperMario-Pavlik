package com.supermario.game.bonus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.model.Player;

import java.util.ArrayList;

/**
 * Created by Анна on 06.12.2014.
 */

public class RubBonus extends Bonus {
    BitmapFont font = new BitmapFont(Gdx.files.internal("font/1.fnt"), new Sprite(new Texture("font/1.png")), false);
    private Texture texture = new Texture(Gdx.files.internal("assets/coin.png"));
    private TextureRegion[] regionsCoin = new TextureRegion[6];
    private float stateTime = 0;
    private Animation coinAnimation;
    private TextureRegion currentFrame;
//    Sprite sprite;

    public RubBonus(final int x,final int y) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 6, texture.getHeight());
        for (int i = 0; i < 6; i++) {
            regionsCoin[i] = tmp[0][i];
        }
        point = new Vector2(x,y);
        sprite = new Sprite(regionsCoin[0].getTexture(), regionsCoin[0].getRegionWidth(), regionsCoin[0].getRegionHeight()) {
            {
                setX(x*30);
                setY((17-y)*30);
            }
        };
        coinAnimation = new Animation(0.1f,regionsCoin);
    }
    @Override
    public void playAnimation(){
        stateTime +=Gdx.graphics.getDeltaTime();
        currentFrame = coinAnimation.getKeyFrame(stateTime,true);
        sprite.setRegion(currentFrame);
    }

    @Override
    public void make(Player player) {
        player.count += 100;
        player.setCount();
        labels.add(new Label("+100", new Label.LabelStyle(font, Color.LIGHT_GRAY)));
        visible = false;
    }
}

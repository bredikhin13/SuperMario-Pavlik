package com.supermario.game.bonus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.model.Player;

import java.util.ArrayList;

/**
 * Created by Анна on 06.12.2014.
 */
public abstract class Bonus {

    public abstract void make (Player player);

    static ArrayList <TextureRegion> texture;

    public static ArrayList<Label> labels = new ArrayList<Label>();

    public Vector2 point;
    public Sprite sprite;
    public boolean visible = true;
    public abstract void playAnimation();
    public static void setTexture (){

        texture = new ArrayList<TextureRegion>();
        texture.add(new TextureRegion(new Texture(Gdx.files.internal("assets/rub.png"))));
        texture.add(new TextureRegion(new Texture(Gdx.files.internal("assets/dol.png"))));
    }
}

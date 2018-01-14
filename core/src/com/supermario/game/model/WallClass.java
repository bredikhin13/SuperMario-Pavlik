package com.supermario.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by User on 22.12.2014.
 */
public class WallClass  {
    public Sprite sprite;

    public WallClass(Sprite s,int x, int y){
        this.sprite = s;
        sprite.setX(x);
        sprite.setY(y);
    }


}

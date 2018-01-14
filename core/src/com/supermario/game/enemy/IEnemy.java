package com.supermario.game.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Анна on 06.12.2014.
 */
public abstract class IEnemy {
    public abstract void moving ();
    public Sprite sprite;
    public boolean live;
}

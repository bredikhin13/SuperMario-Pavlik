package com.supermario.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.supermario.game.model.Map;

import java.util.ArrayList;

/**
 * Created by User on 21.12.2014.
 */
public class EnemyBullet extends IEnemy {

    public Vector2 point, size;
    int dx = 450;
    Map map;
    Texture texture = new Texture(Gdx.files.internal("assets/bullet.png"));
    private float startX;

    public EnemyBullet(Map m, int x, int y,int d) {
        map = m;
        point = new Vector2(x, y);
        startX = x;
        sprite = new Sprite(texture);
        sprite.setX(x);
        sprite.setY(y);
        dx*=d;
        size = new Vector2(6, 2);
        live = true;
    }

    void Collision(float x, float y) {//проверка на столкновение с объектами
        for (int i = (int) (map.getHeight() - (y + size.y) / map.cellSize); i <= (int) (map.getHeight() - y / map.cellSize); i++)
            for (int j = (int) x / map.cellSize; j <= (int) (x + size.x) / map.cellSize; j++) {
//                if (map.charMapArray[i][j] == 'B' || map.charMapArray[i][j] == 'S') {// Стены
                if (map.charMapArray[i][j] == 'B' || map.charMapArray[i][j] == 'S' || map.charMapArray[i][j] == 'G' || map.charMapArray[i][j] == 'T'|| map.charMapArray[i][j] == 'I') {
                    dx = 0;
                    live = false;
                }
            }
        if (live && (x >= (map.player.point.x - size.x)) && (x <= (map.player.point.x + map.player.playerSize.x)) && (map.player.point.y<y && map.player.point.y + map.player.playerSize.y>y)&& !map.player.stunned) {//somnitelno blya
            dx *= -1;
            map.player.getDamage(dx);
            dx = 0;
            live = false;
        }
    }

    @Override
    public void moving() {
        Collision(point.x += dx * Gdx.graphics.getDeltaTime(), point.y);
        sprite.setX(point.x);
        if (Math.abs(point.x-startX)>10*map.cellSize){
            dx = 0;
            live = false;
        }
    }
}

package com.supermario.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.supermario.game.bonus.Bonus;
import com.supermario.game.enemy.EnemyBullet;
import com.supermario.game.enemy.EnemyFiring;
import com.supermario.game.enemy.EnemyWalker;
import com.supermario.game.enemy.IEnemy;
import com.supermario.game.model.Map;

/**
 * Created by Анна on 30.11.2014.
 */
public class MapRenderer {
    Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render(SpriteBatch batch) {
        map.drawMap();
        batch.begin();
        map.player.playerSprite.draw(batch);
        for (Bonus bonus : map.bonuses){
            if(bonus.visible) {
                bonus.playAnimation();
                bonus.sprite.draw(batch);
            }
        }
        for (IEnemy enemy : map.enemies) {
            enemy.sprite.draw(batch);
            if (enemy instanceof EnemyFiring) {
                for (EnemyBullet bullet : ((EnemyFiring) enemy).bullets) {
                    if (bullet.live) {
                        bullet.sprite.draw(batch);
                    }
                }
            }
        }

        for (Label label : Bonus.labels) {
            label.draw(batch, 10);
        }

        for (int i = 0; i < map.player.countLife; i++) {
            float pos = map.player.liveSprite.getX();
            map.player.liveSprite.setX(pos + i * map.cellSize);
            map.player.liveSprite.draw(batch);
            map.player.liveSprite.setX(pos);
        }

        map.player.labelPoints.draw(batch, 10);
        batch.end();
    }
}

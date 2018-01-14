package com.supermario.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.supermario.game.model.Map;

/**
 * Created by Анна on 06.12.2014.
 */
public class EnemyWalker extends IEnemy {

    public Vector2 point, size;
    int dx = -170;
    Map map;
    //    Texture textureup = new Texture(Gdx.files.internal("assets/enemy.png"));
    private Texture texture = new Texture(Gdx.files.internal("assets/Sprite2.png"));
    private TextureRegion[] walkObama = new TextureRegion[6];
    float stateTime = 0;
    private TextureRegion currentFrame;
    public Animation walkObamaAnimation;

    public EnemyWalker(Map m, int x, int y) {
        map = m;
        point = new Vector2(x, y + 1);
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 6, texture.getHeight());
        for (int i = 0; i < 6; i++) {
            walkObama[i] = tmp[0][i];
        }
        sprite = new Sprite(walkObama[0].getTexture(), walkObama[0].getRegionWidth(), walkObama[0].getRegionHeight()) {
            {
                setX(point.x);
                setY(point.y);
                setScale(getScaleX() * -1, getScaleY());
            }
        };
//        sprite.setX(x);
//        sprite.setY(y + 1);
        walkObamaAnimation = new Animation(0.08f, walkObama);
        size = new Vector2(50, 68);
        live = true;
    }

    public void playAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkObamaAnimation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        if (sprite.getScaleX() * dx < 0) {
            sprite.setScale(sprite.getScaleX() * -1, sprite.getScaleY());
        }
    }

    float Collision(float x, float y) {//проверка на столкновение с объектами
        for (int i = (int) (map.getHeight() - (y + size.y) / map.cellSize); i <= (int) (map.getHeight() - y / map.cellSize); i++)
            for (int j = (int) x / map.cellSize; j <= (int) (x + size.x) / map.cellSize; j++) {
//                if (map.charMapArray[i][j] == 'B' || map.charMapArray[i][j] == 'S') {// Стены
                if (map.charMapArray[i][j] == 'B' || map.charMapArray[i][j] == 'S' || map.charMapArray[i][j] == 'G' || map.charMapArray[i][j] == 'T' || map.charMapArray[i][j] == 'I') {
                    if (dx > 0) x = j * map.cellSize - size.x - 1;
                    if (dx < 0) x = (j + 1) * map.cellSize + 1;
                    dx *= -1;
                }
                if (map.charMapArray[(int) (map.getHeight() - y / map.cellSize) + 1][j] == ' ') {
                    if (dx > 0) x = j * map.cellSize - size.x - 1;
                    if (dx < 0) x = (j + 1) * map.cellSize + 1;
                    dx *= -1;
                }
            }
        if (live && (x >= (map.player.point.x - size.x)) && (x <= (map.player.point.x + map.player.playerSize.x)) && (Math.abs(y - map.player.point.y) <= size.y) && !map.player.stunned) {//somnitelno blya
            if (!map.player.grounded && map.player.dy < 0) {
                map.player.dy = 200;
                map.player.grounded = false;
                dx = 0;
                live = false;

                sprite = new Sprite(new Texture("assets/dead.png")) {
                    {
                        setY(point.y);
                        setX(point.x);
                    }
                };
            } else {
                if (dx > 0) x = point.x;
                if (dx < 0) x = point.x;
                dx *= -1;
                map.player.getDamage(dx);
            }
        }
        return x;
    }

    @Override
    public void moving() {
        point.x = Collision(point.x + dx * Gdx.graphics.getDeltaTime(), point.y);
        sprite.setX(point.x);
        if(live) {
            playAnimation();
        }
    }
}

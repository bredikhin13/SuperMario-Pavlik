package com.supermario.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.supermario.game.model.Map;

import java.util.ArrayList;

/**
 * Created by Анна on 06.12.2014.
 */
public class EnemyFiring extends IEnemy {

    public Vector2 point, size;
    int dx = -100;
    Map map;
    int timer = 0;
    Texture textureup = new Texture(Gdx.files.internal("assets/sprite1.png"));
    public ArrayList<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    private TextureRegion[] walkObamaUpGun = new TextureRegion[6];
    private float stateTime = 0;
    private TextureRegion currentFrameUp;
    public Animation walkObamaUpGunAnimation;


    public EnemyFiring(Map m, int x, int y) {
        map = m;
        point = new Vector2(x, y + 1);
        TextureRegion[][] tmp = TextureRegion.split(textureup, textureup.getWidth() / 6, textureup.getHeight());
        for (int i = 0; i < 6; i++) {
            walkObamaUpGun[i] = tmp[0][i];
        }
        sprite = new Sprite(walkObamaUpGun[0].getTexture(), walkObamaUpGun[0].getRegionWidth(), walkObamaUpGun[0].getRegionHeight()) {{
            setX(point.x);
            setY(point.y);
            setScale(getScaleX() * -1, getScaleY());
        }};
        walkObamaUpGunAnimation = new Animation(0.1f, walkObamaUpGun);
        size = new Vector2(60, 70);
        live = true;
    }

    public void playAnimation() {
        if (dx * sprite.getScaleX() < 0) {
            walkObamaUpGunAnimation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        } else {
            walkObamaUpGunAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrameUp = walkObamaUpGunAnimation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrameUp);

        if ((point.x - map.player.point.x) > 0 && sprite.getScaleX() > 0) {
            sprite.setScale(sprite.getScaleX() * -1, sprite.getScaleY());
        }
        if ((point.x - map.player.point.x) < 0 && sprite.getScaleX() < 0) {
            sprite.setScale(sprite.getScaleX() * -1, sprite.getScaleY());
        }
    }

    float Collision(float x, float y) {//проверка на столкновение с объектами
        for (int i = (int) (map.getHeight() - (y + size.y) / map.cellSize); i <= (int) (map.getHeight() - y / map.cellSize); i++)
            for (int j = (int) x / map.cellSize; j <= (int) (x + size.x) / map.cellSize; j++) {
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

    public void Shoot() {
        timer++;
        int dir = 1;
        if (timer == 70) {
            if (map.player.point.x < point.x) {
                dir = -1;
            }
            bullets.add(new EnemyBullet(map, (int) point.x, (int) point.y + 46, dir));
            timer = 0;
        }
    }

    @Override
    public void moving() {
            point.x = Collision(point.x + dx * Gdx.graphics.getDeltaTime(), point.y);
            sprite.setX(point.x);
        if (live) {
            playAnimation();
            Shoot();
        }
    }

}

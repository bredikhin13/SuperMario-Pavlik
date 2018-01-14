package com.supermario.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.supermario.game.SuperMario;
import com.supermario.game.bonus.Bonus;


/**
 * Created by Анна on 30.11.2014.
 */

//методы движение, анимация, взаимодействие с картой, взаимодействие с врагами
public class Player {

    public int dx, dy; // ускорение по оси Х и Y
    private Map map; //карта
    public Vector2 point; //вектор, хранит текущее положение игрока
    public boolean grounded = true; // положение игрока: true - на земле, false - в воздухе
    public int countLife = 3; //количество жизней
    public int count = 0; //количество очков
    private BitmapFont font = new BitmapFont(Gdx.files.internal("font/1.fnt"), new Sprite(new Texture("font/1.png")), false);
    public Label labelPoints;
    public Sprite playerSprite, liveSprite;
    public final Vector2 playerSize = new Vector2(53, 68);//Размер спрайта персонажа
    public Animation walkAnimation;
    public boolean isFinished = false;//достиг ли игрок конца уровня
    public boolean stunned = false;//Оглушен ли игрок врагом
    public final int Gravity = 20;//Влияние гравитации
    public final int SpeedX = 300;//Скорость движения по оси Х
    public final int SpeedY = 600;//Скорость движения по оси Y
    private Texture texture = new Texture(Gdx.files.internal("assets/put.png"));// анимационный лист
    private TextureRegion[] walkPutin = new TextureRegion[8];
    private float stateTime = 0;
    private TextureRegion currentFrame;
    public boolean isGameStart = false;

    public Player(final Map map, float x, float y) {
        dx = 0;
        dy = 0;
        this.map = map;
        labelPoints = new Label("0", new Label.LabelStyle(font, Color.LIGHT_GRAY)) {
            {
                setX(SuperMario.WIDTH - 80);
                setY(SuperMario.HEIGHT - map.cellSize);
            }
        };
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight());
        for (int i = 0; i < 8; i++) {
            walkPutin[i] = tmp[0][i];
        }
        walkAnimation = new Animation(0.07f, walkPutin);
        point = new Vector2(x, y);
        liveSprite = new Sprite(new Texture(Gdx.files.internal("assets/live.png"))) {
            {
                setX(10);
                setY(SuperMario.HEIGHT - map.cellSize);
            }
        };
        playerSprite = new Sprite(walkPutin[0].getTexture(), walkPutin[0].getRegionWidth(), walkPutin[0].getRegionHeight()) {
            {
                setX(point.x);
                setY(point.y);
                setScale(getScaleX()*-1,getScaleY());
            }
        };

        //отрисовка игрока в координатах х,у
    }

    public void setCount() {// отбражение очков на экране
        labelPoints.setText(Integer.toString(count));
    }

    float Collision(char dir, float x, float y) {//проверка на столкновение с объектами
        for (int i = (int) (map.getHeight() - (y + playerSize.y) / map.cellSize); i <= (int) (map.getHeight() - y / map.cellSize); i++)
            for (int j = (int) x / map.cellSize; j <= (int) (x + playerSize.x) / map.cellSize; j++) {
                if (map.charMapArray[i][j] == 'B' || map.charMapArray[i][j] == 'S' || map.charMapArray[i][j] == 'G' || map.charMapArray[i][j] == 'T'|| map.charMapArray[i][j] == 'I') {// Стены
                    if (dx > 0 && dir == 'x') x = j * map.cellSize - playerSize.x - 1;
                    if (dx < 0 && dir == 'x') x = (j + 1) * map.cellSize + 1;
                    if (dy > 0 && dir == 'y') {
                        y = (map.getHeight() - i - 1) * map.cellSize - playerSize.y - 1;
                        dy = 0;
                    }
                    if (dy < 0 && dir == 'y') {
                        if (stunned)
                            stunned = false;
                        grounded = true;
                        dy = 0;
                        y = (map.getHeight() - i) * map.cellSize + 1;
                    }
                    if (j > map.getWidth() - 5) {
                        isFinished = true;
                    }
                }
//                if (map.charMapArray[i][j] == 'D') { // Бонус доллар
//                    map.dollar.make(this);
//                    map.charMapArray[i][j] = ' ';
//                    Bonus.labels.get(Bonus.labels.size() - 1).setPosition(point.x, point.y + playerSize.y);
//                }
                if (map.charMapArray[i][j] == 'P' || map.charMapArray[i][j] == 'D') { // Бонус
                    //map.ruble.make(this);
                    for (Bonus bonus: map.bonuses){
                        if(bonus.point.x == j && bonus.point.y == i){
                            bonus.make(this);
                        }
                    }
                    map.charMapArray[i][j] = ' ';
                    Bonus.labels.get(Bonus.labels.size() - 1).setPosition(point.x, point.y + playerSize.y);
                }
            }
        if (dir == 'x') return x;
        else return y;
    }


    public void getDamage(int dir) {// Получение урона от врагов. Пока игрок оглушен он не может менять направление движения
//        texture = new Texture(Gdx.files.internal("assets/playerd.png"));
//        playerSprite.setTexture(texture);
        stunned = true;
        dy = 400;
        grounded = false;
        if(dir>0){
        dx = -300;
        }else {
            dx = 300;
        }
        countLife--;
    }

    public void playerAnimation(int dir){// Анимация игрока
        if(grounded) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            playerSprite.setRegion(currentFrame);
        }
        if(playerSprite.getScaleX()*dir<0){
            playerSprite.setScale(playerSprite.getScaleX()*-1,playerSprite.getScaleY());
        }
    }

    public void playerMove() {
        point.x = Collision('x', point.x + dx * Gdx.graphics.getDeltaTime(), point.y);
        playerSprite.setX(point.x);
        if (grounded && (map.charMapArray[(int) ((map.getHeight() - point.y / map.cellSize) + 1)][(int) point.x / map.cellSize]) == ' ') {
            dy = -100;
            grounded = false;
        }
        if (!stunned)
            dx = 0;
        if (!grounded) {
            point.y = Collision('y', point.x, point.y + dy * Gdx.graphics.getDeltaTime());
            playerSprite.setY(point.y);
            dy -= Gravity;
        }
        for (Label l : Bonus.labels) {
            l.setY(l.getY() + 100 * Gdx.graphics.getDeltaTime());
            if (l.getY() > 540) {
                Bonus.labels.remove(l);
                break;
            }
        }
    }
    public void dispose(){
        font.dispose();
        texture.dispose();
    }
}
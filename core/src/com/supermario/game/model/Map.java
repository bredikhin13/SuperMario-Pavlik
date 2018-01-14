package com.supermario.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.supermario.game.SuperMario;
import com.supermario.game.bonus.Bonus;
import com.supermario.game.bonus.DolBonus;
import com.supermario.game.bonus.RubBonus;
import com.supermario.game.enemy.EnemyFiring;
import com.supermario.game.enemy.EnemyWalker;
import com.supermario.game.enemy.IEnemy;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Анна on 30.11.2014.
 */

//загрузка карты уровня из файла, движение карты, музыка
public class Map {


    private Scanner scanner;
    public boolean isDrawing;
    private SpriteBatch batchMap;
    private String[] linesArray; //схема карты
    public char[][] charMapArray;
    private int height, width; //высота и длина карты
    public final int cellSize = 30;//размер клетки игрового поля
    private Sprite wallSprite, dolSprite, rubSprite, brickSprite,tubeSprite,groundSprite;
    public Sprite backSprite;
    public Player player; //ссылка на персонажа
    public ArrayList<IEnemy> enemies = new ArrayList<IEnemy>();
    public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
    public DolBonus dollar;
    public RubBonus ruble;
    private int w, h, ws, hs;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Map(SpriteBatch batch) {
//        dollar = new DolBonus();
//        ruble = new RubBonus();
        height = 0;
        width = 0;
        batchMap = batch;
        isDrawing = false;
        wallSprite = new Sprite(new Texture("assets/wall.png"));
        dolSprite = new Sprite(new Texture("assets/ddol.png"));
        rubSprite = new Sprite(new Texture("assets/rrub.png"));
        brickSprite = new Sprite(new Texture("assets/brick.png"));
        tubeSprite = new Sprite(new Texture("assets/tube.png"));
        groundSprite = new Sprite(new Texture("assets/ground.png"));
        backSprite = new Sprite(new Texture("assets/back2.png"));
        player = new Player(this, 31, 31);
        loadMap();
    }

    public SpriteBatch getBatch(){
        return this.batchMap;
    }
    private void loadMap() {
        List<String> lines = new ArrayList<String>();
        try {
            scanner = new Scanner(new File("maps/" + SuperMario.currentLevel + ".txt"));
            while (scanner.hasNextLine()) {
                height++;
                lines.add(scanner.nextLine());
            }
            linesArray = lines.toArray(new String[0]);
            width = linesArray[0].length();
            charMapArray = new char[height][width];
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) {
                    charMapArray[i][j] = linesArray[i].charAt(j);
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    void getViewSize() {
        float x = player.point.x / 30;
        float y = player.point.y / 30;
        if (height - y > height - 7) {
            hs = height - 15;
            h = height;
        } else {
            hs = (int) (height - y - 7);
            h = (int) (height - y + 8);
        }
        if (height - y <= 7) {
            hs = 0;
            h = 15;
        }
        if (x <= 10) {
            ws = 0;
            w = 21;
        } else {
            ws = (int) x - 10;
            w = (int) x + 11;
        }
        if (x >= width - 10) {
            ws = width - 21;
            w = width;
        }
        if (!isDrawing) {
            w = width;
            ws = 0;
            h = height;
            hs = 0;
        }
    }

    public void drawMap() {
        getViewSize();
        batchMap.begin();
        backSprite.draw(batchMap);
        batchMap.end();
        for (int i = hs; i < h; i++)
            for (int j = ws; j < w; j++) {
                switch (charMapArray[i][j]) {
                    case 'B':
                        setSprite(wallSprite, j, i);
//                        new WallClass(wallSprite,j,i);
                        break;
                    case 'S':
//                        new WallClass(brickSprite,j,i);
                        setSprite(brickSprite,j,i);
                        break;
                    case 'G':
                        setSprite(groundSprite,j,i);
                        break;
                    case 'T':
                        setSprite(tubeSprite,j,i);
                        break;
                    case 'I':
                        break;
                    case 'V':
                        if (!isDrawing)
                            enemies.add(new EnemyWalker(this, j * cellSize, (height - i - 1) * cellSize));
                        break;
                    case 'D':
//                        setSprite(dolSprite, j, i);
                        if(!isDrawing){
                            bonuses.add(new DolBonus(j,i));
                        }
                        break;
                    case 'P':
//                        setSprite(rubSprite, j, i);
                        if(!isDrawing)
                        bonuses.add(new RubBonus(j,i));
                        break;
                    case 'W':
                        if (!isDrawing)
                            enemies.add(new EnemyFiring(this, j * cellSize, (height - i - 1) * cellSize));
                        break;
                    default:
                        break;
                }
            }
        isDrawing = true;
    }

    private void setSprite(Sprite sprite, int x, int y) {
        sprite.setX(x * cellSize);
        sprite.setY((height - 1 - y) * cellSize);
        batchMap.begin();
        sprite.draw(batchMap);
        batchMap.end();
    }

    public void dispose() {
        wallSprite.getTexture().dispose();
        dolSprite.getTexture().dispose();
        rubSprite.getTexture().dispose();
        brickSprite.getTexture().dispose();
        player.dispose();
        for (IEnemy e : enemies) {
            e.sprite.getTexture().dispose();
        }
    }

}
package com.supermario.game.Res;

/**
 * Created by User on 26.12.2014.
 */
public class Result {
    private String Name;
    private int Point;
    public String getName(){
        return Name;
    }
    public int getPoint(){
        return Point;
    }

    public Result(String s, String k){
        Name = s;
        Point = Integer.parseInt(k);
    }
}

package com.example.user.myapplication;

import java.util.ArrayList;

/**
 * Created by USER on 2017-08-16.
 */

public class Menu {
    public String function;
    public ArrayList<String> item = new ArrayList<String>();

    public Menu(String function){
        this.function = function;
    }

    public String toString(){
        return function;
    }
}

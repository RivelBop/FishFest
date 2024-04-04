package com.rivelbop.fishfest.system;

import com.badlogic.gdx.graphics.Texture;

public class Upgrade {
    public String name;
    public Texture icon;
    public int level;
    public boolean unlocked;

    public Upgrade(String name, Texture icon) {
        this.name = name;
        this.icon = icon;
    }
}
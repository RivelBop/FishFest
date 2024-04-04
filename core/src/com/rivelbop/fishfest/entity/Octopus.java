package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class Octopus extends Enemy {
    public Octopus(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 70;
        health = 70;
        damage = 10;
        speed = 150f;
        sprite = new Sprite(new Texture("badlogic.jpg"));
    }

    @Override
    public Octopus create(GameScreen game) {
        return new Octopus(game);
    }
}
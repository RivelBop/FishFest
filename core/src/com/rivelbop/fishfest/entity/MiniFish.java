package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class MiniFish extends Enemy {
    public MiniFish(GameScreen gameScreen) {
        super(gameScreen);

        maxHealth = 35;
        health = 35;
        damage = 20;
        speed = 100f;
        sprite = new Sprite(new Texture("badlogic.jpg"));
    }

    @Override
    public MiniFish create(GameScreen game) {
        return new MiniFish(game);
    }
}
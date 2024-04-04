package com.rivelbop.fishfest.entity;

import com.rivelbop.fishfest.screen.GameScreen;

public class EelFish extends Enemy {
    public EelFish(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 25;
        health = 25;
        damage = 10;
        speed = 200f;
    }

    @Override
    public EelFish create(GameScreen game) {
        return new EelFish(game);
    }
}
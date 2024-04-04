package com.rivelbop.fishfest.entity;

import com.rivelbop.fishfest.screen.GameScreen;

public class Scorpion extends Enemy {
    public Scorpion(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 50;
        health = 50;
        damage = 50;
        speed = 30f;
    }

    @Override
    public Scorpion create(GameScreen game) {
        return new Scorpion(game);
    }
}
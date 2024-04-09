package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class EelFish extends Enemy {
    private static Texture texture;

    public EelFish(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 25;
        health = 25;
        damage = 10;
        speed = 120f;

        if(texture == null) {
            texture = this.gameScreen.game.assets.get("EelFish.png", Texture.class);
        }
        sprite = new Sprite(texture);
    }

    @Override
    public EelFish create(GameScreen game) {
        return new EelFish(game);
    }
}
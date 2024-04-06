package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class MiniFish extends Enemy {
    private static Texture texture;

    public MiniFish(GameScreen gameScreen) {
        super(gameScreen);

        maxHealth = 35;
        health = 35;
        damage = 20;
        speed = 80f;

        if(texture == null) {
            texture = this.gameScreen.game.assets.get("MiniFish.png", Texture.class);
        }
        sprite = new Sprite(texture);
    }

    @Override
    public MiniFish create(GameScreen game) {
        return new MiniFish(game);
    }
}
package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class Lobster extends Enemy {
    private static Texture texture;

    public Lobster(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 50;
        health = 50;
        damage = 50;
        speed = 70f;

        if(texture == null) {
            texture = this.gameScreen.game.assets.get("Lobster.png", Texture.class);
        }
        sprite = new Sprite(texture);
    }

    @Override
    public Lobster create(GameScreen game) {
        return new Lobster(game);
    }
}
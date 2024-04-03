package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class Octopus extends Entity {

    public Octopus(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 70;
        health = 70;
        damage = 10;
        speed = 150f;
        sprite = new Sprite(new Texture("badlogic,jpg"));
    }

    @Override
    public Octopus create(GameScreen game) {
        return new Octopus(game);
    }

    public void update() {
        if (sprite.getX() > gameScreen.player.sprite.getX()) {
            sprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(true, false);
        }
        if (sprite.getX() < gameScreen.player.sprite.getX()) {
            sprite.translateX(speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(false, false);
        }
        if (sprite.getY() > gameScreen.player.sprite.getY()) {
            sprite.translateY(-speed * Gdx.graphics.getDeltaTime());
        }
        if (sprite.getY() < gameScreen.player.sprite.getY()) {
            sprite.translateY(speed * Gdx.graphics.getDeltaTime());
        }
    }

}

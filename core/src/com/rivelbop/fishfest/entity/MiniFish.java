package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rivelbop.fishfest.screen.GameScreen;

public class MiniFish extends Entity {


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

    public void update(GameScreen gameScreen) {
        if (sprite.getX() > this.gameScreen.player.sprite.getX()) {
            sprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(true, false);
        }
        if (sprite.getX() < this.gameScreen.player.sprite.getX()) {
            sprite.translateX(speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(false, false);
        }
        if (sprite.getY() > this.gameScreen.player.sprite.getY()) {
            sprite.translateY(-speed * Gdx.graphics.getDeltaTime());
        }
        if (sprite.getY() < this.gameScreen.player.sprite.getY()) {
            sprite.translateY(speed * Gdx.graphics.getDeltaTime());
        }
    }

}

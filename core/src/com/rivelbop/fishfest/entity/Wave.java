package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Wave {

    public Sprite sprite;
    private GameScreen gameScreen;
    private final int direction;

    public Wave(int direction, float positionx, float positiony) {
        this.direction = direction;
        sprite = new Sprite(new Texture("Wave.png"));
        sprite.setPosition(positionx, positiony);
    }

    public void update() {
        if (direction == 1) {
            sprite.translateX(200 * Gdx.graphics.getDeltaTime());
            sprite.setFlip(false, false);
        }
        if (direction == 2) {
            sprite.translateX(-200 * Gdx.graphics.getDeltaTime());
            sprite.setFlip(true, false);
        }
        if (direction == 3) {
            sprite.translateY(200 * Gdx.graphics.getDeltaTime());
            sprite.setRotation(90f);
        }
        if (direction == 4) {
            sprite.translateY(-200 * Gdx.graphics.getDeltaTime());
            sprite.setRotation(-90f);
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);

    }

}

package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wave {
    public Sprite sprite;
    private final int direction;

    public Wave(int direction, float positionx, float positiony) {
        this.direction = direction;
        sprite = new Sprite(new Texture("Wave.png"));
        sprite.setPosition(positionx, positiony);
    }

    public void update() {
        switch (direction) {
            case 1:
                sprite.translateX(200 * Gdx.graphics.getDeltaTime());
                sprite.setFlip(false, false);
                break;
            case 2:
                sprite.translateX(-200 * Gdx.graphics.getDeltaTime());
                sprite.setFlip(true, false);
                break;
            case 3:
                sprite.translateY(200 * Gdx.graphics.getDeltaTime());
                sprite.setRotation(90f);
                break;
            case 4:
                sprite.translateY(-200 * Gdx.graphics.getDeltaTime());
                sprite.setRotation(-90f);
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
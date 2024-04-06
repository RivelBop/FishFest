package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Wave {
    private static Texture texture;
    private float speed;
    public Sprite sprite;
    private final int direction;

    public Wave(GameScreen gameScreen, int direction, float positionx, float positiony, int level) {
        this.direction = direction;

        if(texture == null) {
            texture = gameScreen.game.assets.get("Wave.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionx, positiony);

        speed = 200f + (level * 50f);
    }

    public void update() {
        switch (direction) {
            case 1:
                sprite.translateX(speed * Gdx.graphics.getDeltaTime());
                sprite.setFlip(false, false);
                break;
            case 2:
                sprite.translateX(-speed * Gdx.graphics.getDeltaTime());
                sprite.setFlip(true, false);
                break;
            case 3:
                sprite.translateY(speed * Gdx.graphics.getDeltaTime());
                sprite.setRotation(90f);
                break;
            case 4:
                sprite.translateY(-speed * Gdx.graphics.getDeltaTime());
                sprite.setRotation(-90f);
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
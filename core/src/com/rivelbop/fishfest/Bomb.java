package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Bomb {
    public Sprite sprite;
    public float bombTimer;
    public GameScreen game;

    public Bomb(float positionx, float positiony) {
        sprite = new Sprite(new Texture("badlogic.jpg"));
        sprite.setPosition(positionx, positiony);
    }

    public void update() {
        bombTimer += Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
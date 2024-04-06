package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Heart {
    private static Texture texture;
    public Sprite sprite;

    public Heart(GameScreen gameScreen, float positionX, float positionY) {
        if(texture == null) {
            texture = gameScreen.game.assets.get("Heart.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionX, positionY);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
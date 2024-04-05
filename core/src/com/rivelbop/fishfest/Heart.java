package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Heart {
    public Sprite sprite;

    public Heart(float positionX, float positionY) {
        sprite = new Sprite(new Texture(Gdx.files.internal("Heart.png")));
        sprite.setPosition(positionX, positionY);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
package com.rivelbop.fishfest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Xp {
    private static Texture texture;
    public Sprite sprite;

    public Xp(GameScreen gameScreen, float positionX, float positionY) {
        if(texture == null) {
            texture = gameScreen.game.assets.get("Xp.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionX, positionY);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
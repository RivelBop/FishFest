package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Ink {

    private static Texture texture;
    public Sprite sprite;
    private final int direction;

    public Ink(GameScreen gameScreen, int direction,float positionX, float positionY) {
        this.direction = direction;

        if(texture == null) {
            texture = gameScreen.game.assets.get("Ink.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionX, positionY);

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
                break;
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

}

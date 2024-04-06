package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class Bomb {
    private static Texture texture;
    public Sprite sprite;
    public float bombTimer;
    public boolean exploded;
    private GameScreen game;

    public Bomb(GameScreen game, float positionx, float positiony) {
        this.game = game;
        if(texture == null) {
            texture = game.game.assets.get("bomb.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionx, positiony);
    }

    public void update() {
        bombTimer += Gdx.graphics.getDeltaTime();

        if (!exploded && bombTimer >= 1f) {
            exploded = true;
            game.player.cameraShake.resetAndReconfigure(10f,10f,20f);
            game.player.cameraShake.startShaking();
        }

    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
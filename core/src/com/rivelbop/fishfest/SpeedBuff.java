package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.screen.GameScreen;

public class SpeedBuff {
    private static Texture texture;
    public Sprite sprite;
    private float timer;
    private GameScreen gameScreen;

    public SpeedBuff(GameScreen gameScreen, float positionX, float positionY) {
        this.gameScreen = gameScreen;
        if(texture == null) {
            texture = gameScreen.game.assets.get("SpeedBuff.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionX, positionY);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        timer += Gdx.graphics.getDeltaTime();
        if(timer >= 15f) {
            gameScreen.player.speed = 1500f;
            gameScreen.player.sprite.setColor(Color.WHITE);
        }
    }

}

package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.rivelbop.fishfest.screen.GameScreen;

public abstract class Enemy extends Entity {
    public Enemy(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void update() {
        if (sprite.getX() > gameScreen.player.sprite.getX()) {
            sprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(true, false);
        }

        if (sprite.getX() < gameScreen.player.sprite.getX()) {
            sprite.translateX(speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(false, false);
        }

        if (sprite.getY() > gameScreen.player.sprite.getY()) {
            sprite.translateY(-speed * Gdx.graphics.getDeltaTime());
        }

        if (sprite.getY() < gameScreen.player.sprite.getY()) {
            sprite.translateY(speed * Gdx.graphics.getDeltaTime());
        }

        checkCollision();
        if (health <= 0) {
            sprite.setColor(Color.RED);
        }
    }

    private void checkCollision() {
        for (int i = 0; i < gameScreen.player.waves.size; i++) {
            Wave w = gameScreen.player.waves.get(i);
            if (w.sprite.getBoundingRectangle().overlaps(bounds())) {
                health -= gameScreen.player.damage;
                gameScreen.player.waves.removeIndex(i);
                i--;
            }
        }
    }

    @Override
    public abstract Enemy create(GameScreen game);
}
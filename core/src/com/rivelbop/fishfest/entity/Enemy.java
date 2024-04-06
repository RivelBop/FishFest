package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.rivelbop.fishfest.*;
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
            gameScreen.player.xps.add(new Xp(gameScreen, sprite.getX(), sprite.getY()));

            if (FishFest.randomInt(1, 6) == 1) {
                gameScreen.player.hearts.add(new Heart(gameScreen, sprite.getX(), sprite.getY()));
            }

            if (FishFest.randomInt(1, 20) == 1) {
                gameScreen.player.speedBuffs.add(new SpeedBuff(gameScreen, sprite.getX(), sprite.getY()));
            }

        }
    }

    private void checkCollision() {
        for (int i = 0; i < gameScreen.player.waves.size; i++) {
            Wave w = gameScreen.player.waves.get(i);
            if (w.sprite.getBoundingRectangle().overlaps(bounds())) {
                gameScreen.damageTexts.add(new DamageText(gameScreen.player.damage, w.sprite.getX(), w.sprite.getY()));
                health -= gameScreen.player.damage;
                gameScreen.player.waves.removeIndex(i);
                i--;
            }
        }

        if (!gameScreen.player.hasTakenDamage && gameScreen.player.sprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) {
            gameScreen.player.health -= damage;
            gameScreen.player.hasTakenDamage = true;
        }
    }

    @Override
    public abstract Enemy create(GameScreen game);
}
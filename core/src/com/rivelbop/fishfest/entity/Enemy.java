package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.*;
import com.rivelbop.fishfest.screen.GameScreen;

public abstract class Enemy extends Entity {
    private Sound enemyDamage;
    private Sound healthTaken;
    public Enemy(GameScreen gameScreen) {
        super(gameScreen);
        enemyDamage = Gdx.audio.newSound(Gdx.files.internal("damageTaken.wav"));
        healthTaken = Gdx.audio.newSound(Gdx.files.internal("healthTaken.wav"));

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
            if(FishFest.randomInt(1,2) == 1) {
                gameScreen.player.xps.add(new Xp(gameScreen, sprite.getX(), sprite.getY()));
            }
            if (FishFest.randomInt(1, 6) == 1) {
                gameScreen.player.hearts.add(new Heart(gameScreen, sprite.getX(), sprite.getY()));
            }

        }
    }

    protected void checkCollision() {
        for(Bomb b : gameScreen.player.bombs) {
            if(b.rect.overlaps(sprite.getBoundingRectangle()) && b.exploded) {
                health = 0;
            }
        }

        for (int i = 0; i < gameScreen.player.waves.size; i++) {
            Wave w = gameScreen.player.waves.get(i);
            if (w.sprite.getBoundingRectangle().overlaps(bounds())) {
                gameScreen.damageTexts.add(new DamageText(gameScreen.player.damage, w.sprite.getX(), w.sprite.getY()));
                health -= gameScreen.player.damage;
                gameScreen.player.waves.removeIndex(i);
                enemyDamage.play();
                i--;
            }
        }

        if (!gameScreen.player.hasTakenDamage && gameScreen.player.bounds().overlaps(bounds())) {
            gameScreen.player.health -= damage;
            gameScreen.player.hasTakenDamage = true;
            healthTaken.play();
        }
    }


    @Override
    public abstract Enemy create(GameScreen game);
}
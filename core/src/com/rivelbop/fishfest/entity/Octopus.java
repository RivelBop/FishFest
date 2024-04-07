package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.*;
import com.rivelbop.fishfest.screen.GameScreen;

public class Octopus extends Enemy {
    private static Texture texture;
    private Array<Ink> inks;
    private float timer;
    private final float DISTANCE_AWAY = 40f;

    public Octopus(GameScreen gameScreen) {
        super(gameScreen);
        maxHealth = 70;
        health = 70;
        damage = 10;
        speed = 150f;

        inks = new Array<>();
        if(texture == null) {
            texture = this.gameScreen.game.assets.get("Octopus.png", Texture.class);
        }
        sprite = new Sprite(texture);
    }

    @Override
    public void update() {
        timer += Gdx.graphics.getDeltaTime();
        for(Ink i : inks) {
            i.update();
        }

        if (sprite.getX() > gameScreen.player.sprite.getX() + DISTANCE_AWAY) {
            sprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(true, false);

            if(timer >= 1f) {
                inks.add(new Ink(gameScreen, 2, sprite.getX(), sprite.getY()));
                timer = 0f;
            }
        }

        if (sprite.getX() < gameScreen.player.sprite.getX() - DISTANCE_AWAY) {
            sprite.translateX(speed * Gdx.graphics.getDeltaTime());
            sprite.setFlip(false, false);

            if(timer >= 1f) {
                inks.add(new Ink(gameScreen, 1, sprite.getX(), sprite.getY()));
                timer = 0f;
            }
        }

        if (sprite.getY() > gameScreen.player.sprite.getY() + DISTANCE_AWAY) {
            sprite.translateY(-speed * Gdx.graphics.getDeltaTime());

            if(timer >= 1f) {
                inks.add(new Ink(gameScreen, 4, sprite.getX(), sprite.getY()));
                timer = 0f;
            }
        }

        if (sprite.getY() < gameScreen.player.sprite.getY() - DISTANCE_AWAY) {
            sprite.translateY(speed * Gdx.graphics.getDeltaTime());

            if(timer >= 1f) {
                inks.add(new Ink(gameScreen, 3, sprite.getX(), sprite.getY()));
                timer = 0f;
            }
        }

        checkCollision();

        if (health <= 0) {
            gameScreen.player.xps.add(new Xp(gameScreen, sprite.getX(), sprite.getY()));

            if (FishFest.randomInt(1, 6) == 1) {
                gameScreen.player.hearts.add(new Heart(gameScreen, sprite.getX(), sprite.getY()));
            }

            if (FishFest.randomInt(1, 2) == 1) {
                gameScreen.player.speedBuffs.add(new SpeedBuff(gameScreen, sprite.getX(), sprite.getY()));
            }
        }
        System.out.println(inks.size);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        for(Ink i : inks) {
            i.render(batch);
        }
    }

    protected void checkCollision() {
        for(Bomb b : gameScreen.player.bombs) {
            if(b.rect.overlaps(bounds())) {
                health = 0;
            }
        }

        for (int i = 0; i < gameScreen.player.waves.size; i++) {
            Wave w = gameScreen.player.waves.get(i);
            if (w.sprite.getBoundingRectangle().overlaps(bounds())) {
                gameScreen.damageTexts.add(new DamageText(gameScreen.player.damage, w.sprite.getX(), w.sprite.getY()));
                health -= gameScreen.player.damage;
                gameScreen.player.waves.removeIndex(i);
                i--;
            }
        }

        if(!gameScreen.player.hasTakenDamage) {
            for (int i = 0; i < inks.size; i++) {
                Ink n = inks.get(i);
                if (n.sprite.getBoundingRectangle().overlaps(gameScreen.player.bounds())){
                    gameScreen.player.health -= damage;
                    gameScreen.player.speed = 1000f;
                    gameScreen.player.hasTakenDamage = true;
                    inks.removeIndex(i);
                    i--;
                }
            }
        }
    }

    @Override
    public Octopus create(GameScreen game) {
        return new Octopus(game);
    }
}
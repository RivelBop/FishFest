package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.rivelbop.fishfest.screen.GameScreen;

public abstract class Entity {
    protected GameScreen gameScreen;
    public Sprite sprite;
    public int maxHealth, health, damage;
    public float speed;

    public Entity(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle bounds() {
        return sprite.getBoundingRectangle();
    }

    public float x() {
        return bounds().x;
    }

    public float y() {
        return bounds().y;
    }

    public float width() {
        return bounds().width;
    }

    public float height() {
        return bounds().height;
    }

    public float centerX() {
        return x() + width() / 2f;
    }

    public float centerY() {
        return y() + height() / 2f;
    }

    public abstract Entity create(GameScreen game);
}
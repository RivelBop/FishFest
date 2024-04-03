package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.rivelbop.fishfest.screen.GameScreen;
import com.rivelbop.rivelworks.physics2d.DynamicBody;

public class Player extends Entity {
    private final DynamicBody body;

    public Player(GameScreen gameScreen) {
        super(gameScreen);

        maxHealth = 100;
        health = 100;
        damage = 15;
        speed = 1500f;

        sprite = new Sprite(this.gameScreen.game.assets.get("goldfish.png", Texture.class));
        sprite.scale(2f);

        body = new DynamicBody(this.gameScreen.world, new PolygonShape() {{
            setAsBox(sprite.getWidth(), sprite.getHeight());
        }}, 0.1f, 0f, 0f);
        body.getBody().setFixedRotation(true);
        body.getBody().setUserData(sprite);
    }

    @Override
    public Entity create(GameScreen game) {
        return new Player(game);
    }

    public void update() {
        body.getBody().setLinearVelocity(0f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.getBody().setLinearVelocity(body.getBody().getLinearVelocity().x, speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.getBody().setLinearVelocity(-speed, body.getBody().getLinearVelocity().y);
            sprite.setFlip(true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.getBody().setLinearVelocity(body.getBody().getLinearVelocity().x, -speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.getBody().setLinearVelocity(speed, body.getBody().getLinearVelocity().y);
            sprite.setFlip(false, false);
        }
        sprite.setPosition(body.getBody().getPosition().x - sprite.getWidth() / 2f,
                body.getBody().getPosition().y - sprite.getHeight() / 2f);
    }

    public void isColliding(Entity entity) {

    }
}
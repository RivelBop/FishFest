package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.*;
import com.rivelbop.fishfest.screen.GameScreen;
import com.rivelbop.rivelworks.physics2d.DynamicBody;
import com.rivelbop.rivelworks.ui.Font;

public class Player extends Entity {
    public final DynamicBody body;
    private final Font healthText;

    public Array<Wave> waves;
    public Array<Bomb> bombs;
    public Array<Xp> xps;
    public Array<Heart> hearts;

    private float timer, timeShooterUpgrade, bombTimer, bombDetination;
    private final Sound shoot;

    public Player(GameScreen gameScreen) {
        super(gameScreen);

        maxHealth = 100;
        health = 100;
        damage = 15;
        speed = 1500f;

        sprite = new Sprite(this.gameScreen.game.assets.get("goldfish.png", Texture.class));

        healthText = new Font(Gdx.files.internal("Minecraft.ttf"), 100, Color.WHITE);

        waves = new Array<>();
        bombs = new Array<>();
        xps = new Array<>();
        hearts = new Array<>();

        shoot = Gdx.audio.newSound(Gdx.files.internal("click.wav"));

        body = new DynamicBody(this.gameScreen.world, new PolygonShape() {{
            setAsBox(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
        }}, 0.1f, 0f, 0f);
        body.getBody().setFixedRotation(true);
        body.getBody().setUserData(sprite);
    }

    @Override
    public Entity create(GameScreen game) {
        return new Player(game);
    }

    public void update() {
        timer += Gdx.graphics.getDeltaTime();
        timeShooterUpgrade += Gdx.graphics.getDeltaTime();
        bombTimer += Gdx.graphics.getDeltaTime();

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

        for (Wave w : waves) {
            w.update();
        }

        for (Bomb b : bombs) {
            b.update();
        }

        if (timeShooterUpgrade >= 1f) {
            waves.add(new Wave(1, sprite.getX(), sprite.getY()));
            waves.add(new Wave(2, sprite.getX(), sprite.getY()));
            waves.add(new Wave(3, sprite.getX(), sprite.getY()));
            waves.add(new Wave(4, sprite.getX(), sprite.getY()));
            timeShooterUpgrade = 0f;
        }

        /*if(bombTimer >= 3f) {
            bombs.add(new Bomb(sprite.getX(), sprite.getY()));
            bombTimer = 0f;
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && timer > 1f) {
            waves.add(new Wave(1, sprite.getX(), sprite.getY()));
            timer = 0f;
            shoot.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && timer > 1f) {
            waves.add(new Wave(2, sprite.getX(), sprite.getY()));
            timer = 0f;
            shoot.play();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && timer > 1f) {
            waves.add(new Wave(3, sprite.getX(), sprite.getY()));
            timer = 0f;
            shoot.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && timer > 1f) {
            waves.add(new Wave(4, sprite.getX(), sprite.getY()));
            timer = 0f;
            shoot.play();
        }

        for (int i = 0; i < xps.size; i++) {
            Xp x = xps.get(i);
            if (sprite.getBoundingRectangle().overlaps(x.sprite.getBoundingRectangle())) {
                gameScreen.upgradeSystem.xp += 3f;
                xps.removeIndex(i);
                i--;
            }
        }

        for (int i = 0; i < hearts.size; i++) {
            Heart h = hearts.get(i);
            if (sprite.getBoundingRectangle().overlaps(h.sprite.getBoundingRectangle())) {
                hearts.removeIndex(i);
                health += 30;
                if (health > maxHealth) {
                    health = maxHealth;
                }
                i--;
            }
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        for (Wave w : waves) {
            w.render(batch);
        }
        for (Bomb b : bombs) {
            b.render(batch);
        }
        for (Xp x : xps) {
            x.render(batch);
        }
        for (Heart h : hearts) {
            h.render(batch);
        }
    }

    public void renderBar() {
        gameScreen.shapeRenderer.setColor(Color.RED);
        gameScreen.shapeRenderer.rect(100f, 500f, health, 50f);
    }

    public void renderText(SpriteBatch batch) {
        healthText.draw(batch, "Health: " + health, 50f, 50f);
    }

    public void isColliding(Entity entity) {

    }
}
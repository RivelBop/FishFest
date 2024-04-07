package com.rivelbop.fishfest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
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
    public Array<SpeedBuff> speedBuffs;

    private float shootCoolDown = 1f;
    private float timer, damageTimer, timeShooterUpgrade, bombTimer, bombDetination, speedTimer;
    public boolean hasTakenDamage, inked, hasSpeed;
    private final Sound shoot;
    public CameraShaker cameraShake;

    public Player(GameScreen gameScreen) {
        super(gameScreen);

        maxHealth = 100;
        health = 100;
        damage = 15;
        speed = 100000f;

        sprite = new Sprite(this.gameScreen.game.assets.get("goldfish.png", Texture.class));
        cameraShake = new CameraShaker(gameScreen.game.camera, 5f, 5f, 10f);
        healthText = new Font(Gdx.files.internal("Minecraft.ttf"), 35, Color.WHITE);

        waves = new Array<>();
        bombs = new Array<>();
        xps = new Array<>();
        hearts = new Array<>();
        speedBuffs = new Array<>();

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
        System.out.println(body.getBody().getLinearVelocity());
        System.out.println(bombs.size);
        timer += Gdx.graphics.getDeltaTime();

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

        for (int i = 0; i < bombs.size; i++) {
            bombs.get(i).update();
            Bomb b = bombs.get(i);
            if(bombs.get(i).exploded && b.bombTimer >= 2f) {
                bombs.removeIndex(i);
                i--;
            }
        }

        if(gameScreen.upgradeSystem.upgrades[2].unlocked) {
            if(gameScreen.upgradeSystem.upgrades[2].level > 5) {
                gameScreen.upgradeSystem.upgrades[2].level = 5;
            }
            shootCoolDown = (100f - 10f * gameScreen.upgradeSystem.upgrades[2].level) / 100f;

            if(shootCoolDown < 0f) {
                shootCoolDown = 0f;
            }
        }

        if(gameScreen.upgradeSystem.upgrades[0].unlocked) {
            timeShooterUpgrade += Gdx.graphics.getDeltaTime();

            int upgrade = gameScreen.upgradeSystem.upgrades[0].level;
            if (timeShooterUpgrade >= shootCoolDown) {
                waves.add(new Wave(gameScreen, 1, sprite.getX(), sprite.getY(), upgrade));
                waves.add(new Wave(gameScreen, 2, sprite.getX(), sprite.getY(), upgrade));
                waves.add(new Wave(gameScreen, 3, sprite.getX(), sprite.getY(), upgrade));
                waves.add(new Wave(gameScreen, 4, sprite.getX(), sprite.getY(), upgrade));
                timeShooterUpgrade = 0f;
                cameraShake.resetAndReconfigure(2f,2f,5f);
                cameraShake.startShaking();
            }
        }

        if(gameScreen.upgradeSystem.upgrades[1].unlocked) {
            bombTimer += Gdx.graphics.getDeltaTime();
            if (bombTimer >= 3f) {
                bombs.add(new Bomb(gameScreen, sprite.getX(), sprite.getY()));
                bombTimer = 0f;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && timer > shootCoolDown) {
            waves.add(new Wave(gameScreen, 1, sprite.getX(), sprite.getY(),
                    gameScreen.upgradeSystem.upgrades[3].unlocked ? gameScreen.upgradeSystem.upgrades[3].level : 0));
            timer = 0f;
            shoot.play();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && timer > shootCoolDown) {
            waves.add(new Wave(gameScreen, 2, sprite.getX(), sprite.getY(),
                    gameScreen.upgradeSystem.upgrades[3].unlocked ? gameScreen.upgradeSystem.upgrades[3].level : 0));
            timer = 0f;
            shoot.play();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && timer > shootCoolDown) {
            waves.add(new Wave(gameScreen, 3, sprite.getX(), sprite.getY(),
                    gameScreen.upgradeSystem.upgrades[3].unlocked ? gameScreen.upgradeSystem.upgrades[3].level : 0));
            timer = 0f;
            shoot.play();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && timer > shootCoolDown) {
            waves.add(new Wave(gameScreen, 4, sprite.getX(), sprite.getY(),
                    gameScreen.upgradeSystem.upgrades[3].unlocked ? gameScreen.upgradeSystem.upgrades[3].level : 0));
            timer = 0f;
            shoot.play();
        }

        for (int i = 0; i < xps.size; i++) {
            Xp x = xps.get(i);
            if (bounds().overlaps(x.sprite.getBoundingRectangle())) {
                gameScreen.upgradeSystem.xp += 15f;
                xps.removeIndex(i);
                i--;
            }
        }

        for (int i = 0; i < hearts.size; i++) {
            Heart h = hearts.get(i);
            if (bounds().overlaps(h.sprite.getBoundingRectangle())) {
                hearts.removeIndex(i);
                health += 30;
                if (health > maxHealth) {
                    health = maxHealth;
                }
                i--;
            }
        }

        for (int i = 0; i < speedBuffs.size; i++) {
            SpeedBuff s = speedBuffs.get(i);
            if (bounds().overlaps(s.sprite.getBoundingRectangle())) {
                speedBuffs.removeIndex(i);
                speed = 150f;
                sprite.setColor(Color.GREEN);
                i--;
                speedTimer +=Gdx.graphics.getDeltaTime();
            }
        }
        if(speedTimer >= 15f) {
            speed = 100f;
            sprite.setColor(Color.WHITE);
            timer = 0f;
        }

        if(!hasTakenDamage) {
            for (Bomb b : bombs) {
                if (b.rect.overlaps(bounds()) && b.exploded) {
                    hasTakenDamage = true;
                    health /= 2;
                }
            }
        }

        if(hasTakenDamage) {
            if(damageTimer == 0f) {
                cameraShake.resetAndReconfigure(5f,5f,10f);
                cameraShake.startShaking();
            }
            damageTimer += Gdx.graphics.getDeltaTime();

            if(damageTimer >= 1f) {
                damageTimer = 0f;
                hasTakenDamage = false;
                speed = 1500f;
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
        for(SpeedBuff s : speedBuffs) {
            s.render(batch);
        }

    }

    public void renderBar() {
        gameScreen.shapeRenderer.setColor(Color.GRAY);
        gameScreen.shapeRenderer.rect(50f, 600f, 500f, 75f);
        gameScreen.shapeRenderer.setColor(Color.RED);
        gameScreen.shapeRenderer.rect(50f, 600f, 500f * ((float) health / maxHealth), 75f);
    }

    public void renderText(SpriteBatch batch) {
        healthText.drawCenter(batch, "Health: " + health, 300f, 665f);
    }

    public void isColliding(Entity entity) {

    }
}
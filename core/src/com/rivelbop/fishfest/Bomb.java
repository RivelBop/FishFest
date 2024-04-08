package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.screen.GameScreen;

public class Bomb {
    private static Texture texture;
    public Sprite sprite;
    public float bombTimer;
    public boolean exploded;
    private GameScreen game;
    public Rectangle rect;
    public Explosion explosion;
    private Sound explosionSound;
    private boolean soundPlayed;

    public Bomb(GameScreen game, float positionx, float positiony) {
        this.game = game;
        if(texture == null) {
            texture = game.game.assets.get("bomb.png", Texture.class);
        }
        sprite = new Sprite(texture);
        sprite.setPosition(positionx, positiony);
        rect = new Rectangle(positionx - 100f / 2f, positiony - 100f / 2f ,100f, 100f);
        explosion = new Explosion(sprite.getX(), sprite.getY());
        explosion.effect.scaleEffect(0.2f);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
    }

    public void update() {
        bombTimer += Gdx.graphics.getDeltaTime();

        if (!exploded && bombTimer >= 1f) {
            exploded = true;
            game.player.cameraShake.resetAndReconfigure(10f,10f,20f);
            game.player.cameraShake.startShaking();
        }
        if(exploded) {
            explosion.effect.update(Gdx.graphics.getDeltaTime());
        }
        if(exploded && !soundPlayed) {
            explosionSound.play();
            soundPlayed = true;
        }


    }

    public void render(SpriteBatch batch) {
        if(!exploded) {
            sprite.draw(batch);
        }
        explosion.effect.draw(batch);

    }
}
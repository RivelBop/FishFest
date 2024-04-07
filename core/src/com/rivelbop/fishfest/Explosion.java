package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.rivelbop.fishfest.screen.GameScreen;

public class Explosion {
        public ParticleEffect effect;
    public Explosion(float positionX, float positionY) {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""));
        effect.setPosition(positionX, positionY);
        effect.start();
    }

    public void render(SpriteBatch batch) {
        effect.draw(batch);
    }

}

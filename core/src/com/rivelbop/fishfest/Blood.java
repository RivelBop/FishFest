package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Blood {

    public ParticleEffect effect;
    public Blood(float positionX, float positionY) {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Blood.p"), Gdx.files.internal(""));
        effect.setPosition(positionX, positionY);
        effect.start();
    }

    public void render(SpriteBatch batch) {
        effect.draw(batch);
    }

}

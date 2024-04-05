package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.rivelbop.fishfest.screen.GameScreen;
import com.rivelbop.rivelworks.math.Interpolator;
import com.rivelbop.rivelworks.ui.Font;

public class DamageText {
    private static final Font font = new Font(Gdx.files.internal("font.ttf"), 12, Color.SKY);
    private final float positionX, positionY;
    private int damage;
    //private Interpolator fadeIn, fadeOut;

    private float timer;
    public boolean finished;

    public DamageText(int damage, float positionX, float positionY) {
        this.damage = damage;
        this.positionX = positionX;
        this.positionY = positionY;
        //fadeIn = new Interpolator(Interpolation.fade, 1f);
        //fadeOut = new I
    }

    public void render(SpriteBatch batch) {
        timer += Gdx.graphics.getDeltaTime();
        if(timer > 0.5f) {
            finished = true;
        }

        if(!finished) {
            font.drawCenter(batch, Integer.toString(damage), positionX, positionY);
        }
    }
}
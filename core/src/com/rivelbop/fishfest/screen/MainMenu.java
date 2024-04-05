package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.rivelworks.math.Interpolator;
import com.rivelbop.rivelworks.ui.Font;

public class MainMenu implements Screen {

    public FishFest game;
    public Font font;
    public SpriteBatch uiBatch;
    public Interpolator fadeOut, fadeIn;
    public Sprite spriteBox;
    boolean spacePressed;

    public MainMenu(FishFest game) {
        this.game = game;
    }


    @Override
    public void show() {
        game.camera.position.setZero();
        game.camera.zoom = 1f;
        uiBatch = new SpriteBatch();

        font = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 50, Color.YELLOW);
        fadeOut = new Interpolator(Interpolation.fade, 1f);
        fadeIn = new Interpolator(Interpolation.fade, 1f);
        spriteBox = new Sprite(game.assets.get("fadeBox.png", Texture.class));
        spriteBox.setAlpha(1f);
        spriteBox.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float v) {
        spriteBox.setAlpha(1f - fadeOut.update());

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && fadeOut.isComplete()) {
            spacePressed = true;
        }

        if(spacePressed) {
            spriteBox.setAlpha(fadeIn.update());
        }

        uiBatch.begin();
        font.drawCenter(uiBatch, "Press Space To Start", 650f, 200f);
        spriteBox.draw(uiBatch);
        uiBatch.end();

        if(fadeIn.isComplete()) {
            game.setScreen(new GameScreen(game));
        }

    }

    @Override
    public void resize(int i, int i1) {
        game.viewport.update(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}

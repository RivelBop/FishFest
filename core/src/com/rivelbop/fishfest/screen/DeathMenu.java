package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.rivelworks.ui.Font;

public class DeathMenu implements Screen {
    public FishFest game;
    public Font header;
    public Font deadText;
    public SpriteBatch uiBatch;

    public DeathMenu(FishFest game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.camera.position.setZero();
        game.camera.zoom = 1f;
        uiBatch = new SpriteBatch();

        header = new Font(Gdx.files.internal("font.ttf"), 30, Color.RED);
        deadText = new Font(Gdx.files.internal("font.ttf"), 50, Color.GREEN);
    }

    @Override
    public void render(float v) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }

        uiBatch.begin();
        header.drawCenter(uiBatch, "You Died!!!", 650f, 700f);
        deadText.drawCenter(uiBatch, "Press Space To Play Again", 650f, 200f);
        uiBatch.end();
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
package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.rivelworks.math.Interpolator;
import com.rivelbop.rivelworks.ui.Font;

public class DeathMenu implements Screen {
    public FishFest game;
    public Font header, deadText;
    public SpriteBatch uiBatch;
    public Sprite spriteBox;
    public Interpolator fadeOut, fadeIn;
    public boolean spacePressed;
    private Sound deathMenu;
    private Sound spaceBar;
    private Sprite background;

    public DeathMenu(FishFest game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.viewport.getCamera().position.set(FishFest.WIDTH / 2f, FishFest.HEIGHT / 2f, 0f);
        ((OrthographicCamera)game.viewport.getCamera()).zoom = 1f;
        game.viewport.getCamera().update();

        uiBatch = new SpriteBatch();
        uiBatch.setProjectionMatrix(game.viewport.getCamera().combined);

        header = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 75, Color.RED);
        deadText = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 50, Color.GREEN);

        fadeOut = new Interpolator(Interpolation.fade, 1f);
        fadeIn = new Interpolator(Interpolation.fade, 1f);

        spriteBox = new Sprite(game.assets.get("fadeBox.png", Texture.class));
        spriteBox.setAlpha(1f);
        spriteBox.setSize(FishFest.WIDTH, FishFest.HEIGHT);

        deathMenu = Gdx.audio.newSound(Gdx.files.internal("deathMenu.wav"));
        deathMenu.play();
        spaceBar = Gdx.audio.newSound(Gdx.files.internal("spacePressed.wav"));
        background = new Sprite(new Texture("backgroundDeathMenu.png"));
        background.setSize(FishFest.WIDTH, FishFest.HEIGHT);
        background.setAlpha(0.3f);
    }

    @Override
    public void render(float v) {
        spriteBox.setAlpha(1f - fadeOut.update());

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && fadeOut.isComplete() && !spacePressed) {
            spacePressed = true;
            spaceBar.play();
        }


        if (spacePressed) {
            spriteBox.setAlpha(fadeIn.update());
        }

        game.viewport.getCamera().update();

        uiBatch.begin();
        background.draw(uiBatch);
        header.drawCenter(uiBatch, "You Died!!!", 650f, 700f);
        deadText.drawCenter(uiBatch, "Press Space To Play Again", 650f, 200f);
        spriteBox.draw(uiBatch);
        uiBatch.end();

        if (fadeIn.isComplete()) {
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
        uiBatch.dispose();
        deathMenu.dispose();
    }
}
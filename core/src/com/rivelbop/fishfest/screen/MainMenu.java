package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Matrix4;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.rivelworks.math.Interpolator;
import com.rivelbop.rivelworks.ui.Font;

public class MainMenu implements Screen {
    public FishFest game;
    public Font font, font1, font2, font3;
    public SpriteBatch uiBatch;
    public Interpolator fadeOut, fadeIn;
    public Sprite spriteBox;
    boolean spacePressed;
    private int highScore;
    private Sound spaceBar;
    private Sprite background;

    public MainMenu(FishFest game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.viewport.getCamera().position.set(FishFest.WIDTH / 2f, FishFest.HEIGHT / 2f, 0f);
        ((OrthographicCamera)game.viewport.getCamera()).zoom = 1f;
        game.viewport.getCamera().update();
        uiBatch = new SpriteBatch();
        uiBatch.setProjectionMatrix(game.viewport.getCamera().combined);

        font = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 50, Color.YELLOW);
        font1 = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 60, Color.BLUE);
        font2 = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 85, Color.LIME);
        font3 = new Font(game.assets.get("font.ttf", FreeTypeFontGenerator.class), 15, Color.WHITE);

        fadeOut = new Interpolator(Interpolation.fade, 1f);
        fadeIn = new Interpolator(Interpolation.fade, 1f);
        spriteBox = new Sprite(game.assets.get("fadeBox.png", Texture.class));
        spriteBox.setAlpha(1f);
        spriteBox.setSize(FishFest.WIDTH, FishFest.HEIGHT);

        spaceBar = Gdx.audio.newSound(Gdx.files.internal("spacePressed.wav"));
        background = new Sprite(new Texture("backgroundMainMenu.png"));
        background.setAlpha(0.3f);

        Preferences save = Gdx.app.getPreferences("FishFestSave");
        highScore = save.getInteger("highscore", 0);
        save.flush();
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
        font3.drawCenter(uiBatch, "Controls: \n" +
                "\n" +
                "WASD- Movement\n" +
                "\n" +
                "Left Arrow key- Shoot left\n" +
                "\n" +
                "Right Arrow Key- Shoot right\n" +
                "\n" +
                "Up Arrow Key- Shoot up\n" +
                "\n" +
                "Down Arrow Key- Shoot down", FishFest.WIDTH / 2f - 420f, FishFest.HEIGHT + 70f);
        font2.drawCenter(uiBatch, "Fish Grade", FishFest.WIDTH / 2f + 200f, FishFest.HEIGHT );
        font1.drawCenter(uiBatch, "Highscore: " + highScore, FishFest.WIDTH / 2f, FishFest.HEIGHT - 400f);
        font.drawCenter(uiBatch, "Press Space To Start", 650f, 200f);
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
        spaceBar.dispose();
    }
}
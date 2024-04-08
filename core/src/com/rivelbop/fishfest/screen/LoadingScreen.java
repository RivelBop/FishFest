package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.rivelworks.graphics2d.ShapeBatch;

public class LoadingScreen implements Screen {
    public FishFest game;
    public AssetManager assets;
    public ShapeBatch shapeBatch;

    public final float BAR_WIDTH = 250f, BAR_HEIGHT = 100f;

    public LoadingScreen(FishFest game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.viewport.getCamera().position.set(FishFest.WIDTH / 2f, FishFest.HEIGHT / 2f, 0f);
        ((OrthographicCamera)game.viewport.getCamera()).zoom = 1f;
        game.viewport.getCamera().update();
        
        shapeBatch = new ShapeBatch();
        shapeBatch.setProjectionMatrix(game.viewport.getCamera().combined);

        assets = game.assets;
        assets.setLoader(
                FreeTypeFontGenerator.class,
                new FreeTypeFontGeneratorLoader(
                        new InternalFileHandleResolver()
                )
        );

        assets.load("goldfish.png", Texture.class);
        assets.load("rock.png", Texture.class);
        assets.load("sharkJaw.png", Texture.class);
        assets.load("EelFish.png", Texture.class);
        assets.load("Lobster.png", Texture.class);
        assets.load("Octopus.png", Texture.class);
        assets.load("MiniFish.png", Texture.class);
        assets.load("Xp.png", Texture.class);
        assets.load("fadeBox.png", Texture.class);
        assets.load("Wave.png", Texture.class);
        assets.load("Heart.png", Texture.class);
        assets.load("bomb.png", Texture.class);
        assets.load("alldir.png", Texture.class);
        assets.load("bombUpgrade.png", Texture.class);
        assets.load("Ink.png", Texture.class);
        assets.load("SpeedBuff.png", Texture.class);
        assets.load("fireRate.png", Texture.class);
        assets.load("bulletSpeed.png", Texture.class);
        assets.load("font.ttf", FreeTypeFontGenerator.class);
    }

    @Override
    public void render(float v) {
        game.viewport.getCamera().update();

        shapeBatch.begin(ShapeRenderer.ShapeType.Filled);

        shapeBatch.setColor(Color.LIGHT_GRAY);
        shapeBatch.rect(0f, 0f, BAR_WIDTH, BAR_HEIGHT);
        shapeBatch.setColor(Color.RED);
        shapeBatch.rect(0f, 0f, BAR_WIDTH * game.assets.getProgress(), BAR_HEIGHT);

        shapeBatch.end();

        if (game.assets.update()) {
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void resize(int i, int i1) {
        game.viewport.update(i, i1);
    }

    @Override
    public void pause() {
        // USELESS
    }

    @Override
    public void resume() {
        // USELESS
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        shapeBatch.dispose();
    }
}
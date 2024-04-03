package com.rivelbop.fishfest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.*;
import com.bitfire.postprocessing.filters.Combine;
import com.bitfire.postprocessing.filters.CrtScreen;
import com.bitfire.utils.ShaderLoader;
import com.rivelbop.fishfest.screen.LoadingScreen;
import com.rivelbop.rivelworks.Utils;

public class FishFest extends Game {
    public static final int HEIGHT = 720, WIDTH = HEIGHT * 16 / 9;

    public StretchViewport viewport;
    public OrthographicCamera camera;
    public AssetManager assets;
    public PostProcessor postProcessor;

    @Override
    public void create() {
        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        camera.update();

        ShaderLoader.BasePath = "shaders/";
        postProcessor = new PostProcessor(false, false, false);

        MotionBlur motionBlur = new MotionBlur();
        motionBlur.setBlurOpacity(0.5f);

        Vignette vignette = new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        vignette.setIntensity(0.75f);

        Bloom bloom = new Bloom(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bloom.setBloomIntesity(0.5f);

        Fxaa fxaa = new Fxaa(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fxaa.setSpanMax(1f);

        int effects = CrtScreen.Effect.TweakContrast.v | CrtScreen.Effect.PhosphorVibrance.v | CrtScreen.Effect.Scanlines.v | CrtScreen.Effect.Tint.v;
        CrtMonitor crt = new CrtMonitor(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false, CrtScreen.RgbMode.RgbShift, effects);
        Combine combine = crt.getCombinePass();
        combine.setSource1Intensity(0f);
        combine.setSource2Intensity(1f);
        combine.setSource1Saturation(0f);
        combine.setSource2Saturation(1f);

        Curvature curvature = new Curvature();
        curvature.setDistortion(0.075f);

        postProcessor.addEffect(motionBlur);
        postProcessor.addEffect(vignette);
        postProcessor.addEffect(bloom);
        postProcessor.addEffect(fxaa);
        postProcessor.addEffect(crt);
        postProcessor.addEffect(curvature);

        assets = new AssetManager();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        Utils.clearScreen2D();

        postProcessor.capture();
        super.render();
        postProcessor.render();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
		assets.dispose();
		postProcessor.dispose();
    }
}
package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.fishfest.system.UpgradeSystem;
import com.rivelbop.fishfest.system.WaveSystem;
import com.rivelbop.fishfest.entity.Player;
import com.rivelbop.rivelworks.map2d.OrthogonalMap;

public class GameScreen implements Screen {
    private final float LERP = 7.5f;
    private Music music;

    public FishFest game;

    public SpriteBatch spriteBatch;
    public Box2DDebugRenderer box2DRenderer;
    public ShapeRenderer shapeRenderer;

    public World world;
    public OrthogonalMap map;

    public UpgradeSystem upgradeSystem;
    public Player player;
    public WaveSystem waveSystem;

    public GameScreen(FishFest game) {
        this.game = game;
    }

    @Override
    public void show() {
        music = Gdx.audio.newMusic(Gdx.files.internal("musicfight.wav"));
        music.play();
        music.setLooping(true);

        game.camera.position.setZero();
        game.camera.zoom = 0.33f;

        spriteBatch = new SpriteBatch();
        box2DRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        world = new World(new Vector2(0f, 0f), true);
        map = new OrthogonalMap("MapforGameJamImproved.tmx");
        map.staticBodyToPhysicsWorld(3, world);

        upgradeSystem = new UpgradeSystem(this);
        player = new Player(this);
        player.body.getBody().setTransform(200f, 200f, 0f);
        waveSystem = new WaveSystem(this);
    }

    @Override
    public void render(float v) {
        if(!upgradeSystem.update()) {
            world.step(1 / 60f, 6, 2);
            player.update();
            waveSystem.update();
        }

        Vector3 position = game.camera.position;
        position.x += (player.centerX() - position.x) * LERP * Gdx.graphics.getDeltaTime();
        position.y += (player.centerY() - position.y) * LERP * Gdx.graphics.getDeltaTime();
        game.camera.update();

        map.render(game.camera);
        box2DRenderer.render(world, game.camera.combined);

        spriteBatch.setProjectionMatrix(game.camera.combined);
        spriteBatch.begin();
        player.renderText(spriteBatch);
        player.render(spriteBatch);
        waveSystem.render(spriteBatch);
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.renderBar();
        shapeRenderer.end();

        upgradeSystem.render();
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
        spriteBatch.dispose();
        box2DRenderer.dispose();
        shapeRenderer.dispose();

        world.dispose();
        map.dispose();

        music.dispose();
    }
}
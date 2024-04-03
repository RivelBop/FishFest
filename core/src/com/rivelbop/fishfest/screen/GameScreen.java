package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.fishfest.entity.Entity;
import com.rivelbop.fishfest.entity.Player;
import com.rivelbop.rivelworks.map2d.OrthogonalMap;

public class GameScreen implements Screen {
    private final float LERP = 7.5f;

    public FishFest game;
    public SpriteBatch spriteBatch;
    public Box2DDebugRenderer renderer;

    public World world;
    public Player player;

    public OrthogonalMap map;

    public GameScreen(FishFest game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.camera.position.setZero();
        game.camera.zoom = 0.33f;
        spriteBatch = new SpriteBatch();

        world = new World(new Vector2(0f, 0f), true);
        player = new Player(this);
        renderer = new Box2DDebugRenderer();

        map = new OrthogonalMap("MapGameJam.tmx");
        map.staticBodyToPhysicsWorld(3, world);
    }

    @Override
    public void render(float v) {
        world.step(1 / 60f, 6, 2);
        player.update();

        Vector3 position = game.camera.position;
        position.x += (player.centerX() - position.x) * LERP * Gdx.graphics.getDeltaTime();
        position.y += (player.centerY() - position.y) * LERP * Gdx.graphics.getDeltaTime();
        game.camera.update();
        
        spriteBatch.setProjectionMatrix(game.camera.combined);
        spriteBatch.begin();
        player.render(spriteBatch);
        spriteBatch.end();

        //map.render(game.camera);
        renderer.render(world, game.camera.combined);
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
        world.dispose();
        map.dispose();
    }
}
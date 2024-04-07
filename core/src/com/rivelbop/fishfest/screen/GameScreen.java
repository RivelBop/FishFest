package com.rivelbop.fishfest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.Bomb;
import com.rivelbop.fishfest.DamageText;
import com.rivelbop.fishfest.FishFest;
import com.rivelbop.fishfest.Wave;
import com.rivelbop.fishfest.entity.Player;
import com.rivelbop.fishfest.system.UpgradeSystem;
import com.rivelbop.fishfest.system.WaveSystem;
import com.rivelbop.rivelworks.graphics2d.ShapeBatch;
import com.rivelbop.rivelworks.map2d.OrthogonalMap;
import com.rivelbop.rivelworks.math.Interpolator;
import com.rivelbop.rivelworks.ui.Font;

public class GameScreen implements Screen {
    private final float LERP = 7.5f;
    private Music music;

    public FishFest game;

    public SpriteBatch spriteBatch, uiBatch;
    public Box2DDebugRenderer box2DRenderer;
    public ShapeBatch shapeRenderer;
    public ShapeBatch testShapeRenderer;

    public World world;
    public OrthogonalMap map;

    public UpgradeSystem upgradeSystem;
    public Player player;
    public WaveSystem waveSystem;
    public Font textWave;

    public Sprite spriteBox;
    public Interpolator fadeOut, fadeIn;
    public boolean isDead;

    public Array<DamageText> damageTexts;

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

        spriteBox = new Sprite(game.assets.get("fadeBox.png", Texture.class));
        spriteBox.setAlpha(1f);
        spriteBox.setSize(FishFest.WIDTH, FishFest.HEIGHT);
        fadeOut = new Interpolator(Interpolation.fade, 1f);
        fadeIn = new Interpolator(Interpolation.fade, 1f);

        spriteBatch = new SpriteBatch();
        uiBatch = new SpriteBatch();
        box2DRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeBatch();
        testShapeRenderer = new ShapeBatch();

        world = new World(new Vector2(0f, 0f), true);
        map = new OrthogonalMap("MapThing.tmx");
        map.staticBodyToPhysicsWorld(6, world);

        upgradeSystem = new UpgradeSystem(this);
        player = new Player(this);
        player.body.getBody().setTransform(200f, 200f, 0f);
        waveSystem = new WaveSystem(this);
        textWave = new Font(Gdx.files.internal("font.ttf"), 50, Color.BLUE);

        damageTexts = new Array<>();
    }

    @Override
    public void render(float v) {
        spriteBox.setAlpha(1f - fadeOut.update());
        if (isDead) {
            spriteBox.setAlpha(fadeIn.update());
        }

        if (!upgradeSystem.update()) {
            world.step(1 / 60f, 6, 2);
            player.update();
            if(player.health <= 0f) {
                isDead = true;
            }
            waveSystem.update();

            for (int i = 0; i < player.waves.size; i++) {
                Wave w = player.waves.get(i);
                if (w.sprite.getX() + w.sprite.getWidth() < game.camera.position.x - FishFest.WIDTH) {
                    player.waves.removeIndex(i);
                    i--;
                    continue;
                }

                if (w.sprite.getX() > game.camera.position.x + FishFest.WIDTH) {
                    player.waves.removeIndex(i);
                    i--;
                    continue;
                }

                if (w.sprite.getY() + w.sprite.getHeight() < game.camera.position.y - FishFest.HEIGHT) {
                    player.waves.removeIndex(i);
                    i--;
                    continue;
                }

                if (w.sprite.getY() > game.camera.position.y + FishFest.HEIGHT) {
                    player.waves.removeIndex(i);
                    i--;
                }
            }
        }

        Vector3 position = game.camera.position;
        position.x += (player.centerX() - position.x) * LERP * Gdx.graphics.getDeltaTime();
        position.y += (player.centerY() - position.y) * LERP * Gdx.graphics.getDeltaTime();
        player.cameraShake.origPosition.set(game.camera.position.x, game.camera.position.y, 0f);
        player.cameraShake.update(Gdx.graphics.getDeltaTime());
        game.camera.update();

        map.render(game.camera);
        box2DRenderer.render(world, game.camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.renderBar();
        shapeRenderer.setColor(0f, 0f, 0.7f, 0.3f);
        shapeRenderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();


        spriteBatch.setProjectionMatrix(game.camera.combined);
        spriteBatch.begin();
        player.render(spriteBatch);
        waveSystem.render(spriteBatch);
        for (DamageText t : damageTexts) {
            t.render(spriteBatch);
        }
        spriteBatch.end();
        
        uiBatch.begin();
        player.renderText(uiBatch);
        textWave.drawCenter(uiBatch, "Wave: " + waveSystem.count, FishFest.WIDTH / 2f, 150f);
        spriteBox.draw(uiBatch);
        uiBatch.end();

        upgradeSystem.render();

        if (fadeIn.isComplete()) {
            game.setScreen(new DeathMenu(game));
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
        spriteBatch.dispose();
        uiBatch.dispose();
        box2DRenderer.dispose();
        shapeRenderer.dispose();

        world.dispose();
        map.dispose();

        music.dispose();

        Preferences save = Gdx.app.getPreferences("FishFestSave");
        if(waveSystem.count > save.getInteger("highscore", 0)) {
            save.putInteger("highscore", waveSystem.count);
        }
        save.flush();
    }
}
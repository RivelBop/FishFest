package com.rivelbop.fishfest.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.entity.*;
import com.rivelbop.fishfest.screen.GameScreen;

import static com.rivelbop.fishfest.FishFest.randomInt;

public class WaveSystem {
    private final Array<Enemy> enemyChoiceList;
    private final GameScreen gameScreen;
    private float spawnTimer;

    public int count, tempCount;
    public Array<Enemy> currentEnemies;

    public WaveSystem(GameScreen game) {
        this.gameScreen = game;
        enemyChoiceList = new Array<Enemy>() {
            {
                add(new MiniFish(gameScreen)); // All waves
                add(new Scorpion(gameScreen)); // Waves 5 and up
                add(new EelFish(gameScreen)); // Waves 10 and up
                add(new Octopus(gameScreen)); // Waves 20 and up
            }
        };

        currentEnemies = new Array<>();
    }

    public void update() {
        if (currentEnemies.isEmpty() && tempCount == count) {
            tempCount = 0;
            count++;
        }

        spawnTimer += Gdx.graphics.getDeltaTime();
        if (timerCheck() && tempCount < count) {
            tempCount++;

            int enemyCount = randomInt(5, 25);
            for (int i = 0; i < enemyCount; i++) {
                Enemy enemy = enemyChoiceList.get(randomInt(0,
                        (count < 5) ? 0 :
                                (count < 10) ? 1 :
                                        (count < 20) ? 2 : 3
                )).create(gameScreen);

                enemy.sprite.setPosition(randomInt(-1000, 1000), randomInt(-1000, 1000));
                currentEnemies.add(enemy);
            }
        }

        // Make this into a regular for loop
        // Update all the enemies
        // After updating check their health and remove them if they are dead
        /*for (Enemy e : currentEnemies) {
            e.update();
        }

         */
        for (int i = 0; i < currentEnemies.size; i++) {
            currentEnemies.get(i).update();
            if (currentEnemies.get(i).health <= 0f) {
                currentEnemies.removeIndex(i);
                i--;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy e : currentEnemies) {
            e.render(batch);
        }
    }

    private boolean timerCheck() {
        if (count < 5 && spawnTimer >= 6f) {
            spawnTimer = 0f;
            return true;
        } else if (count >= 5 && count < 10 && spawnTimer >= 5f) {
            spawnTimer = 0f;
            return true;
        } else if (count >= 10 && count < 20 && spawnTimer >= 4f) {
            spawnTimer = 0f;
            return true;
        } else if (count >= 20 && spawnTimer >= 3f) {
            spawnTimer = 0f;
            return true;
        }
        return false;
    }
}
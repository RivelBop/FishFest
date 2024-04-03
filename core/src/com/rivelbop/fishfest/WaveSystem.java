package com.rivelbop.fishfest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.rivelbop.fishfest.entity.*;
import com.rivelbop.fishfest.screen.GameScreen;

public class WaveSystem {
    private final Array<Entity> enemyChoiceList;
    private GameScreen gameScreen;
    private float spawnTimer;

    public int count, tempCount;
    public Array<Entity> currentEnemies;

    public WaveSystem(GameScreen game) {
        this.gameScreen = game;
        enemyChoiceList = new Array<Entity>() {
            {
                add(new MiniFish(gameScreen)); // All waves
                add(new Crab(gameScreen)); // Waves 5 and up
                add(new EelFish(gameScreen)); // Waves 10 and up
                add(new Octopus(gameScreen)); // Waves 20 and up
            }
        };

        currentEnemies = new Array<>();
    }

    public void update() {
        if(currentEnemies.isEmpty() && tempCount == count) {
            tempCount = 0;
            count++;
        }

        spawnTimer += Gdx.graphics.getDeltaTime();
        if(timerCheck() && tempCount < count) {
            tempCount++;
            // Spawn code here
        }
    }

    private boolean timerCheck() {
        if(count < 5 && spawnTimer >= 6f) {
            spawnTimer = 0f;
            return true;
        } else if(count >= 5 && count < 10 && spawnTimer >= 5f) {
            spawnTimer = 0f;
            return true;
        } else if(count >= 10 && count < 20 && spawnTimer >= 4f) {
            spawnTimer = 0f;
            return true;
        } else if(count >= 20 && spawnTimer >= 3f) {
            spawnTimer = 0f;
            return true;
        }
        return false;
    }
}
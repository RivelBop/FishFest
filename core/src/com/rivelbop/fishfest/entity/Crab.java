package com.rivelbop.fishfest.entity;

import com.rivelbop.fishfest.screen.GameScreen;

public class Crab extends Entity {
    public Crab(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public Crab create(GameScreen game) {
        return new Crab(game);
    }
}

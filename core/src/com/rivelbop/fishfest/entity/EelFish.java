package com.rivelbop.fishfest.entity;

import com.rivelbop.fishfest.screen.GameScreen;

public class EelFish extends Entity {
    public EelFish(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public EelFish create(GameScreen game) {
        return new EelFish(game);
    }
}

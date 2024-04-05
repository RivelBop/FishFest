package com.rivelbop.fishfest.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rivelbop.fishfest.screen.GameScreen;
import com.rivelbop.rivelworks.graphics2d.ShapeBatch;
import com.rivelbop.rivelworks.ui.Font;

import static com.rivelbop.fishfest.FishFest.*;

public class UpgradeSystem {
    private final static Font FONT1 = new Font(Gdx.files.internal("font.ttf"), 64, Color.RED);
    private final static Font FONT2 = new Font(Gdx.files.internal("font.ttf"), 32, Color.CORAL);

    private final static int[] xpLvlUp = {
            100, 125, 175, 325, 400, 500, 550, 620, 750, 835, 900, 950, 1000, 1150, 1325
    };

    private final Upgrade[] upgrades;
    private final int[] choices = {
            -1, -1
    };

    private final float
            GUI_WIDTH = 950f,
            GUI_HEIGHT = 575f;

    private final GameScreen gameScreen;
    private final SpriteBatch spriteBatch;
    private final ShapeBatch shapeBatch;

    private int level = 1, xpLimit;
    private boolean justLeveledUp;
    public int xp;

    public UpgradeSystem(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        spriteBatch = new SpriteBatch();
        shapeBatch = new ShapeBatch();

        upgrades = new Upgrade[]{
                new Upgrade("Surround Waves", new Texture("badlogic.jpg")), // 0
                new Upgrade("Surround Waves", new Texture("tilesetforFishGameJam.png"))
        };
    }

    public boolean update() {
        xpLimit = (level - 1 < xpLvlUp.length) ? xpLvlUp[level - 1] : xpLvlUp[xpLvlUp.length - 1];

        if (xp >= xpLimit) {
            level++;
            xp = 0;
            justLeveledUp = true;
        }

        if (!justLeveledUp && !isEmptyChoices()) {
            for (int i = 0; i < choices.length; i++) {
                choices[i] = -1;
            }
        }

        if (justLeveledUp && isEmptyChoices()) {
            int previousChoice = -1;
            for (int i = 0; i < choices.length; i++) {
                int choice = randomInt(0, upgrades.length - 1);
                while (choice == previousChoice) {
                    choice = randomInt(0, upgrades.length - 1);
                }

                choices[i] = choice;
                previousChoice = choice;
            }
        }

        if (justLeveledUp && !isEmptyChoices()) {

        }

        return justLeveledUp;
    }

    public void render() {
        if (justLeveledUp) {
            shapeBatch.begin(ShapeRenderer.ShapeType.Filled);

            shapeBatch.setColor(0, 0, 255, 0.75f);
            shapeBatch.rect(WIDTH / 2f - GUI_WIDTH / 2f, HEIGHT / 2f - GUI_HEIGHT / 2f,
                    GUI_WIDTH, GUI_HEIGHT);

            shapeBatch.end();
            spriteBatch.begin();

            if (!isEmptyChoices()) {
                for (int i = 0; i < choices.length; i++) {
                    Texture t = upgrades[choices[i]].icon;
                    spriteBatch.draw(upgrades[choices[i]].icon, WIDTH / 2f - t.getWidth(), HEIGHT / 2f - t.getHeight() / 2f);
                }
            }

            FONT1.drawCenter(spriteBatch, "LEVEL UP!", WIDTH / 2f, HEIGHT / 2f + GUI_HEIGHT / 1.75f);
            FONT2.drawCenter(spriteBatch, "lvl" + (level - 1) + " -> lvl" + level, WIDTH / 2f, HEIGHT / 2f + GUI_HEIGHT / 3f);

            spriteBatch.end();
        }
    }

    private boolean isEmptyChoices() {
        for (int i : choices) {
            if (i != -1) {
                return false;
            }
        }
        return true;
    }
}
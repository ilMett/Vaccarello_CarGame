package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DifficultyMenu {
    private Texture easy, normal, impossible;
    private Rectangle easyRec, normalRec, impossibleRec;
    private SpriteBatch batch;

    public DifficultyMenu(String easy, String normal, String impossible) {
        this.easy = new Texture(easy);
        this.normal = new Texture(normal);
        this.impossible = new Texture(impossible);
        this.batch = new SpriteBatch();
        this.easyRec = new Rectangle();
        this.normalRec = new Rectangle();
        this.impossibleRec = new Rectangle();
    }

    public void mostraSceltaDifficolta() {
        batch.begin();
        batch.draw(easy, Gdx.graphics.getWidth() / 2 - easy.getWidth() / 2, 100);
        batch.draw(normal, Gdx.graphics.getWidth() / 2 - normal.getWidth() / 2, 230);
        batch.draw(impossible, Gdx.graphics.getWidth() / 2 - impossible.getWidth() / 2, 360);
        batch.end();
    }

    public boolean easyTouched() {
        easyRec.x = Gdx.graphics.getWidth() / 2;
        easyRec.y = 230;
        easyRec.height = easy.getHeight();
        easyRec.width = easy.getWidth();

        if (Gdx.input.justTouched()) {
            Gdx.app.log("info", "Click X: " + Gdx.input.getX());
            Gdx.app.log("info", "Click Y: " + Gdx.input.getY());
            if (Gdx.input.getX() >= easyRec.width && Gdx.input.getY() >= easyRec.height + 220) {
                Gdx.app.log("info", "Easy cliccato");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean normalTouched() {
        normalRec.x = Gdx.graphics.getWidth() / 2;
        normalRec.y = 230;
        normalRec.height = normal.getHeight();
        normalRec.width = normal.getWidth();
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() >= 0 && Gdx.input.getY() >= 170 && Gdx.input.getY() <= easyRec.height + 220) {
                Gdx.app.log("info", "Normal cliccato");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean impossibleTouched() {
        impossibleRec.x = Gdx.graphics.getWidth() / 2;
        impossibleRec.y = 230;
        impossibleRec.height = impossible.getHeight();
        impossibleRec.width = impossible.getWidth();

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() >= 0 && Gdx.input.getY() <= 169) {
                Gdx.app.log("info", "Impossible cliccato");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void dispose() {
        easy.dispose();
        normal.dispose();
        impossible.dispose();
    }
}

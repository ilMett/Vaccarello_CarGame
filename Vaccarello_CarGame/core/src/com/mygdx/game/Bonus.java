package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import sun.tools.jar.Main;

public class Bonus {
    private Texture bonusImproveDimension, littleCar;
    private SpriteBatch batch;
    private Array<Rectangle> bonusRec;
    private Rectangle littleCarHitBox;
    private long lastDropTime;
    private long spawnTime;
    private boolean stato;
    private Sound powerUp;
    private MainActivity mainActivity;
    private boolean statoMacchina;

    public Bonus(String bonusImproveDimension, String littleCar) {
        this.bonusImproveDimension = new Texture(bonusImproveDimension);
        this.batch = new SpriteBatch();
        this.littleCar = new Texture(littleCar);
        this.lastDropTime = 0;
        this.bonusRec = new Array<Rectangle>();
        this.spawnTime = 9000;
        this.stato = false;
        this.powerUp = Gdx.audio.newSound(Gdx.files.internal("power_up.wav"));
        this.littleCarHitBox = new Rectangle();
        this.statoMacchina = false;
        hitBoxBonus();
    }

    public void spawningBonus(){
        for(Rectangle rectangle: bonusRec) {
            batch.begin();
            batch.draw(bonusImproveDimension, rectangle.x, rectangle.y);
            batch.end();
        }
        if(TimeUtils.nanoTime() - lastDropTime > TimeUtils.millisToNanos(spawnTime)) hitBoxBonus();
        for (Iterator<Rectangle> iter = bonusRec.iterator(); iter.hasNext(); ) {
            Rectangle bonusHitBox = iter.next();
            bonusHitBox.y -= 200 * Gdx.graphics.getDeltaTime();
            if (bonusHitBox.y + littleCar.getWidth() < 0){
                statoMacchina = false;
                iter.remove();
            }
        }
    }

    public boolean collisionDetection(Cars cars){
        stato = false;
        for (Iterator<Rectangle> iter = bonusRec.iterator(); iter.hasNext(); ) {
            Rectangle bonusHitBox = iter.next();
            bonusHitBox.y -= 200 * Gdx.graphics.getDeltaTime();
            if (bonusHitBox.overlaps(cars.getCarHitBox())) {
                cars.setCarHitBox(littleCarHitBox);
                littleCarHitBox();
                powerUp.play();
                cars.setScore(cars.getScore() +20);
                stato = true;
                statoMacchina = true;
                bonusRec.clear();
            }
            return stato;
        }
        return stato;
    }

    public void hitBoxBonus(){
        Rectangle bonusHitBox = new Rectangle();
        bonusHitBox.x = MathUtils.random(0, Gdx.graphics.getWidth()-50);
        bonusHitBox.y = Gdx.graphics.getHeight();
        bonusHitBox.width = bonusImproveDimension.getWidth();
        bonusHitBox.height = bonusImproveDimension.getHeight();
        bonusRec.add(bonusHitBox);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void littleCarHitBox(){
        littleCarHitBox.x = (float) (Gdx.input.getX());
        littleCarHitBox.y = 20;
        littleCarHitBox.height = 50;
        littleCarHitBox.width = littleCar.getWidth() - 50;
    }

    public boolean isStatoMacchina() {
        return statoMacchina;
    }

    public void setStatoMacchina(boolean statoMacchina) {
        this.statoMacchina = statoMacchina;
    }

    public void dispose(){
        bonusImproveDimension.dispose();
    }
}

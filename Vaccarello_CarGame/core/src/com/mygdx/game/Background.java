package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private SpriteBatch batch;
    private Texture backGround1, backGround2, gameOver;
    private float screenHeight;
    private float screenWitdh;
    private Position background;
    private float backGroundvelocity;

    public Background(SpriteBatch batch, String background1, float screenHeight, float screenWitdh, float backGroundvelocity, String gameOver) {
        this.batch = batch;
        this.gameOver = new Texture(gameOver);
        this.backGround1 = new Texture(background1);
        this.backGround2 = new Texture(background1);
        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWitdh = Gdx.graphics.getWidth();
        this.background = new Position(0,0);
        this.backGroundvelocity = backGroundvelocity;
    }

    public void staticBackground(){
        batch.begin();
        batch.draw(backGround1, 0, background.getY(), screenWitdh, screenHeight);
        batch.draw(backGround2,0, background.getY() + screenHeight, screenWitdh, screenHeight);
        batch.end();
    }

    public void gameOver(){
        batch.begin();
        batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() /2 - gameOver.getHeight()/2);
        batch.end();
    }

    public void scrollingBackground(){
        batch.begin();
        batch.draw(backGround1, 0, background.getY(), screenWitdh, screenHeight);
        batch.draw(backGround2, 0, background.getY() + screenHeight, screenWitdh, screenHeight);
        batch.end();

        background.setY(background.getY() - backGroundvelocity);

        if (background.getY() + screenHeight == 0){
            background.setY(0);
        }

    }

    public void dispose(){
        gameOver.dispose();
        backGround1.dispose();
        backGround2.dispose();
    }

}


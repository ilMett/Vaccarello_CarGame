package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class Cars {
    private Texture car, littleCar, enemyCar1, enemyCar2, enemycar3;
    private SpriteBatch batch;
    private Rectangle carHitBox;
    private ArrayList<Texture> enemyCarAryTexture;
    private Array<Rectangle> enemyCarRec;
    private long lastDropTime;
    private int textureGenerate;
    private int carDifficulty;
    private DifficultyMenu difficultyMenu;
    private int score;
    private String yourScoreName;
    private Sound carCrash;
    private BitmapFont yourBitmapFontName;
    private ShapeRenderer shapeRenderer;

    public Cars(String car, String littleCar, String enemyCar1, SpriteBatch batch) {
        this.car = new Texture(car);
        this.littleCar = new Texture(littleCar);
        this.enemyCar1 = new Texture(enemyCar1);
        this.batch = batch;
        this.carHitBox = new Rectangle();
        this.enemyCarRec = new Array<Rectangle>();
        this.enemyCarAryTexture = new ArrayList<Texture>();
        this.textureGenerate = 0;
        this.difficultyMenu = new DifficultyMenu("easy.png", "normal.png", "impossible.png");
        this.score = 0;
        this.yourScoreName = "score: 0";
        this.yourBitmapFontName = new BitmapFont();
        this.carCrash = Gdx.audio.newSound(Gdx.files.internal("crash_sound.mp3"));
        this.shapeRenderer = new ShapeRenderer();
        hitBoxEnemyCar();
    }

    public void riempimentoArray(){
        enemyCarAryTexture.add(enemyCar1);
        textureGenerate = MathUtils.random(0);
    }

    public void spawningFriendlyCar(){
        batch.begin();;
        SpawningHitBox(car);
        batch.draw(car, (float) (Gdx.input.getX() - 45), 20);
        batch.end();
    }

    public void spawningLittleFriendlyCar(){
        batch.begin();;
        SpawningHitBox(littleCar);
        batch.draw(littleCar, (float) (Gdx.input.getX() - 30), 20);
        batch.end();
    }

    public void renderBitMap(){
        batch.begin();
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        yourBitmapFontName.getData().setScale(1.3f);
        yourBitmapFontName.draw(batch, yourScoreName, 5, 20);
        batch.end();
    }

    private void SpawningHitBox(Texture texture) {
        carHitBox.x = (float) (Gdx.input.getX());
        carHitBox.y = 20;
        carHitBox.height = texture.getHeight();
        carHitBox.width = texture.getWidth() - (texture.getWidth() / 2);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(carHitBox.x, carHitBox.y, carHitBox.width, carHitBox.height);
//        shapeRenderer.end();
    }

    public void spawningEnemyCar(){
        riempimentoArray();
        //Provare a fare texture differenti
        for(Rectangle rectangle: enemyCarRec) {
            batch.begin();
            batch.draw(enemyCarAryTexture.get(0), rectangle.x, rectangle.y);
            batch.end();
        }
        if(TimeUtils.nanoTime() - lastDropTime > carDifficulty) hitBoxEnemyCar();
        for (Iterator<Rectangle> iter = enemyCarRec.iterator(); iter.hasNext(); ) {
            Rectangle enemyHitBox = iter.next();
            enemyHitBox.y -= 200 * Gdx.graphics.getDeltaTime();
            if (enemyHitBox.y + car.getWidth() < 0){
                score++;
                yourScoreName = "score: " + score;
                iter.remove();
            }
        }
    }

    public boolean collisionDetection(){
        for (Iterator<Rectangle> iter = enemyCarRec.iterator(); iter.hasNext(); ) {
            Rectangle enemyHitBox = iter.next();
            enemyHitBox.y -= 200 * Gdx.graphics.getDeltaTime();
            if(enemyHitBox.overlaps(carHitBox)) {
                yourScoreName = "score: 0"  ;
                carCrash.play();
                enemyCarRec.clear();
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public void difficultyChanger(){
        if(difficultyMenu.easyTouched()) carDifficulty = 1200000000;
        if(difficultyMenu.normalTouched()) carDifficulty = 750000000;
        if(difficultyMenu.impossibleTouched()) carDifficulty = 300000000;
        score = 0;
    }

    private void hitBoxEnemyCar() {
        Rectangle enemyHitBox = new Rectangle();
        enemyHitBox.x = MathUtils.random(0, Gdx.graphics.getWidth()-64);
        enemyHitBox.y = Gdx.graphics.getHeight();
        enemyHitBox.width = enemyCar1.getWidth();
        enemyHitBox.height = enemyCar1.getHeight();
        enemyCarRec.add(enemyHitBox);
        lastDropTime = TimeUtils.nanoTime();
    }

    public Rectangle getCarHitBox() {
        return carHitBox;
    }

    public void setCarHitBox(Rectangle carHitBox) {
        this.carHitBox = carHitBox;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void dispose(){
        enemyCar1.dispose();
        enemyCar2.dispose();
        car.dispose();
        carCrash.dispose();
    }
}


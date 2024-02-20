package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainActivity extends ApplicationAdapter {
	Background background;
	SpriteBatch batch;
	Cars cars;
	DifficultyMenu difficultyMenu;
	Bonus bonus;
	private int gameState = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cars = new Cars("car.png", "car_dimension.png","enemy_car.png",  batch);
		background = new Background(batch, "roadBG.jpg", Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 4, "game_over.png");
		difficultyMenu = new DifficultyMenu("easy.png", "normal.png", "impossible.png");
		bonus = new Bonus("bonus.png", "car_dimension.png");
	}

	@Override
	public void render () {
		switch (gameState){
			case 0:
				background.staticBackground();
				difficultyMenu.mostraSceltaDifficolta();
				cars.difficultyChanger();
				if (difficultyMenu.easyTouched()) gameState = 1;
				if (difficultyMenu.normalTouched()) gameState = 1;
				if (difficultyMenu.impossibleTouched()) gameState = 1;
				break;
			case 1:
				background.scrollingBackground();
				cars.spawningEnemyCar();
				cars.renderBitMap();
				bonus.spawningBonus();
				bonus.collisionDetection(cars);
				if (bonus.isStatoMacchina()) cars.spawningLittleFriendlyCar();
				else cars.spawningFriendlyCar();
				if (cars.collisionDetection()) gameState = 2;
				break;
			case 2:
				background.staticBackground();
				background.gameOver();
				if (Gdx.input.justTouched()) gameState = 0;
				break;
		}
	}

	@Override
	public void dispose () {
		cars.dispose();
		background.dispose();
		difficultyMenu.dispose();
		bonus.dispose();
	}
}

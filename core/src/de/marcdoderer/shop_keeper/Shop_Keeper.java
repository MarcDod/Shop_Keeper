package de.marcdoderer.shop_keeper;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import de.marcdoderer.shop_keeper.screen.StartScreen;


public class Shop_Keeper extends Game {

	public AssetManager assetManager;
	public static boolean vSync;

	public Shop_Keeper(){
	}



	@Override
	public void create () {
		assetManager = new AssetManager();
		this.setScreen(new StartScreen(this));
	}

}

package de.marcdoderer.shop_keeper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.marcdoderer.shop_keeper.manager.ModManager;
import de.marcdoderer.shop_keeper.screen.StartScreen;


public class Shop_Keeper extends Game {

	public AssetManager assetManager;
	public static boolean vSync;

	public Shop_Keeper(){
	}



	@Override
	public void create () {
		assetManager = new AssetManager();
		ModManager.getModManager(assetManager);
		this.setScreen(new StartScreen(this));
	}

}

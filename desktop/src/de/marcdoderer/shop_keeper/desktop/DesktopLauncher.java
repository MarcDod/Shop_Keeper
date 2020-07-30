package de.marcdoderer.shop_keeper.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import de.marcdoderer.shop_keeper.Shop_Keeper;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//TexturePacker.Settings settings = new TexturePacker.Settings();
		//settings.maxHeight = 4096;
		//settings.maxWidth = 4096;
		//settings.edgePadding = true;
		//settings.duplicatePadding = true;
		//settings.filterMin = Texture.TextureFilter.Nearest;
		//settings.filterMag = Texture.TextureFilter.Nearest;
		//settings.bleed = false;
		//TexturePacker.process(settings, "core/assets/shop/entity/ent", "core/assets/shop/entity/atlas", "shopEntity");
		//TexturePacker.process(settings, "core/assets/npc/npc", "core/assets/npc/atlas", "npc");
		//TexturePacker.process(settings, "core/assets/items/items", "core/assets/items/atlas", "items");
		//TexturePacker.process(settings, "android/assets/shop/background/shop", "android/assets/shop/background/atlas", "shop");

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setResizable(true);
		config.setTitle("Shop_Keeper");
		config.useVsync(true);

		config.setWindowIcon("core/assets/npc/npc/guy0.png");

		config.setWindowedMode(GameState.WIDTH * GameState.SCALE, GameState.HEIGHT * GameState.SCALE);
		new Lwjgl3Application(new Shop_Keeper(), config);
	}
}

package de.marcdoderer.shop_keeper;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.marcdoderer.shop_keeper.Shop_Keeper;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = false;
		config.useWakelock = true;
		config.useImmersiveMode = true;
		initialize(new Shop_Keeper(), config);
	}

}

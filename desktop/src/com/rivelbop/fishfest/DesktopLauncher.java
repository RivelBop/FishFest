package com.rivelbop.fishfest;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.rivelbop.fishfest.FishFest;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(true);
		config.setResizable(false);
		config.setWindowedMode(FishFest.WIDTH, FishFest.HEIGHT);
		config.setTitle("FishFest");
		new Lwjgl3Application(new FishFest(), config);
	}
}

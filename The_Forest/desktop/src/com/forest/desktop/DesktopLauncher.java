package com.forest.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.forest.Forests;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = 1024;
//		config.height = 800;
		config.title = "The_Forest";
		config.addIcon("iconni.png", Files.FileType.Internal);
		new LwjglApplication(new Forests(), config);
	}
}
 
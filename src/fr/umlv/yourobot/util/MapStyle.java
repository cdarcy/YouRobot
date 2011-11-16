package fr.umlv.yourobot.util;

import java.util.ArrayList;

public class MapStyle {
	public static ArrayList<String> background = new ArrayList<>();
	public static ArrayList<String> wall = new ArrayList<>();
	
	public MapStyle() {
		background.add("background_1.png");
		background.add("background_2.png");
		background.add("background_3.png");
		background.add("background_4.png");
		
		wall.add("wall_1.png");
		wall.add("wall_2.png");
		wall.add("wall_3.png");
		wall.add("wall_4.png");
	}
}

package fr.umlv.yourobot.util;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.MenusDrawAPI;

public class KeyControllers {
	private static RobotGameMod player;
	private static RobotTextureMod graphic;
	
	public enum RobotTextureMod{
		TEXTURE,
		GRAPHIC
	}
	public RobotGameMod getModePlayer(){
		return player;
	}
	
	public RobotTextureMod getModeGraphic(){
		return graphic;
	}
	/**
	 *	Default game controller for Yourobot
	 *	Returns a keyController object which defines command for keyboard events
	 * @param keys the set of keys to use to manage events (up/down/left/right/fire)   
	 * @return new KeyController defines commands for the keys passed in parameter
	 */
	public static KeyController getGameController(final RobotWorld world, final HumanRobot e, String keys[]){
		return new KeyController(keys) {	
			@Override
			public void pressKeyUp() {
				e.impulse(world);
			}

			@Override
			public void pressKeyRight() {
				e.rotate(10);
			}

			@Override
			public void pressKeyLeft() {
				e.rotate(-10);
			}

			@Override
			public void pressKeyFire() {
				e.detectBonus(world);
			}

			@Override
			public void pressKeyDown() {	}

			@Override
			public void drawMenu(Graphics2D g) {

			}
		};
	}
	/**
	 *	Default menu controller 
	 *	Returns a keyController object which defines command for keyboard events
	 * @param keys the set of keys to use to manage events (up/down/fire)  
	 * @return new KeyController defines commands for the keys passed in parameter
	 */
	public static KeyController getMenuController(String keys[]){
		return new KeyController(keys){

			@Override
			public void pressKeyUp() {
				MenusDrawAPI.choice1 = RobotGameMod.ONEPLAYER;
			}
			@Override
			public void pressKeyFire() {
				finished = true;
			}
			@Override
			public void pressKeyDown() {
				MenusDrawAPI.choice1 = RobotGameMod.TWOPLAYER;
			}
			@Override
			public void pressKeyRight() { 	 }
			@Override
			public void pressKeyLeft() {	}
			@Override
			public void drawMenu(Graphics2D g) {
				try {
					MenusDrawAPI.menu1(g);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	public static KeyController getGraphicsMenuController(String keys[]){
		return new KeyController(keys){
			@Override
			public void pressKeyUp() {
				MenusDrawAPI.choice2 = RobotTextureMod.TEXTURE;
			}

			@Override
			public void pressKeyDown() {
				MenusDrawAPI.choice2 = RobotTextureMod.GRAPHIC;
			}

			@Override
			public void pressKeyLeft() {
			}

			@Override
			public void pressKeyRight() {		}

			@Override
			public void pressKeyFire() {
				finished = true;
			}

			@Override
			public void drawMenu(Graphics2D g) {
				try {
					MenusDrawAPI.menu2(g);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			

		};
	}

}

package fr.umlv.yourobot.util;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.RobotGame.RobotGameMod;
import fr.umlv.yourobot.RobotGame.RobotTextureMod;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.MenusDrawAPI;
import fr.umlv.zen.KeyboardEvent;


/**
 * @code {@link KeyControllers}
 * Factory of controllers used for game and menus
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
public class KeyControllers {
	private static RobotGameMod player;
	private static RobotTextureMod graphic;


	public static RobotGameMod getModePlayer(){
		return player;
	}

	public static RobotTextureMod getModeGraphic(){
		return graphic;
	}
	/**
	 *	Default game controller for Yourobot
	 *	Returns a keyController object which defines command for keyboard events
	 * @param keys the set of keys to use to manage events (up/down/left/right/fire)   
	 * @return new KeyController defines commands for the keys passed in parameter
	 */
	public static KeyController getGameController(final RobotGame world, final HumanRobot e, String keys[]){
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
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 *	Graphics menu controller 
	 *	Returns a keyController object which defines commands for keyboard events
	 * @param keys the set of keys to use to manage events (up/down/fire)  
	 * @return new KeyController defines commands for the keys passed in parameter
	 */
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


	/**
	 *	Game Over menu controller 
	 *	Returns a keyController object which defines commands for keyboard events
	 * @param keys the set of keys to use to manage events (up/down/fire)  
	 * @return new KeyController defines commands for the keys passed in parameter
	 */
	public static KeyController getMenuGameOverController(String[] k) {
		return new KeyController(k) {

			@Override
			public void pressKeyUp() {
				MenusDrawAPI.choiceEndGame = GameMenu.REPLAY;
			}

			@Override
			public void pressKeyRight() {    }

			@Override
			public void pressKeyLeft() {	}

			@Override
			public void pressKeyFire() {
				finished = true;				
			}

			@Override
			public void pressKeyDown() {
				MenusDrawAPI.choiceEndGame = GameMenu.EXIT;
			}

			@Override
			public void drawMenu(Graphics2D g) {
				try {
					MenusDrawAPI.menu3(g);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 * @code {@link KeyController}
	 * Abstract class defining a keyboard controller and its events listeners
	 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
	 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
	 */
	public abstract static class KeyController {
		private String keyUp;
		private String keyDown;
		private String keyLeft;
		private String keyRight;
		private String keyFire;
		protected boolean finished;

		public KeyController(String keys[]){
			keyUp = keys[0];
			keyDown = keys[1];
			keyLeft = keys[2];
			keyRight = keys[3];
			keyFire = keys[4];
			finished = false;
		}

		public void control(KeyboardEvent event) {
			if(keyUp.equals(event.getKey().name()))
				pressKeyUp();
			else if(keyDown.equals(event.getKey().name()))
				pressKeyDown();
			else if(keyLeft.equals(event.getKey().name()))
				pressKeyLeft();
			else if(keyRight.equals(event.getKey().name()))
				pressKeyRight();
			else if(keyFire.equals(event.getKey().name())){
				pressKeyFire();
			}
		}
		abstract public void pressKeyUp();
		abstract public void pressKeyDown();
		abstract public void pressKeyLeft();
		abstract public void pressKeyRight();
		abstract public void pressKeyFire();
		abstract public void drawMenu(Graphics2D g);
		public boolean isFinished() {
			return finished;
		}

		public enum GameMenu{
			REPLAY,
			EXIT;
		}
	}
}


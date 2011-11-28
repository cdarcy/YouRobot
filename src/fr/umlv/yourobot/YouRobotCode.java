package fr.umlv.yourobot;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.RobotGame.StateGame;
import fr.umlv.yourobot.graphics.MenusDrawAPI;
import fr.umlv.yourobot.util.KeyControllers.KeyController;
import fr.umlv.yourobot.util.KeyControllers;
import fr.umlv.yourobot.util.KeyControllers.KeyController.GameMenu;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;
import fr.umlv.zen.KeyboardEvent;

/**
 * @code {@link YouRobotCode}
 * Defines main procedure of launching YouRobot game.
 * @see {@link ApplicationCode} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
public class YouRobotCode implements ApplicationCode{

	final int WIDTH = 800;
	final int HEIGHT = 600;

	public static boolean loop;
	public static StateGame causeInterruption;
	public final static String[] DEFAULT_KEYBOARDSET = {"UP","DOWN","LEFT","RIGHT", "SPACE"};
	public final static String[] ALT_KEYBOARDSET  = {"Z","S","Q","D","X"};

	private static KeyController graphicController;
	private static KeyController modeController;
	private static KeyController gameOverMenuController;

	private static volatile RobotGame game;
	private static int level = 0;

	/**
	 * Run method of YouRobot game
	 * Displays menus and launches set of games
	 * @see CodeFactory
	 * @see fr.umlv.zen.ApplicationCode#run(fr.umlv.zen.ApplicationContext)
	 */
	@Override
	public void run(final ApplicationContext context) {

		while(true){
			loop = true;
			// Displays menu 1 (game mod choice)
			context.render(CodeFactory.runMenu1());

			// Loop to control menu 1
			while(loop) {
				context.render(CodeFactory.controlMenu1(context));
			}
			// Displays menu 2 (graphic mod choice)
			context.render(CodeFactory.runMenu2());

			// Loop to control menu 2
			loop = true;
			while(loop) {
				context.render(CodeFactory.controlMenu2(context));
			}

			// Loop running a set of 10 games

			for(int i=0;i<=9;i++){

				// Initializing new game
				context.render(CodeFactory.initNewGame());
				loop=true;

				// Game updating loop
				while(loop) {
					context.render(CodeFactory.runGame(game, context));
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
						e1.printStackTrace();	
					}
				}

				// If players died, we show the game-over menu
				if(causeInterruption == StateGame.PLAYERDIED){
					context.render(CodeFactory.runMenuGameOver());	
					loop=true;
					while(loop){
						context.render(CodeFactory.controlMenuGameOver(context));
					}
					if(MenusDrawAPI.choiceEndGame == GameMenu.EXIT)
						break;
				}
			}
		}
	}
	/**
	 * Private inner-class CodeFactory 
	 * Returns render codes to manage displaying and controlling of menus and game
	 * @see YouRobotCode
	 */
	private static class CodeFactory {

		private static ApplicationRenderCode runMenuGameOver() {
			gameOverMenuController  = KeyControllers.getMenuGameOverController(DEFAULT_KEYBOARDSET);

			return new ApplicationRenderCode() {

				@Override
				public void render(Graphics2D graphics) {
					try {
						MenusDrawAPI.menu3(graphics);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}

		private static ApplicationRenderCode controlMenuGameOver(final ApplicationContext context){
			return new ApplicationRenderCode() {
				final KeyboardEvent event = context.pollKeyboard();
				@Override
				public void render(Graphics2D graphics) {
					if(event != null)
						try {
							if(doControlMenuFinished(gameOverMenuController, graphics, event)){
								loop=false;
								graphics.clearRect(0, 0, 800,600);
							}

						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
				}
			};
		}
		private static ApplicationRenderCode initNewGame() {
			return new ApplicationRenderCode() {

				@Override
				public void render(Graphics2D graphics) {
					game = new RobotGame(level , MenusDrawAPI.choice1, MenusDrawAPI.choice2);
					game.init(graphics);
				}
			};
		}


		private static ApplicationRenderCode controlMenu2(final ApplicationContext context) {
			return new ApplicationRenderCode() {
				final KeyboardEvent event = context.pollKeyboard();
				@Override
				public void render(Graphics2D graphics) {
					if(event != null)
						try {
							if(doControlMenuFinished(graphicController, graphics, event)){
								loop=false;
								graphics.clearRect(0, 0, 800,600);
							}

						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
				}
			};
		}


		private static ApplicationRenderCode runMenu2() {
			graphicController  = KeyControllers.getGraphicsMenuController(DEFAULT_KEYBOARDSET);
			return new ApplicationRenderCode() {

				@Override
				public void render(Graphics2D graphics) {

					try {
						MenusDrawAPI.menu2(graphics);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			};
		}


		private static ApplicationRenderCode runGame(final RobotGame game, final ApplicationContext context){
			return new ApplicationRenderCode() {

				@Override
				public void render(Graphics2D graphics) {
					try {
						final KeyboardEvent event = context.pollKeyboard();
						switch (game.updateGame(graphics, event)){
						case WINLEVEL:
							loop = false;
							level++;
							causeInterruption = StateGame.WINLEVEL;
							break;
						case PLAYERDIED:
							causeInterruption = StateGame.PLAYERDIED;
							loop = false;
							break;
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			};
		}

		private static ApplicationRenderCode runMenu1(){
			modeController  = KeyControllers.getGraphicsMenuController(DEFAULT_KEYBOARDSET);

			return new ApplicationRenderCode() {

				@Override
				public void render(Graphics2D graphics) {

					try {
						MenusDrawAPI.menu1(graphics);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}

		private static ApplicationRenderCode controlMenu1(final ApplicationContext context){
			return new ApplicationRenderCode() {

				final KeyboardEvent event = context.pollKeyboard();

				@Override
				public void render(Graphics2D graphics) {

					if(event != null)
						try {
							if(doControlMenuFinished(modeController, graphics, event))
								loop=false;
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
				}
			};
		}

		private static boolean doControlMenuFinished(KeyController k, Graphics2D g, KeyboardEvent event) throws IOException, InterruptedException{
			if (k != null){
				k.drawMenu(g);
				k.control(event);
				k.drawMenu(g);
				if(k.isFinished()){
					k = null;
					return true;
				}
			}
			return false;
		}
	}

}	

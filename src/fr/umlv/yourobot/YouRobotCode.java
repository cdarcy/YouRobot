package fr.umlv.yourobot;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import fr.umlv.yourobot.graphics.MenusDrawAPI;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.yourobot.util.KeyControllers;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;
import fr.umlv.zen.KeyboardEvent;

public class YouRobotCode implements ApplicationCode{

	final Random random = new Random(0);
	final int WIDTH = 800;
	final int HEIGHT = 600;
	private boolean loop = true;
	public final static String[] DEFAULT_KEYBOARDSET = {"UP","DOWN","LEFT","RIGHT", "SPACE"};
	public final static String[] ALT_KEYBOARDSET  = {"Z","S","Q","D","X"};

	KeyController graphicController  = KeyControllers.getGraphicsMenuController(DEFAULT_KEYBOARDSET);
	KeyController modeController  = KeyControllers.getMenuController(DEFAULT_KEYBOARDSET);


	volatile RobotWorld world;
	//HumanRobot player1;
	private int level = 0;


	@Override
	public void run(final ApplicationContext context) {


		// Welcoming interface with game and graphic modes choice
		context.render(new ApplicationRenderCode() {

			@Override
			public void render(Graphics2D graphics) {

				try {
					MenusDrawAPI.menu1(graphics);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		while(loop) {
			final KeyboardEvent event = context.pollKeyboard();
			context.render(new ApplicationRenderCode() {
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
			});
		}
		context.render(new ApplicationRenderCode() {

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

		});

		loop = true;
		while(loop) {
			final KeyboardEvent event = context.pollKeyboard();
			context.render(new ApplicationRenderCode() {
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
			});
		}

		// Defining basic Game Object

		// World game initialisation

		// Game loop. Updates world and manages control.players
		
		for(int i=0;i<20;i++){
			// Defining basic Game Object
			world = new RobotWorld(level , MenusDrawAPI.choice1, MenusDrawAPI.choice2);
			
			context.render(new ApplicationRenderCode() {
				@Override
				public void render(Graphics2D graphics) {
					world.init(graphics);
				}
			});
			loop=true;
			System.out.println(level);
			while(loop) {
				
				context.render(runGame(context));
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();	
				}
			}
		}
	}


	public ApplicationRenderCode runGame(final ApplicationContext context){
		return new ApplicationRenderCode() {

			@Override
			public void render(Graphics2D graphics) {
				try {
					final KeyboardEvent event = context.pollKeyboard();
					if(world.updateGame(graphics, event)){
						loop=false;
						level++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		};
	}

	public boolean doControlMenuFinished(KeyController k, Graphics2D g, KeyboardEvent event) throws IOException, InterruptedException{
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



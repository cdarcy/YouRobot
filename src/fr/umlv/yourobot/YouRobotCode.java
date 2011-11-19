package fr.umlv.yourobot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.bonus.Bomb;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.util.MapGenerator;
import fr.umlv.yourobot.welcome.LoadingGame;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;
import fr.umlv.zen.KeyboardEvent;

public class YouRobotCode implements ApplicationCode{

	final Random random = new Random(0);
	final int WIDTH = 800;
	final int HEIGHT = 600;
	RobotWorld world;
	LoadingGame welcome;


	@Override
	public void run(final ApplicationContext context) {


		// Defining World
		world = new RobotWorld();
		
		
		//welcome page
		context.render(new ApplicationRenderCode() {
			@Override
			public void render(final Graphics2D graphics) {
				try {
					LoadingGame.menu(welcome, graphics);
					world.init(graphics);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		


	// Thread that update player searching (ray-casts) from enemy robot every 5sec
	new Thread(new Runnable() {
		@Override
		public void run() {
			for(;;){

				try {
					world.updateRaycasts();
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}).start();

	
	
	for(;;) {

		/*new Runnable() {

				@Override
				public void run() {
					for(;;){
						world.updateRaycasts();

					}
				}

			}.run();*/

		final KeyboardEvent event = context.pollKeyboard();

		context.render(new ApplicationRenderCode() {

			@Override
			public void render(final Graphics2D graphics) {
				try {
					world.updateGame(graphics, event);
					//world.updateRaycasts();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} 
		});
		try {
			Thread.sleep(20);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}


}

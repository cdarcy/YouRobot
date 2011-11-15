package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;
import fr.umlv.zen.KeyboardEvent;

public class YouRobotCode implements ApplicationCode{

	final Random random = new Random(0);
	final int WIDTH = 800;
	final int HEIGHT = 600;
	final String[] keysP1 = {"UP","DOWN","LEFT","RIGHT"};
	final String[] keysP2 = {"Z","S","Q","D"};
	ComputerRobot e1;
	HumanRobot e2;
	HumanRobot e3;
	Wall w1;
	Bonus b1;
	RobotWorld world;

	@Override
	public void run(ApplicationContext context) {
		// Defining World
		world = new RobotWorld();
		world.setMode(RobotGameMod.TWOPLAYER);
		
		// Defining ComputerRobots
		e1 = new ComputerRobot("Computer", world ,300,300);
		
		// Defining HumanRobots
		e2 = new HumanRobot("Camcam", world, keysP1,500, 300);
		e3 = new HumanRobot("Camcam", world, keysP2,600, 300);

		world.addPlayer(e3);
		world.addPlayer(e2);
		world.addRobot(e1);
		
		context.render(new ApplicationRenderCode() {

			@Override
			public void render(final Graphics2D graphics) {
				// Add borders
				world.addArena(graphics);
				world.putBonus();
				world.putBonus();world.putBonus();
				world.putBonus();world.putBonus();
				world.putBonus();
				try {
					world.draw(graphics);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		// Thread that update player searching (ray-casts) from enemy robot every 5sec
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(;;){
					world.updateRaycasts();	
					try {
						Thread.sleep(5000);
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
					//graphics.setColor(Color.GRAY);
					//graphics.setColor(Color.WHITE);
					graphics.fillRect(Wall.WALL_SIZE, Wall.WALL_SIZE, WIDTH-(2*Wall.WALL_SIZE), HEIGHT-(2*Wall.WALL_SIZE));		
					world.doControl(graphics, event);
					try {
						world.updateGame(graphics);
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

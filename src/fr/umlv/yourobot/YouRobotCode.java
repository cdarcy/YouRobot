package fr.umlv.yourobot;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.bonus.Bomb;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.util.MapGenerator;
import fr.umlv.yourobot.util.MapStyle;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;
import fr.umlv.zen.KeyboardEvent;

public class YouRobotCode implements ApplicationCode{

	final Random random = new Random(0);
	final int WIDTH = 800;
	final int HEIGHT = 600;
	final String[] keysP1 = {"UP","DOWN","LEFT","RIGHT", "SPACE"};
	final String[] keysP2 = {"Z","S","Q","D","X"};
	ComputerRobot r1;
	ComputerRobot r2;
	ComputerRobot r3;
	HumanRobot e1;
	HumanRobot e2;
	Wall w1;
	Bomb b1;
	RobotWorld world;

	@Override
	public void run(ApplicationContext context) {
		// Defining World
		world = new RobotWorld();
		world.setMode(RobotGameMod.TWOPLAYER);
		
		// Defining ComputerRobots
		r1 = new ComputerRobot("Computer", world ,300,300);
		r2 = new ComputerRobot("Computer", world ,300,400);
		r3 = new ComputerRobot("Computer", world ,300,500);
		
		// Defining HumanRobots
		e1 = new HumanRobot("Camcam", world, keysP1,500, 300);
		e2 = new HumanRobot("Camcam", world, keysP2,600, 300);

		world.addPlayer(e1);
		world.addPlayer(e2);
		world.addRobot(r1);
		world.addRobot(r2);
		world.addRobot(r3);
		world.addWall(400, 150);
		context.render(new ApplicationRenderCode() {

			@Override
			public void render(final Graphics2D graphics) {
				// create map
				try {
					MapGenerator.mapRandom(graphics);
					//MapGenerator.drawArena(graphics, MapStyle.wall.get(MapGenerator.value));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				world.putBonus();
				world.putBonus();world.putBonus();
				world.putBonus();world.putBonus();
				world.putBonus();
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
					//graphics.setColor(Color.GRAY);
					//graphics.setColor(Color.WHITE);
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

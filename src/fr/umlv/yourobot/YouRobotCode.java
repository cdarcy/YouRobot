package fr.umlv.yourobot;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import fr.umlv.yourobot.welcome.LoadingGame;
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
	RobotWorld world;
	LoadingGame welcome;


	@Override
	public void run(final ApplicationContext context) {
		// Defining World
		world = new RobotWorld();

		// Welcoming interface with mode choice 

		/*context.render(new ApplicationRenderCode() {
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
		});*/
		// Loop for choosing mode
		/*for(;;) {
			final KeyboardEvent event = context.pollKeyboard();
			context.render(new ApplicationRenderCode() {
				@Override
				public void render(final Graphics2D graphics) {
					//graphics.setColor(Color.GRAY);
					//graphics.setColor(Color.WHITE);
					//world.doControl(graphics, event);
					try {
						world.doControlMenu(graphics, event, world);
						//System.out.println("mode de jeu: "+world.getMode());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 
			});
			try {
				Thread.sleep(20);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (world.getMode() != null)
				break;
		}*/



		context.render(new ApplicationRenderCode(){

			@Override
			public void render(Graphics2D graphics) {
				world.init(graphics);
			}

		});

		// Game loop. Updates world and manages control.
		for(;;) {
			final KeyboardEvent event = context.pollKeyboard();
			context.render(new ApplicationRenderCode() {
				@Override
				public void render(final Graphics2D graphics) {
					try {
						world.updateGame(graphics, event);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} 
			});
			try {
				Thread.sleep(20);
				world.updateRaycasts();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}



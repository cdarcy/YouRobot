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
		// Defining basic World Game
		// Création de l'objet world basique
		world = new RobotWorld();

		// Welcoming interface with game and graphic modes choice
		// C'est ici que je pense que tu dois lancer les deux interfaces de choix utilisateur
		
		
		// World game initialisation
		// C'est ici qu'on passera les paramètres à init() après la fin du choix de mode graphics
		
		context.render(new ApplicationRenderCode(){

			@Override
			public void render(Graphics2D graphics) {
				world.init(graphics);

			}

		});
		
		// Game loop. Updates world and manages control.
		// Et enfin, c'est ici qu'on lance world.updateGame() pour actualiser la partie et capter les events
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
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}



package fr.umlv.yourobot.welcome;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class LoadingGame {
	protected static BufferedImage img;
	private World jboxWorld;
	
	public LoadingGame() {
		jboxWorld = new World(new Vec2(0, 0), true);
		jboxWorld.createBody(new BodyDef());		
	}
	
	
	public static void menu(LoadingGame welcome, Graphics2D g) throws IOException, InterruptedException {
		//MapGenerator background
		drawLoafingPage(g);
	}

	private static void drawLoafingPage(Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/accueil.png"));	
		g.drawImage(img, null, 0, 0);		
	}

}

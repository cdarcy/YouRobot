package fr.umlv.yourobot.welcome;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.util.KeyController;
import fr.umlv.zen.KeyboardEvent;

public class LoadingGame {
	protected static BufferedImage img;
	private World jboxWorld;
	protected static KeyController controller;
	
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
		drawSelectMenuSP(g);
	}

	public static void drawSelectMenuSP(Graphics2D g) throws IOException {
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Single player", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("Multi players", 330, 550);
	}
	
	public static void drawSelectMenuMP(Graphics2D g) throws IOException {
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Single player", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("Multi players", 330, 550);
	}
	
	
}

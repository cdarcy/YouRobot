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

public class LoadingGame {
	protected static BufferedImage img;
	private World jboxWorld;
	protected static KeyController controller;
	
	public LoadingGame() {
		jboxWorld = new World(new Vec2(0, 0), true);
		jboxWorld.createBody(new BodyDef());		
	}
	
	
	public static void menu1(Graphics2D g) throws IOException {
		drawLoadingPage(g);
		drawSelectMenuSP(g);
	}
	
	public static void menu2(Graphics2D g) throws IOException, InterruptedException {
		drawLoadingPage(g);
		drawSelectMenuGT(g);
	}

	private static void drawLoadingPage(Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/accueil.png"));	

		g.drawImage(img, null, 0, 0);		
	}


	public static void drawSelectMenuSP(Graphics2D g) {
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Single player", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("Multi players", 330, 550);
	}
	
	public static void drawSelectMenuMP(Graphics2D g) {
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Single player", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("Multi players", 330, 550);
	}

	public static void drawSelectMenuGT(Graphics2D g) throws IOException {
		drawLoadingPage(g);
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Texture mode", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("Graphic mode", 330, 550);		
	}

	public static void drawSelectMenuGC(Graphics2D g) throws IOException {
		drawLoadingPage(g);
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Texture mode", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("Graphic mode", 330, 550);		
	}

	

}

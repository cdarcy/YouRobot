package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.yourobot.util.KeyControllers.RobotTextureMod;

public class MenusDrawAPI {
	protected static BufferedImage img;
	protected static KeyController controller;
	
	public static RobotGameMod choice1 = RobotGameMod.ONEPLAYER;
	public static RobotTextureMod choice2 = RobotTextureMod.TEXTURE;

	public static void menu1(Graphics2D g) throws IOException {

		if(choice1.name().equals(RobotGameMod.ONEPLAYER.name())){
			drawSelectMenuSP(g);
		}
		else if(choice1 == RobotGameMod.TWOPLAYER){
			drawSelectMenuMP(g);
		}
	}

	public static void menu2(Graphics2D g) throws IOException, InterruptedException {
		drawLoadingPage(g);
		if(choice2 == RobotTextureMod.TEXTURE)
			drawSelectMenuGT(g);
		else if(choice2 == RobotTextureMod.GRAPHIC)
			drawSelectMenuGC(g);
	}

	private static void drawLoadingPage(Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/accueil.png"));	

		g.drawImage(img, null, 0, 0);		
	}


	public static void drawSelectMenuSP(Graphics2D g) {
		try {
			drawLoadingPage(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Single player", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("Multi players", 330, 550);
	}

	public static void drawSelectMenuMP(Graphics2D g) {
		try {
			drawLoadingPage(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		g.drawString("Color mode", 330, 550);		
	}

	public static void drawSelectMenuGC(Graphics2D g) throws IOException {
		drawLoadingPage(g);
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Texture mode", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("Color mode", 330, 550);		
	}

	private static void drawEndPage(Graphics2D g) throws IOException {
		img = ImageIO.read(new File("images/end.png"));	

		g.drawImage(img, null, 0, 0);		
	}
	
	public static void restartGame(Graphics2D g) throws IOException {
		drawEndPage(g);
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Play Again", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("EXIT", 360, 550);
	}

	public static void exitGame(Graphics2D g) throws IOException {
		drawEndPage(g);
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Play Again", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("EXIT", 360, 550);
	}
	

}

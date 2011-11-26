package fr.umlv.yourobot.graphics;

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

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.yourobot.util.KeyControllers.RobotTextureMod;

public class MenusAPI {
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
			// TODO Auto-generated catch block
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

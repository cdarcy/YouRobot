package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.yourobot.RobotGame.RobotGameMod;
import fr.umlv.yourobot.util.KeyControllers.KeyController.GameMenu;
import fr.umlv.yourobot.util.KeyControllers.RobotTextureMod;

public class MenusDrawAPI {
	private static BufferedImage imgLoad;
	private static BufferedImage imgEnd;
	
	public static RobotGameMod choice1 = RobotGameMod.ONEPLAYER;
	public static RobotTextureMod choice2 = RobotTextureMod.TEXTURE;
	public static GameMenu choiceEndGame = GameMenu.REPLAY;

	/**
	 * Draw the first menu page
	 * This metode use two methodes the one for the single player mode and the seconde for the multi player mode
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void menu1(Graphics2D g) throws IOException {
		if(choice1 == RobotGameMod.ONEPLAYER){
			drawSelectMenuSP(g);
		}
		else if(choice1 == RobotGameMod.TWOPLAYER){
			drawSelectMenuMP(g);
		}
	}

	/**
	 * Draw the second menu page
	 * This metode use two methodes the one for the Texture mode and the seconde for the Graphic mode
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void menu2(Graphics2D g) throws IOException, InterruptedException {
		if(choice2 == RobotTextureMod.TEXTURE)
			drawSelectMenuGT(g);
		else if(choice2 == RobotTextureMod.GRAPHIC)
			drawSelectMenuGC(g);
	}
	
	/**
	 * Draw the third menu page
	 * This metode use two methodes the one for the play again mode and the seconde for the exit mode
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void menu3(Graphics2D g) throws IOException, InterruptedException {
		if(choiceEndGame == GameMenu.REPLAY)
			drawSelectRestartGame(g);
		else if(choiceEndGame == GameMenu.EXIT)
			drawSelectExitGame(g);
	}

	/**
	 * Draw the loading page background
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	private static void drawLoadingPage(Graphics2D g) throws IOException {
		if(imgLoad == null)
			imgLoad = ImageIO.read(new File("images/accueil.png"));	
		g.drawImage(imgLoad, null, 0, 0);		
	}

	/**
	 * Draw the game over page
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	private static void drawEndPage(Graphics2D g) throws IOException {
		if(imgEnd == null)
			imgEnd = ImageIO.read(new File("images/end.png"));	
		g.drawImage(imgEnd, null, 0, 0);		
	}

	/**
	 * Draw the choice of player mode
	 * White => single player
	 * Black => multi players
	 * @param g Graphics2D to use the library
	 */
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

	/**
	 * Draw the choice of player mode
	 * White =>  multi players
	 * Black => single player
	 * @param g Graphics2D to use the library
	 */
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

	/**
	 * Draw the choice of texture mode
	 * White =>  texture mode
	 * Black => Graphic mode
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void drawSelectMenuGT(Graphics2D g) throws IOException {
		drawLoadingPage(g);
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Texture mode", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("Color mode", 330, 550);		
	}

	/**
	 * Draw the choice of Graphic mode
	 * White => Graphic mode
	 * Black => Texture mode
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void drawSelectMenuGC(Graphics2D g) throws IOException {
		drawLoadingPage(g);
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Texture mode", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("Color mode", 330, 550);		
	}
	
	/**
	 * Draw the choice of the end page
	 * White => restart level menu
	 * Black => exit : go to the first menu
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	private static void drawSelectRestartGame(Graphics2D g) throws IOException {
		drawEndPage(g);
		g.setColor(Color.WHITE);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Play Again", 330, 450);
		g.setColor(Color.BLACK);
		g.drawString("EXIT", 360, 550);
	}

	/**
	 * Draw the choice of the end page
	 * White => exit : go to the first menu
	 * Black => restart level menu 
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	private static void drawSelectExitGame(Graphics2D g) throws IOException {
		drawEndPage(g);
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 26);
		g.setFont(fonte);
		g.drawString("Play Again", 330, 450);
		g.setColor(Color.WHITE);
		g.drawString("EXIT", 360, 550);
	}
}

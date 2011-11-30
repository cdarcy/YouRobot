package fr.umlv.yourobot;
import fr.umlv.zen.Application;
import fr.umlv.zen.ApplicationCode;

/**
 * Main class of the application, launching the ApplicationCode YouRobotCode
 * @author camille
 * @see {@link YouRobotCode} 
 */
public class Main {

	private static final ApplicationCode CODE = new YouRobotCode();
	private static final String NAME = "YouRobot";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main(String[] args){
		Application.run(NAME, WIDTH, HEIGHT, CODE);

	}
}

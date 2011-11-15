package fr.umlv.yourobot;
import fr.umlv.zen.Application;
import fr.umlv.zen.ApplicationCode;

public class Main {

	static final ApplicationCode CODE = new YouRobotCode();
	static final String NAME = "YouRobot";
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	
	public static void main(String[] args){
		Application.run(NAME, WIDTH, HEIGHT, CODE);
	}
}

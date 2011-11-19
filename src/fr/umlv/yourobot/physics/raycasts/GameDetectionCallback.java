package fr.umlv.yourobot.physics.raycasts;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;

public interface GameDetectionCallback {
	public void raycast(Element elem);
}

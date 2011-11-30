package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.LureRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;


/**
 * @code {@link Lure}
 * Sub bonus simulating lure robot bonus
 * @see {@link Bonus} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public class Lure extends Bonus {
	private LureRobot lureRobot;
	
	public Lure(float x, float y) {
		super(x, y);
		type = ElementType.LURE;
	}
	
	/**
	 * Lure effect. 
	 * Adds a lure robot on the 
		long start = System.nanoTime();map to attract AI robots
	 */
	@Override
	public void run(final RobotGame world, final HumanRobot robot) {
		this.lureRobot = new LureRobot(robot.getPosition().x+20, robot.getPosition().y, this);
		this.world = world;
		world.addStaticElement(lureRobot);
		start = (int) System.nanoTime();
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw("lure.png", g, api);
		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font ("Sans", Font.BOLD, 15));
		gr.drawString(length + "s", (int) bodyElem.getPosition().x, (int)bodyElem.getPosition().y);
		return this;
	}

	/**
	 * Accessor for the time remaining before the end of the effect
	 * @return
	 */
	public int getTimeleft() {
		return timeleft;
	}

	@Override
	public boolean update() {
		
		if(start + ((long)length*1000000000) > System.nanoTime()){
			timeleft = (int) (System.nanoTime()-start)/1000000;
			return true;
		}
		world.removeElement(lureRobot);
		return false;
	}
}

package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;
import fr.umlv.yourobot.util.KeyController;

public class HumanRobot extends Robot {
	private ArrayList<Bonus> bonuslist;	
	public HumanRobot(String pName, RobotWorld world, String[] k, float x, float y) {
		super(pName, world,  x, y);
		bonuslist = new ArrayList<>();
		controller = new KeyController(this, k);
		bodyElem.setUserData(new ElementData(100, ElementType.PLAYER_ROBOT, this));
	}
	
	public void draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/robot.png"));	

		g.drawImage(img, null, (int)pos.x, (int)pos.y);
	}

	public void drawBonusList()  {
		
		for (int i = 0;i < bonuslist.size();  i++){
			System.out.println(bonuslist.get(i));
		}
	}
	public void addBonus(Bonus b) {
		if(b == null || bonuslist.contains(b))
			return;
		bonuslist.add(b);
	}
}

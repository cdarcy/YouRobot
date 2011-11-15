package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;


public class IceWall extends Element {

	public IceWall(RobotWorld world, float x, float y) {
		super(world, x, y);
		
	}

	@Override
	public void draw(Graphics2D g) throws IOException {
		Vec2 pos = bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/iceWall.png"));	
		g.drawImage(img, null, (int)pos.x, (int)pos.y);
	}
	
	

}

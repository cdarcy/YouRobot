package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;


/**
 * @code {@link StoneWall}
 * StoneWall element definition
 * @see {@link Wall} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public class StoneWall extends Wall {

	public StoneWall(float x, float y) {
		super(x, y);
		type = ElementType.STONEWALL;
	}


	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		Vec2 pos = bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/stoneWall.png"));	
		api.drawWall(pos, img, Color.WHITE, g);
		shapeElem.setAsBox(WALL_SIZE, WALL_SIZE);
		return this;
	}
}

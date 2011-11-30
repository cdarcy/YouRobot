package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;

/**
 * @code {@link BorderWall}
 * BorderWall element definition
 * @see {@link Wall} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public class BorderWall extends Wall{
	String fileName;

	public BorderWall(float x, float y, String name) throws IOException {
		super(x, y);
		fileName = name;
		type = ElementType.BORDERWALL;
		
	}
	
	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw(this, fileName, g, api);
		return this;
	}


}

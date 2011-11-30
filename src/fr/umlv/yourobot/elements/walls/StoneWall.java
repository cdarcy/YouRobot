package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.io.IOException;
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
		this.draw(this, "stoneWall.png", g, api);
		return this;
	}
}

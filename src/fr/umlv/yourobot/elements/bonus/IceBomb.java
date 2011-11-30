package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;


/**
 * @code {@link IceBomb}
 * Sub bonus simulating ice magnetic bomb
 * @see {@link Bonus} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public class IceBomb extends Bomb {

	public IceBomb(float x, float y) {
		super(x, y);
		type = ElementType.ICEBOMB;
		typeEffectMax = ElementType.ICEWALL;
	}
	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		return super.draw("icebomb.png", g, api);
	}
}

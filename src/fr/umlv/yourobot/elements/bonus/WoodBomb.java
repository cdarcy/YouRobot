package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementType;

public class WoodBomb extends Bomb{

	public WoodBomb(float x, float y) {
		super(x, y);
		type = ElementType.WOODBOMB;
		typeEffectMax = ElementType.WOODWALL;
	}
	
	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		return super.draw("woodbomb.png", g, api);
	}
	
	@Override
	public ElementType getTypeBomb() {
		return typeEffectMax;
	}
}

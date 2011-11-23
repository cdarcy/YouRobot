package fr.umlv.yourobot.util;
public enum ElementType{
	COMPUTER_ROBOT(ElementClass.ROBOT), 
	PLAYER_ROBOT(ElementClass.ROBOT),
	LURE_ROBOT(ElementClass.ROBOT),
	BOMB(ElementClass.BONUS), 
	SNAP(ElementClass.BONUS), 
	LURE(ElementClass.BONUS),
	WOODWALL(ElementClass.WALL),
	STONEWALL(ElementClass.WALL),
	ICEWALL(ElementClass.WALL),
	BARWALL(ElementClass.WALL),
	BORDERWALL(ElementClass.BLOCK), 
	EFFECT(ElementClass.EFFECT);

	private ElementClass eclass;
	private ElementType(ElementClass c){
		this.eclass = c;
	}

	public ElementClass getEClass(){
		return eclass;
	}
}


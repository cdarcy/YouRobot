package fr.umlv.yourobot.util;



/**
 * @code {@link ElementType}
 * Enumeration defining types of game Elements
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
public enum ElementType{
	COMPUTER_ROBOT(ElementClass.ROBOT), 
	PLAYER_ROBOT(ElementClass.ROBOT),
	LURE_ROBOT(ElementClass.ROBOT),
	SNAP(ElementClass.BONUS), 
	LURE(ElementClass.BONUS),
	WOODWALL(ElementClass.WALL),
	STONEWALL(ElementClass.WALL),
	ICEWALL(ElementClass.WALL),
	BARWALL(ElementClass.WALL),
	BORDERWALL(ElementClass.BLOCK), 
	EFFECT(ElementClass.EFFECT), 
	ICEBOMB(ElementClass.BONUS),
	STONEBOMB(ElementClass.BONUS), 
	WOODBOMB(ElementClass.BONUS), 
	END_CIRCLE(ElementClass.CIRCLE), 
	START_CIRCLE(ElementClass.CIRCLE);

	private ElementClass eclass;
	private ElementType(ElementClass c){
		this.eclass = c;
	}

	public ElementClass getEClass(){
		return eclass;
	}
	
	/**
	 * Inner-enumeration defining category of the element
	 */
	public enum ElementClass{
		ROBOT, // Human and AI robots
		WALL, // Wall group
		START_CIRCLE, // Bonus group
		BLOCK, // For Borderwalls 
		EFFECT,
		CIRCLE, // Circles 
		BONUS;
		
	}


}


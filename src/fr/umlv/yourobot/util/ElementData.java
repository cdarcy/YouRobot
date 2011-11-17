package fr.umlv.yourobot.util;

import fr.umlv.yourobot.elements.Element;

public class ElementData {
	private float life;
	private ElementType type;
	private Element obj;
	
	public enum ElementType{
		COMPUTER_ROBOT(ElementClass.ROBOT), 
		PLAYER_ROBOT(ElementClass.ROBOT), 
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

	public enum ElementClass{
		ROBOT,
		WALL,
		BONUS,
		BLOCK,
		EFFECT
	}

	public ElementData(float life, ElementType type, Element obj){
		this.obj = obj;
		this.life = life;
		this.type = type;
	}

	public ElementData(String pName, float life, ElementType type, Element obj){
		this(life, type, obj);
	}

	public ElementType type(){
		return type;
	}

	public Element getObj(){
		return obj;
	}

	public void setLife(int i) {
		if(life+i>0)
			life += i;
		else 
			// Do die
			;

	}

	public float life() {
		return life;
	}
	
	public ElementClass getElemClass(){
		return type.getEClass();
	}
}

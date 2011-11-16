package fr.umlv.yourobot.util;

import fr.umlv.yourobot.elements.Element;

public class ElementData {
	private float life;
	private ElementType type;
	private Element obj;
	
	public enum ElementType{
		ROBOT, 
		WALL, 
		BONUS, COMPUTER_ROBOT, PLAYER_ROBOT, BOMB, SNAP, LURE
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
}

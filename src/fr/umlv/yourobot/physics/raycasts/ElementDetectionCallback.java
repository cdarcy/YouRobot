package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.dynamics.Fixture;

public class ElementDetectionCallback implements QueryCallback {
	public boolean value=false;
	@Override
	public boolean reportFixture(Fixture arg0) {
		if(arg0 != null){
			value = true;
			return false;
		}
		return true;
	}

	public boolean isDetected(){
		return value;
	}
}

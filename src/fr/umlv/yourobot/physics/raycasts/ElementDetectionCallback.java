package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.dynamics.Fixture;

public class ElementDetectionCallback implements QueryCallback{
	public boolean detected = false;

	@Override
	public boolean reportFixture(Fixture fixture) {
		if(fixture != null)
			detected  = true;
		return false;
	}
	
	public boolean isDetected(){
		return detected;
	}
	
}

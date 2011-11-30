package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.dynamics.Fixture;


/**
 * @code {@link ElementDetectionCallback}
 * Used for element placement on the map to detect if an Element is already placed at the position
 * Implements Jbox2D QueryCallback interface.
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
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

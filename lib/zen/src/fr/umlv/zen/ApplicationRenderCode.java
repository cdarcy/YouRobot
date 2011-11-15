package fr.umlv.zen;

import java.awt.Graphics2D;

/**
 * Interface that should be implemented by the user to provide
 * the code corresponding to a drawing to the drawing area of the application.
 */
public interface ApplicationRenderCode {
  /**
   *  Called by the application, this method should be implemented to
   *  provide the drawing code that the application will render on screen.
   *  
   * @param graphics an abstraction that allows to draw into the drawing area
   *                 of the application.
   */
  public void render(Graphics2D graphics);
}
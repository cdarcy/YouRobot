package fr.umlv.zen;

/**
 * Interface that a should be implemented by the user to provide
 * the code of the application to run.
 * 
 * The method {@link #run(ApplicationContext)} contains the code of the application,
 * the application ends if {@link #run(ApplicationContext)} ends.
 */
public interface ApplicationCode {
  /** This method should be implemented to provide the code of the application.
   * 
   * @param context a context object that allow the code of the application
   *                to render some drawings in the drawing area of the application.
   */
  public void run(ApplicationContext context);
}
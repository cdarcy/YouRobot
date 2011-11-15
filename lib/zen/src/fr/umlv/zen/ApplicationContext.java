package fr.umlv.zen;

/**
 * Provided by the application framework, this object allows
 * to render a graphic code into the drawing area of the application
 * and to wait/ask for keyboard events.
 */
public interface ApplicationContext {
  /** Returns the first keyboard event since or the application was started or
   * {@link #pollKeyboard()} or {@link #waitKeyboard()} was called
   * or null if the user didn't press a key.
   *  
   * @return a keyboard event or null otherwise.
   */
  public KeyboardEvent pollKeyboard();
  
  /** Returns the first keyboard event since or the application was started or
   * {@link #pollKeyboard()} or {@link #waitKeyboard()} was called
   * or if the user didn't press a key, this call wait until a key is pressed
   * or the application thread is interrupted.
   *  
   * @return a keyboard event or null if the application thread is interrupted.
   */
  public KeyboardEvent waitKeyboard();
  
  /** Used to ask the application to render a specific graphic code into the drawing area.
   *  In fact, the drawing code doesn't draw directly into the drawing area
   *  but draw into a back buffer that will be drawn to the screen.
   *  
   * @param rendererCode user object specifying the code that should be executed to drawn
   *                     to the drawing area.
   */
  public void render(ApplicationRenderCode rendererCode);
}
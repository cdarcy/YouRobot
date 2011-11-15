package fr.umlv.zen;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *  Main class of the Zen framework.
 *  Starting a GUI application is as simple as calling {@link #run(String, int, int, ApplicationCode)}. 
 */
public class Application {
  private Application() {
    throw null; // no instance
  }
  
  /** Starts a GUI frame with a drawing area then spawns a new thread that will run the application code.
   *  The application context is provided to the application code as the way for the application code to ask
   *  to render a drawing into the drawing area.
   *  
   *  The render is done in a back buffer and after a render, the GUI frame is notified that
   *  the back buffer has changed and should be displayed on the screen.
   *  Because of this notification mechanism, an application should try to do all the rendering task
   *  in one render instead of try to split it in several calls to render.
   * 
   * @param title title of the GUI frame.
   * @param width width of the drawing area.
   * @param height height of the drawing area.
   * @param applicationCode code of the application to run in the application thread.
   */
  public static void run(final String title, final int width, final int height, final ApplicationCode applicationCode) {
    Objects.requireNonNull(title);
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("invalid size");
    }
    Objects.requireNonNull(applicationCode);
    
    final BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    class Canvas extends JComponent {
      private static final long serialVersionUID = 1360301844144298605L;

      Canvas() {
        setOpaque(true);
        setFocusable(true);
      }
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(width, height); 
      }
      
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
      }
    }
    
    final ArrayBlockingQueue<KeyboardEvent> keyboardEventQueue =
        new ArrayBlockingQueue<>(20);
    
    final Canvas canvas = new Canvas();
    canvas.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        keyboardEventQueue.offer(new KeyboardEvent(KeyboardKey.key(e.getKeyCode()), e.getModifiersEx()));
      }
    });
    
    final Thread thread = new ApplicationContext() {
      final Thread applicationThread;
      {
        final ApplicationContext context = this;
        applicationThread = new Thread(new Runnable() {
          @Override
          public void run() {
            applicationCode.run(context);
          }
        });
      }
      
      private void checkThread() {
        if (Thread.currentThread() != applicationThread) {
          throw new IllegalStateException("try to do something with another thread than the application thread");
        }
      }
      
      @Override
      public KeyboardEvent pollKeyboard() {
        checkThread();
        return keyboardEventQueue.poll();
      }
      
      @Override
      public KeyboardEvent waitKeyboard() {
        checkThread();
        try {
          return keyboardEventQueue.take();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return null;
        }
      }
      
      @Override
      public void render(ApplicationRenderCode rendererCode) {
        checkThread();
        Graphics2D graphics = buffer.createGraphics();
        try {
          rendererCode.render(graphics);
        } finally {
          graphics.dispose();
        }
        
        canvas.repaint();
      }
    }.applicationThread;
    thread.setName("Application-Thread");
    
    try {
      EventQueue.invokeAndWait(new Runnable() {
        @Override
        public void run() {
          JPanel panel = new JPanel();
          panel.add(canvas);
          
          JFrame frame = new JFrame(title);
          frame.setIgnoreRepaint(true);
          frame.setResizable(false);
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.setContentPane(panel);
          frame.setLocationRelativeTo(null);
          frame.pack();
          frame.setVisible(true);
          
          EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              thread.start();
            }
          });
        }
      });
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      if (cause instanceof RuntimeException) {
        throw (RuntimeException)cause;
      }
      if (cause instanceof Error) {
        throw (Error)cause;
      }
      throw new UndeclaredThrowableException((cause == null)? e: cause);
    } catch(InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}

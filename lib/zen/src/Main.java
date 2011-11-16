import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import fr.umlv.zen.Application;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;

public class Main {
  public static void main(String[] args) {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    final int SIZE = 30;
    final int STRIDE = 100;
    
    Application.run("Colors", WIDTH, HEIGHT, new ApplicationCode() {
      @Override
      public void run(ApplicationContext context) {   
        final Random random = new Random(0);
        for(;;) {
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          
          context.render(new ApplicationRenderCode() {
            @Override
            public void render(Graphics2D graphics) {
              for(int i = 0; i < STRIDE; i++) {
                float x = random.nextInt(WIDTH);
                float y = random.nextInt(HEIGHT);

                Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255));
                RadialGradientPaint paint = new RadialGradientPaint(x, y, 100, new float[]{.3f, 1f}, new Color[]{color, Color.WHITE});
                graphics.setPaint(paint);
                graphics.fill(new Ellipse2D.Float(x - SIZE/2, y - SIZE/2, SIZE, SIZE));
              }
            }
          });
        }
      }
    });
  }
}

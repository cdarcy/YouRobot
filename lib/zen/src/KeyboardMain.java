import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

import fr.umlv.zen.Application;
import fr.umlv.zen.ApplicationCode;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.ApplicationRenderCode;
import fr.umlv.zen.KeyboardEvent;

public class KeyboardMain {
  public static void main(String[] args) {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    
    Application.run("Keyboard", WIDTH, HEIGHT, new ApplicationCode() {
      @Override
      public void run(final ApplicationContext context) {
        final Random random = new Random(0);
        final Font font = new Font("arial", Font.BOLD, 30);
        
        for(;;) {
          final KeyboardEvent event = context.waitKeyboard();
          if (event == null) {
            return;
          }
          context.render(new ApplicationRenderCode() {
            @Override
            public void render(Graphics2D graphics) {
              float x = random.nextInt(WIDTH);
              float y = random.nextInt(HEIGHT);

              Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
              graphics.setPaint(color);
              graphics.setFont(font);
              graphics.drawString(event.toString(), x, y);
            }
          });
        }
      } 
    });
  }
}

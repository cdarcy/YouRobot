package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.util.ElementType;

public class Snap  extends Bonus  {
	public int length;
	private ArrayList<Element> elements = new ArrayList<>();
	private ArrayList<Joint> joints = new ArrayList<>();
	public Snap(float x, float y) {
		super(x, y);
		length = (int) Math.round((Math.random()*13)+2);
		type = ElementType.SNAP;
	}

	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/snap.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	@Override
	public ArrayList<Element> run(final RobotWorld world, final HumanRobot robot){
		System.out.println("Raycasting area");

		final float quarter_diagonal = (float) (Math.sqrt(RobotWorld.WIDTH * RobotWorld.WIDTH + RobotWorld.HEIGHT * RobotWorld.HEIGHT) / 4);

		new Thread(new Runnable() {
			
			@Override
			public void run() {
					long start = System.nanoTime();
					while((System.nanoTime()-start)/1000000<(length*1000)){
						for(final Wall wall : world.getWalls()){
							float distance = MathUtils.distance(robot.getPosition(), wall.getPosition());
							if(distance < quarter_diagonal && (elements.indexOf(wall)==-1)){
								
								wall.getBody().setType(BodyType.DYNAMIC);
								System.out.println(wall);
								//wall.getBody().destroyFixture(wall.getFixture());
								wall.getBody().setBullet(true);
								wall.getBody().setTransform(robot.getPosition().add(new Vec2(30,30)), robot.getDirection());
								DistanceJointDef djd = new DistanceJointDef();
								djd.initialize(robot.getBody(), wall.getBody(), new Vec2(0,0), new Vec2(0,20));
								djd.collideConnected = true;
								Joint j = world.getJBoxWorld().createJoint(djd);

								joints.add(j);
								elements.add(wall);
							}
						}
						//System.out.println((float)(System.nanoTime()-start)/1000000+" sur "+(float)length*1000);
					}
					System.out.println(joints);
					clearJoints(world.getJBoxWorld());
				}
		}).start();

		System.out.println("end");

		return null;
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw("snap.png", g, api);
		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font("Sans", Font.BOLD, 15));
		drawTime(gr);
		return this;
	}
	
	public void drawTime(Graphics gr){
		gr.drawString(length+"s", (int) bodyElem.getPosition().x, (int)bodyElem.getPosition().y);
	}
	
	public void clearJoints(World world){
				for(Joint j : joints){
					if(j!=null)
						world.destroyJoint(j);
				}
				for(Element d : elements)
					d.getBody().setType(BodyType.STATIC);
			}

}

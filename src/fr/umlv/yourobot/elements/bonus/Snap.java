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
import org.jbox2d.dynamics.joints.Joint;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType.ElementClass;
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
	public ArrayList<Element> run(final RobotGame world, final HumanRobot robot){
		System.out.println("Raycasting area");
		new Thread(new Runnable() {
			private float quarter_diagonal = (float) (Math.sqrt((RobotGame.WIDTH*RobotGame.WIDTH)+(RobotGame.HEIGHT*RobotGame.HEIGHT))/4);

			@Override
			public void run() {
				long start = System.nanoTime();
				while(((System.nanoTime()-start)/1000000)<(length*1000)){
					final Vec2 pos = robot.getPosition();
					for (Element elem : world.getListByClass(ElementClass.WALL)){

						if (MathUtils.distance(elem.getBody().getPosition(), robot.getBody().getPosition()) > quarter_diagonal) {
							elem.getBody().setType(BodyType.STATIC);
							continue;
						}  
						if(MathUtils.distance(robot.getPosition(), elem.getPosition())>100 && MathUtils.distance(robot.getPosition(), elem.getPosition())<quarter_diagonal){
							elem.getBody().setType(BodyType.DYNAMIC);
							final Vec2 force = pos.sub(elem.getPosition());
							elem.getBody().setLinearVelocity(new Vec2(force.x * 6000, force.y * 6000));
						}
						else if(MathUtils.distance(robot.getPosition(), elem.getPosition())<100){
							elem.getBody().setType(BodyType.DYNAMIC);
							final Vec2 force = elem.getPosition().sub(pos);
							elem.getBody().setLinearVelocity(new Vec2(force.x * 1000, force.y * 1000));
						}
						
					}
					System.out.println((System.nanoTime()-start)/1000000 + "<"+length*1000);
				}
				for (Element elem : world.getWalls()){
					elem.getBody().setType(BodyType.STATIC);
				}
			}
		}).start();
		/*

		final float quarter_diagonal = (float) (Math.sqrt(RobotGame.WIDTH * RobotGame.WIDTH + RobotGame.HEIGHT * RobotGame.HEIGHT) / 4);

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
		 */
		System.out.println("end");

		return null;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
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

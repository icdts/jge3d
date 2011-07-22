package test;

import javax.vecmath.Vector3f;

import engine.Engine;
import engine.entity.*;
import engine.importing.FileLoader;

import com.bulletphysics.collision.shapes.BoxShape;

public class Main {

	private Engine engine;

	private Player player;
	private Entity model;
	private Entity model2;
	private Entity model3;
	private Camera camera;

	public static void main(String args[]) {
		Main m = new Main();
		m.run();
	}

	public Main() {
		engine = new Engine();

		player = new Player(1.0f, 0.5f);
		player.setModel(FileLoader.loadFile("resources/models/misc/box.xgl"));
		player.setProperty(Entity.NAME, "player");
		player.setPosition(new Vector3f(0, 0, 2));
		
		model = new Entity(1f, true);
		model.setModel(FileLoader.loadFile("resources/models/misc/test.xgl"));
		model.setProperty(Entity.NAME, "model1");
		model.setPosition(new Vector3f(4, 0, 0));
		//model.applyImpulse(new Vector3f(-2,0,0), new Vector3f(0,0,0));

		model2 = new Entity(1f, true);
		model2.setModel(FileLoader.loadFile("resources/models/misc/box2.xgl"));
		model2.setProperty(Entity.NAME, "model2");
		model2.setPosition(new Vector3f(-4, 0, 0));
		model2.applyImpulse(new Vector3f(2,0,0), new Vector3f(0,0,0));
		
		model3 = new Entity(1f, false);
		model3.setModel(FileLoader.loadFile("resources/models/misc/box.xgl"));
		model3.setProperty(Entity.NAME, "model3");
		model3.setPosition(new Vector3f(0, 0, 0));
		//model3.applyImpulse(new Vector3f(-2,0,0), new Vector3f(-2,0,0));
	
		camera = new Camera(1f, new BoxShape(new Vector3f(1, 1, 1)), false, player);
		camera.setProperty(Entity.NAME, Camera.CAMERA_NAME);
		camera.setDistance(20f);
		camera.setPosition(new Vector3f(0, 0, 5));

		engine.addEntity(model);
		engine.addEntity(model2);
		engine.addEntity(model3);
		engine.addEntity(camera);
		engine.addEntity(player);
		
		camera.focusOn(player);
	}

	public void run() {
		engine.run();
	}
}

package engine.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import engine.physics.Physics;

public class EntityList {
	private HashMap<Object, Entity> entities;
	private Physics physics;

	// private HashMap<String,TypedConstraint> constraints;

	private ConcurrentLinkedQueue<QueueItem> physicsQueue;
	private ConcurrentLinkedQueue<QueueItem> renderQueue;

	public EntityList(Physics physics) {
		entities = new HashMap<Object, Entity>();
		this.physics = physics;

		physicsQueue = new ConcurrentLinkedQueue<QueueItem>();
		renderQueue = new ConcurrentLinkedQueue<QueueItem>();
	}

	/*************** QUEUE METHODS ***************/
	public void parsePhysicsQueue() {
		Object[] itemArray = physicsQueue.toArray();
		for (Object item : itemArray) {
			if (QueueItem.ADD == ((QueueItem) item).getAction()) {
				addPhysicsItem(((QueueItem) item).getEnt());
			} else if (QueueItem.REMOVE == ((QueueItem) item).getAction()) {
				removePhysicsItem(((QueueItem) item).getEnt());
			}
			physicsQueue.remove(item);
		}
	}
	public void parseRenderQueue() {
		Object[] itemArray = renderQueue.toArray();
		for (Object item : itemArray) {
			if (QueueItem.ADD == ((QueueItem) item).getAction()) {
				addRenderItem(((QueueItem) item).getEnt());
			} else if (QueueItem.REMOVE == ((QueueItem) item).getAction()) {
				removeRenderItem(((QueueItem) item).getEnt());
			}

			renderQueue.remove(item);
		}
	}
	
	public void addEntity(Entity ent) {
		entities.put(ent.getProperty("name"), ent);
		entities.put(ent.collision_object, ent);
		physicsQueue.add(new QueueItem(ent, QueueItem.ADD));
		renderQueue.add(new QueueItem(ent, QueueItem.ADD));
	}
	public void removeEntity(Object key) {
		if (entities.containsKey(key)) {
			Entity ent = (entities.get(key));

			entities.remove(ent.getProperty(Entity.NAME));
			entities.remove(ent.collision_object);
			physicsQueue.add(new QueueItem(ent, QueueItem.REMOVE));
			renderQueue.add(new QueueItem(ent, QueueItem.REMOVE));
		}
	}
	
	public void updateEntity(Entity ent) {
		renderQueue.add(new QueueItem(ent, QueueItem.ADD));
	}
	
	public int physicsQueueSize() {
		return physicsQueue.size();
	}
	public int renderQueueSize() {
		return renderQueue.size();
	}
	/**********************************************/
	
	/*************** ACCESSORS ***************/
	public Entity getItem(Object key) {
		return entities.get(key);
	}
	
	public int entityCount() {
		return this.getEntities().size();
	}

	public Set<Object> getKeySet() {
		return entities.keySet();
	}
	
	public ArrayList<Entity> getEntities() {
		Set<Entity> ents = new HashSet<Entity>();
		for(Entity e : entities.values()){
			ents.add(e);
		}
		return new ArrayList<Entity>(ents);
	}
	
	public Physics getPhysics() {
		return physics;
	}
	/******************************************/	

	/*************** MUTATORS ***************/
	// Add an item to the entity List
	private void addRenderItem(Entity e) {
		if (e.keyExists("name")) {
			if (e.getModel() != null) {
				e.getModel().createVBO();
				e.getModel().reduceHull();
			}
		}
	}
	private void removeRenderItem(Entity e) {
		if (e.keyExists("name")) {
			if (e.getModel() != null) {
				e.getModel().destroyVBO();
			}
		}
	}
	
	private void addPhysicsItem(Entity entity) {
		physics.addEntity(entity);
	}
	private void removePhysicsItem(Entity entity) {
		physics.removeEntity(entity);
	}
	/*****************************************/

	/*************** DRAWING ***************/
	public void drawList() {
		// Have to change keySet into array so that a clone will be made
		// Avoids concurrency issues
		for (Entity ent : this.getEntities()){
			ent.draw();
		}
	}
	/***************************************/
	/* Physics Constraints */
	/*
	 * public void addBallJoint(String name, Entity object1, Vector3f point1,
	 * Entity object2, Vector3f point2){ //Setup a Ball joint between the two
	 * objects, at the point given Point2PointConstraint ballJoint = new Point2P
	 */
	 /*
	 * pointConstraint( object1, object2, point1, point2 );
	 * physics.getDynamicsWorld().addConstraint(ballJoint); } public void
	 * removeJoint(String constraint_name){ if(
	 * constraints.containsKey(constraint_name) ){
	 * physics.getDynamicsWorld().removeConstraint
	 * (constraints.get(constraint_name)); constraints.remove(constraint_name);
	 * } }
	 */
}
package test;

import java.util.HashMap;

import javax.vecmath.Vector3f;

import com.bulletphysics.BulletGlobals;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CapsuleShapeX;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConeTwistConstraint;
import com.bulletphysics.dynamics.constraintsolver.TypedConstraint;
import com.bulletphysics.linearmath.Transform;

import engine.entity.Entity;
import engine.render.Model;
import engine.render.Shader;
import engine.render.primitives.Box;

public class RagDoll extends Entity {
    private Shader shader;
    private Vector3f position;
    
	public RagDoll(float mass, boolean collide, Vector3f location, Model model, Shader shader) {
		setProperty(Entity.COLLIDABLE, false);
		setProperty(Entity.SHOULD_DRAW, false);
		setProperty(Entity.POSITION, location);
		position = location;
		this.shader = shader;
		createRagDoll();
	}

	private void createRagDoll() {
    	this.addSubEntity("shoulders",createLimb("shoulders", 1.0f, 0.2f, 1.0f, new Vector3f(0.00f,  1.5f, 0), true));
    	this.addSubEntity("uArmL",	  createLimb("uArmL",     1.0f, 0.2f, 0.5f, new Vector3f(-0.75f, 0.8f, 0), false));
    	this.addSubEntity("uArmR",	  createLimb("uArmR",     1.0f, 0.2f, 0.5f, new Vector3f(0.75f,  0.8f, 0), false));
    	this.addSubEntity("lArmL",	  createLimb("lArmL",     1.0f, 0.2f, 0.5f, new Vector3f(-0.75f,-0.2f, 0), false));
    	this.addSubEntity("lArmR",	  createLimb("lArmR",	  1.0f, 0.2f, 0.5f, new Vector3f(0.75f, -0.2f, 0), false));
    	this.addSubEntity("body", 	  createLimb("body",	  1.0f, 0.2f, 1.0f, new Vector3f(0.00f,  0.5f, 0), false));
    	this.addSubEntity("hips", 	  createLimb("hips",      1.0f, 0.2f, 0.5f, new Vector3f(0.00f, -0.5f, 0), true));
    	this.addSubEntity("uLegL",	  createLimb("uLegL",  	  1.0f, 0.2f, 0.5f, new Vector3f(-0.25f,-1.2f, 0), false));
    	this.addSubEntity("uLegR",	  createLimb("uLegR",	  1.0f, 0.2f, 0.5f, new Vector3f(0.25f, -1.2f, 0), false));
    	this.addSubEntity("lLegL",	  createLimb("lLegL",     1.0f, 0.2f, 0.5f, new Vector3f(-0.25f,-2.2f, 0), false));
    	this.addSubEntity("lLegR",	  createLimb("lLegR",     1.0f, 0.2f, 0.5f, new Vector3f(0.25f, -2.2f, 0), false));
        this.addSubEntity("head",     createLimb("head",      1.0f, 0.3f, 0.1f, new Vector3f(0.00f,  2.0f, 0), false));
    	
    	constraints.putAll(join(subEntities.getItem("body"), subEntities.getItem("shoulders"), new Vector3f(0f, 1.4f, 0)));
        constraints.putAll(join(subEntities.getItem("body"), subEntities.getItem("hips"), new Vector3f(0f, -0.5f, 0)));

        constraints.putAll(join(subEntities.getItem("uArmL"), subEntities.getItem("shoulders"), new Vector3f(-0.75f, 1.4f, 0)));
        constraints.putAll(join(subEntities.getItem("uArmR"), subEntities.getItem("shoulders"), new Vector3f(0.75f, 1.4f, 0)));
        constraints.putAll(join(subEntities.getItem("uArmL"), subEntities.getItem("lArmL"), new Vector3f(-0.75f, .4f, 0)));
        constraints.putAll(join(subEntities.getItem("uArmR"), subEntities.getItem("lArmR"), new Vector3f(0.75f, .4f, 0)));

        constraints.putAll(join(subEntities.getItem("uLegL"), subEntities.getItem("body"), new Vector3f(-.25f, -0.5f, 0)));
        constraints.putAll(join(subEntities.getItem("uLegR"), subEntities.getItem("body"), new Vector3f(.25f, -0.5f, 0)));
        constraints.putAll(join(subEntities.getItem("uLegL"), subEntities.getItem("lLegL"), new Vector3f(-.25f, -1.7f, 0)));
        constraints.putAll(join(subEntities.getItem("uLegR"), subEntities.getItem("lLegR"), new Vector3f(.25f, -1.7f, 0)));
        
        constraints.putAll(join(subEntities.getItem("shoulders"), subEntities.getItem("head"), new Vector3f(0, 2.0f, 0)));
        
        setProperty(Entity.CONSTRAINTS, constraints);
    }

    private Entity createLimb(String name, float mass, float width, float height, Vector3f location, boolean rotate) {
    	RigidBody node;
    	Box box;
    	mass=1;
        int axis = rotate ? 0 : 1;
        switch(axis) {
        	case 0: //X axis
        		node = createRigidBody(mass,  new CapsuleShapeX(width, height));
    			box = new Box(
        			mass,
        			true,
        			new Vector3f(
    					height+2*width, 
    					width, 
        				width
        			),
        			shader
        		);
    			break;
        	case 1: //Y axis 
        		 node = createRigidBody(mass,  new CapsuleShape(width, height));
        		 box = new Box(
        			mass,
        			true,
        			new Vector3f(
    					width, 
    					height+2*width, 
    					width
        			),
        			shader
        		);
        		break;
        	default: 
        		node = createRigidBody(mass,  new CapsuleShape(width, height));
        		box = new Box(
        			mass,
        			true,
        			new Vector3f(
        				width, 
        				height+2*width, 
        				width
        			),
        			shader
        		);
        		break;
        }
       
    	Entity ent = new Entity(
    		name, 
    		mass, 
    		true, 
    		(Model)box.getProperty(Entity.MODEL),
    		shader
    	);
    	
    	Vector3f adjusted_pos = new Vector3f(position);
    	adjusted_pos.add(location);
    	
    	ent.setProperty(Entity.POSITION, adjusted_pos);
    	ent.setProperty(Entity.COLLISION_OBJECT, node);
    	//ent.setProperty(Entity.GRAVITY, new Vector3f(0,-10f,0));
    	    	
        return ent;
    }

    private  HashMap<String, TypedConstraint> join(Entity A, Entity B, Vector3f connectionPoint) {
    	Vector3f adjusted_posA = new Vector3f((Vector3f)A.getProperty(Entity.POSITION));
    	Vector3f adjusted_posB = new Vector3f((Vector3f)B.getProperty(Entity.POSITION));
    	Transform posA = new Transform();
    	Transform posB = new Transform();
    	
    	adjusted_posA.sub(position);
    	adjusted_posB.sub(position);
    	
    	posA.setIdentity();
    	posB.setIdentity();
    	
    	posA.origin.set(adjusted_posA);
    	posB.origin.set(adjusted_posB);
    	
    	posA.invXform(connectionPoint, posA.origin);
    	posB.invXform(connectionPoint, posB.origin);

    	System.out.println(A.getProperty(Entity.NAME)+":"+B.getProperty(Entity.NAME)+"|"+posA.origin+":"+posB.origin);
    	
    	ConeTwistConstraint joint = new ConeTwistConstraint(
        	(RigidBody)A.getProperty(Entity.COLLISION_OBJECT), 
        	(RigidBody)B.getProperty(Entity.COLLISION_OBJECT), 
        	posA, 
        	posB      	
        );
        
    	//joint.setLimit(1, 1, 0);
    	/*
        Generic6DofConstraint joint = new Generic6DofConstraint(
        	(RigidBody)A.getProperty(Entity.COLLISION_OBJECT), 
        	(RigidBody)B.getProperty(Entity.COLLISION_OBJECT), 
        	posA, 
        	posB,
        	true        	
        );
        */
        /*
        joint.getTranslationalLimitMotor().limitSoftness = 0.1f;
        joint.getTranslationalLimitMotor().damping = 0.1f;
		joint.getTranslationalLimitMotor().restitution = 2.0f;
        */
        
        joint.setLimit(0, -BulletGlobals.SIMD_EPSILON, BulletGlobals.SIMD_EPSILON);
        joint.setLimit(1, -BulletGlobals.SIMD_EPSILON, BulletGlobals.SIMD_EPSILON);
        joint.setLimit(2, -BulletGlobals.SIMD_EPSILON, BulletGlobals.SIMD_EPSILON);
        joint.setLimit(3, -BulletGlobals.SIMD_PI, BulletGlobals.SIMD_PI);
        joint.setLimit(4, -BulletGlobals.SIMD_PI, BulletGlobals.SIMD_PI);
        joint.setLimit(5, -BulletGlobals.SIMD_PI, BulletGlobals.SIMD_PI);
        
        HashMap<String, TypedConstraint> join = new HashMap<String, TypedConstraint>();
        join.put(A.getProperty(Entity.NAME)+":"+B.getProperty(Entity.NAME),joint);
        return join;
    }
/*
    @Override
    public void update(float tpf) {
        if (applyForce) {
            shoulders.getControl(RigidBodyControl.class).applyForce(upforce, Vector3f.ZERO);
        }
    }
*/
}

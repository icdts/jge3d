package engine.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.vecmath.Vector4f;

import org.lwjgl.BufferUtils;

import engine.render.UBO.Type;

public class Material implements UBOInterface {
    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;
    private float shininess;
    private float alpha;
    
    private static final int size = 14;
    private static final String name = "material";
    private static final String names[] = {
	    "Material.ambient",
	    "Material.diffuse",
	    "Material.specular",
	    "Material.shininess",
	    "Material.alpha"
    };
    
    public Material() {
        ambient = new Vector4f(0.5f,0.5f,0.5f,0.5f);
    	diffuse = new Vector4f(0.5f,0.5f,0.5f,0.5f);
    	specular = new Vector4f(0.5f,0.5f,0.5f,0.5f);
    	shininess = 0.5f;
    	alpha = 1.0f;
    }
    
    public Material(Material material) {
	    this.ambient=material.ambient;
		this.diffuse=material.diffuse;
		this.specular=material.specular;
		this.shininess=material.shininess;
		this.alpha=material.alpha;
	}
    
	public Material(
    	Vector4f ambient,
    	Vector4f diffuse,
    	Vector4f specular,
    	float shininess,
    	float alpha) {
			this.ambient=ambient;
			this.diffuse=diffuse;
			this.specular=specular;
			this.shininess=shininess;
			this.alpha=alpha;
	}
	
	public FloatBuffer createBuffer() {
		float material_buffer[] = new float[size];

		//position
		material_buffer[0] = ambient.x;
		material_buffer[1] = ambient.y;
		material_buffer[2] = ambient.z;
		material_buffer[3] = ambient.w;

		//ambient
		material_buffer[4] = diffuse.x;
		material_buffer[5] = diffuse.y;
		material_buffer[6] = diffuse.z;
		material_buffer[7] = diffuse.w;

		//diffuse
		material_buffer[8] = specular.x;
		material_buffer[9] = specular.y;
		material_buffer[10] = specular.z;
		material_buffer[11] = specular.w;
		
		//specular
		material_buffer[12] = shininess;
		
		//specular
		material_buffer[13] = alpha;
	    
		return FloatBuffer.wrap(material_buffer);
	}
	
	public int getSize() {
		return size;
	}
	
	public static String[] getNames() {
		return names;
	}
	
	public ByteBuffer getNamesAsBuffer() {
		String name_buffer = new String();
		for(String name: names) {
			name_buffer += name;
		} 
		return ByteBuffer.wrap(name_buffer.getBytes());
	}
	
	public IntBuffer getIndices() {
		return BufferUtils.createIntBuffer(size);
	}
	
	public Type getType() {
		return Type.MATERIAL;
	}
	
	public String getName() {
		return name;
	}
	
	public StringBuffer toXGLString(int ref) {
		StringBuffer data = new StringBuffer();
		data.append("<MAT ID=\"" + ref + "\">\n");
		data.append(
			"<AMB>" + String.valueOf(ambient.x) + ", "
			+ String.valueOf(ambient.y) + ", "
			+ String.valueOf(ambient.z) + "</AMB>\n"
		);
		data.append(
			"<DIFF>" + String.valueOf(diffuse.x) + ", "
			+ String.valueOf(diffuse.y) + ", "
			+ String.valueOf(diffuse.z) + "</DIFF>\n"
		);
		data.append(
			"<SPEC>" + String.valueOf(specular.x) + ", "
			+ String.valueOf(specular.y) + ", "
			+ String.valueOf(specular.z) + "</SPEC>\n"
		);
		/*
		data.append(
			"<EMISS>" + String.valueOf(emission.x) + ", "
			+ String.valueOf(emission.y) + ", " + String.valueOf(emission.z)
			+ "</EMISS>\n"
		);
		*/
		data.append("<SHINE>" + String.valueOf(shininess) + "</SHINE>\n");
		data.append("<ALPHA>" + String.valueOf(alpha) + "</ALPHA>\n");
		data.append("</MAT>\n");
		return data;
	}
	
    public Vector4f getAmbient() {
    	return ambient;
    }
    
    public Vector4f getDiffuse() {
    	return diffuse;
    }
    
    public Vector4f getSpecular() {
    	return specular;
    }
    
    public float getShininess() {
    	return shininess;
    }
    
    public float getAlpha() {
    	return alpha;
    }
    
    public void setAmbient(Vector4f ambient) {
    	this.ambient=ambient;
    }
    
    public void setDiffuse(Vector4f diffuse) {
    	this.diffuse=diffuse;
    }
    
    public void setSpecular(Vector4f specular) {
    	this.specular=specular;
    }
    
    public void setShininess(float shininess) {
    	this.shininess=shininess;
    }
    
    public void setAlpha(float alpha) {
    	this.alpha=alpha;
    }
    
    public FloatBuffer getAmbientAsBuffer() {
		float[] temp = new float[3];
		temp[0] = ambient.x;
		temp[1] = ambient.y;
		temp[2] = ambient.z;

		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		buffer.clear();
		buffer.put(temp);
		buffer.put(1.0f);
		buffer.flip();
		return buffer;
	}
    
	public FloatBuffer getDiffuseAsBuffer() {
		float[] temp = new float[3];
		temp[0] = diffuse.x;
		temp[1] = diffuse.y;
		temp[2] = diffuse.z;

		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		buffer.clear();
		buffer.put(temp);
		buffer.put(1.0f);
		buffer.flip();
		return buffer;
	}

	public FloatBuffer getSpecularAsBuffer() {
		float[] temp = new float[3];
		temp[0] = specular.x;
		temp[1] = specular.y;
		temp[2] = specular.z;

		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		buffer.clear();
		buffer.put(temp);
		buffer.put(1.0f);
		buffer.flip();
		return buffer;
	}
	
	public ArrayList<Float> getFloatColor() {
		ArrayList<Float> color = new ArrayList<Float>();

		color.add(average(ambient.x, diffuse.x, specular.x));
		color.add(average(ambient.y, diffuse.y, specular.y));
		color.add(average(ambient.z, diffuse.z, specular.z));

		return color;
	}
	
	private float average(float a, float b, float c) {
		return (a + b + c) / 3;
	}
	
	public ArrayList<Byte> getColor() {
		ArrayList<Byte> color = new ArrayList<Byte>();

		color.add(
			byteAverage(ambient.x, diffuse.x,specular.x)
		);
		color.add(
			byteAverage(ambient.y, diffuse.y,specular.y)
		);
		color.add(
			byteAverage(ambient.z, diffuse.z,specular.z)
		);

		return color;
	}
	
	private byte byteAverage(float a, float b, float c) {
		return (byte) (((a * 255) + (b * 255) + (c * 255)) / 3);
	}
}

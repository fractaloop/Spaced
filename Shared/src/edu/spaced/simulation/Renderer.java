package edu.spaced.simulation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoader;

import edu.spaced.simulation.elements.LevelElement;


public class Renderer {
	private Mesh shipMesh;
	private Texture shipTexture;
	
	private OrthographicCamera camera;
	private float foo = 0;
	
	public Renderer() {
		try {
			InputStream in = Gdx.files.internal("data/ship.obj").read();
			shipMesh = ModelLoader.loadObj(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shipTexture = new Texture(Gdx.files.internal("data/ship.png"), true);
		shipTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	public void render(Simulation sim) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);

		// Camera
		camera.zoom = 1/20f;
		camera.far = 10f;
		camera.position.set(0, 0, 1);
		camera.direction.set(0, 0, -1);//.sub(camera.position).nor();		
		camera.update();
		camera.apply(Gdx.gl10);		
		
		// Render sim stuff (upside down)
		gl.glPushMatrix();
		gl.glRotatef(180, 1, 0, 0);
		for (LevelElement element : sim.getLevel().getElements()) {
			element.draw();
		}
		gl.glPopMatrix();
		
		// Lighting
		float[] direction = {-1f, 1f, 0.75f, 0};
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);

		// Render a dummy ship
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glTranslatef(0, 0, 0);
		foo += Gdx.graphics.getDeltaTime() *16;
		gl.glRotatef(90, 1, 0, 0);
		shipTexture.bind();
		shipMesh.render(GL10.GL_TRIANGLES);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_LIGHT0);
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		
	}
	
	public void reshape(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

}

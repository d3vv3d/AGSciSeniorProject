package com.d3v.senior.project.midpoint;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d3v.senior.project.Main;
import com.d3v.senior.project.Menu;
import com.d3v.senior.project.music.Music;

/**
 * The midpoint algorithm works to create 2D electric effects
 */
public class Midpoint implements Screen {

	OrthographicCamera camera;
	ShapeRenderer renderer;
	
	Array<Vector2> vectorArray;
	Vector2 start;
	Vector2 finish;
	int range = 0;
	long seed = 0;

	SpriteBatch batch;
	Texture menu;
	Texture menuKeys;
	
	Random rand;

	Main main;

	public Midpoint(Main main)
	{
		this.main = main;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		renderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.WHITE);
		//for vectors in vectorArray - 1 render a point then the one after it
		for(int rendered = 0; rendered < vectorArray.size - 1; rendered++)
		{
			renderer.line(vectorArray.get(rendered), vectorArray.get(rendered + 1));
		}
		renderer.end();


		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))// || main.controller.getButton(11))
		{
			seed = System.currentTimeMillis();
			//System.out.println("Got seed");
			rand.setSeed(seed);
			//System.out.println("Set seed");
			generate(3);
			//System.out.println("Generated");
		}
		if (Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			main.setScreenPlus(new Music(main));
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))// || main.controllerPlus.buttonJustPressed(main.controller, 12))
			main.setScreenPlus(new Menu(main));
		if (Gdx.input.isKeyJustPressed(Keys.SPACE))// || main.controllerPlus.buttonJustPressed(main.controller, 0))
		{	
			seed = System.currentTimeMillis();
			//System.out.println("Got seed");
			rand.setSeed(seed);
			//System.out.println("Set seed");
			generate(3);
			//System.out.println("Generated");
		}
		//RESET
		if (Gdx.input.isKeyJustPressed(Keys.R))
		{
			seed = 0;
			rand.setSeed(seed);
			generate(3);
		}
		//+1
		if (Gdx.input.isKeyJustPressed(Keys.E))
		{
			seed++;
			rand.setSeed(seed);
			generate(3);
		}
		//-1
		if (Gdx.input.isKeyJustPressed(Keys.Q))
		{
			seed--;
			rand.setSeed(seed);
			generate(3);
		}
		//+10
		if (Gdx.input.isKeyJustPressed(Keys.C))
		{
			seed += 10;
			rand.setSeed(seed);
			generate(3);
		}
		//-10
		if (Gdx.input.isKeyJustPressed(Keys.Z))
		{
			seed -= 10;
			rand.setSeed(seed);
			generate(3);
		}
		//+100
		if (Gdx.input.isKeyJustPressed(Keys.V))
		{
			seed += 100;
			rand.setSeed(seed);
			generate(3);
		}
		//-100
		if (Gdx.input.isKeyJustPressed(Keys.X))
		{
			seed -= 100;
			rand.setSeed(seed);
			generate(3);
		}

		if (main.control)
		{
			if (main.controllerPlus.buttonJustPressed(main.controller, 0))
			{
				seed = System.currentTimeMillis();
				//System.out.println("Got seed");
				rand.setSeed(seed);
				//System.out.println("Set seed");
				generate(3);
				//System.out.println("Generated");
			}

			if (main.controllerPlus.buttonJustPressed(main.controller, 3))
			{
				seed = 0;
				rand.setSeed(seed);
				generate(3);
			}

			//Increase buttons
			if (main.controllerPlus.buttonJustPressed(main.controller, 1))
			{
				seed++;
				rand.setSeed(seed);
				generate(3);
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 5))
			{
				seed += 10;
				rand.setSeed(seed);
				generate(3);
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 7))
			{
				seed += 100;
				rand.setSeed(seed);
				generate(3);
			}

			//Decrease seed
			if (main.controllerPlus.buttonJustPressed(main.controller, 2))
			{
				seed--;
				rand.setSeed(seed);
				generate(3);
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 4))
			{
				seed -= 10;
				rand.setSeed(seed);
				generate(3);
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 6))
			{
				seed -= 100;
				rand.setSeed(seed);
				generate(3);
			}
			if (main.controller.getButton(11))
			{
				seed = System.currentTimeMillis();
				//System.out.println("Got seed");
				rand.setSeed(seed);
				//System.out.println("Set seed");
				generate(3);
				//System.out.println("Generated");
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 12))
				main.setScreenPlus(new Menu(main));
		}

		batch.begin();
		if (Gdx.input.isKeyPressed(Keys.M))
			batch.draw(menuKeys, Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight() / 2);
		if (main.control)
			if (main.controller.getButton(9))
				batch.draw(menu, Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight() / 2);
		main.font.draw(batch, String.valueOf(seed), 0, main.font.getCapHeight());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		rand.setSeed(seed);
		generate(3);
	}

	@Override
	public void show() {
		vectorArray  = new Array<Vector2>();

		camera = new OrthographicCamera();
		renderer = new ShapeRenderer();
		start = new Vector2();
		finish = new Vector2();

		batch = new SpriteBatch();
		menu = new Texture("menu.png");
		menuKeys = new Texture("menuKeys.png");
		
		seed = System.currentTimeMillis();
		rand = new Random();
		rand.setSeed(seed);
		generate(3);
	}

	/*public void test() {
		System.out.println(rand.nextDouble());
	}*/
	
	public void generate(int iterations) {
		vectorArray.clear(); // As this is also called on resize the clear is for memory
		start.set(Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 2);
		finish.set(Gdx.graphics.getWidth() - start.x, start.y);
		vectorArray.add(start);
		vectorArray.add(finish);
		range = (int) (Gdx.graphics.getHeight() / 3.5);
		//System.out.println(range);

		for(int iter = 0; iter < iterations; iter++) {
			//System.out.println("Working...1");			
			/**
			 *  vectorArray.size - 1 - length;
			 *  This code is used to save memory. The midpoint algorithm adds to the vectorArray in real time
			 *  so the minus length accounts for each new vector added and the minus is because the midpoint algorithm
			 *  increases the size of the vectorArray by its current size minus one. (midpoint creates array * 2 - 1) 
			 */
			for(int length = 0; length < vectorArray.size - 1 - length; length++) {
				//System.out.println("Working...");
				/**
				 * 2 * length + 1
				 * This is used to put the points in their proper order for rendering.
				 * The plus one is to keep the value for being zero and the 2 * length is to skip over preexisting points 
				 */
				vectorArray.insert(2 * length + 1, 
						new Vector2(((vectorArray.get(2 * length).x + vectorArray.get(2 * length + 1).x) / 2), 
								(float) (((vectorArray.get(2 * length).y + vectorArray.get(2 * length + 1).y) / 2) 
										+ ((range * (rand.nextDouble() * 2 - 1) / (iter + 1))))));
			}
		}
	}

	@Override
	public void hide() {

	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		vectorArray.clear();
		renderer.dispose();
		menu.dispose();
		menuKeys.dispose();
		batch.dispose();
	}
}

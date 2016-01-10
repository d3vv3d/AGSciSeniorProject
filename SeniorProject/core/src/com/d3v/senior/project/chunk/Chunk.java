package com.d3v.senior.project.chunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.d3v.senior.project.Main;
import com.d3v.senior.project.Menu;
import com.d3v.senior.project.midpoint.Midpoint;

import java.util.Random;

public class Chunk implements Screen
{
	// Chunk Size
	static byte TilesPerChunk = 16;
	// Tile Size
	static byte TileSize = 32;
	// Total Chunk Size
	static int ChunkSize = TilesPerChunk * TileSize;
	// How many horizontal chunks there should be
	byte numberHorizontalChunks;
	// How many vertical chunks there should be
	byte numberVerticalChunks;
	// The chunk coordinate variables
	int ChunkX, ChunkY;
	
	// The array that contains all chunks
	Array<ChunkObject> chunkArray;
	// The pool that contains all chunks
	Pool<ChunkObject> chunkPool;
	
	SpriteBatch batch;
	Texture tex;
	Texture tex2;
	ChunkRender render;
	OrthographicCamera camera;
	Texture menu;
	Texture menuKeys;

	public static int seed = 5000;
	public static int currentSeed = 5000;
	
	Vector2 cameraChunk, cameraChunkOld;

	Main main;
	Random rand;

	public Chunk(Main main)
	{
		this.main = main;
		rand = new Random(currentSeed);

	}
	
	public void generate()
	{
		cameraChunk.set(((float) Math.ceil(camera.position.x / ChunkSize) - 1), ((float) Math.ceil(camera.position.y / ChunkSize) - 1));
		//cameraChunkOld.set(cameraChunk);
		
		numberHorizontalChunks = (byte) (Math.ceil((double) Gdx.graphics.getWidth() / Chunk.ChunkSize) + 2);
		numberVerticalChunks = (byte) (Math.ceil((double) Gdx.graphics.getHeight() / Chunk.ChunkSize) + 2);
		ChunkX = (int) cameraChunk.x - (int) (Math.ceil((double) (numberHorizontalChunks / 2)));
		ChunkY = (int) cameraChunk.y - (int) (Math.ceil((double) (numberVerticalChunks / 2)));

		for (int y = (int) cameraChunk.y - (int) Math.ceil((double) numberVerticalChunks / 2);
			 y <= (int) cameraChunk.y + (int) Math.ceil((double) numberVerticalChunks / 2); y++)
		{
			for (int x = (int) cameraChunk.x - (int) Math.ceil((double) numberHorizontalChunks / 2);
					x <= (int) cameraChunk.x + (int) Math.ceil((double) numberHorizontalChunks / 2); x++)
			{
				ChunkObject chunk = chunkPool.obtain();
				chunk.init(x, y);
				chunkArray.add(chunk);
			}
		}

		/*while (ChunkY <= cameraChunk.y + (int) (Math.ceil((double) numberVerticalChunks / 2)))
		{
			ChunkObject chunk = chunkPool.obtain();
			chunk.init(ChunkX, ChunkY);
			chunkArray.add(chunk);
			
			ChunkX++;
			if (ChunkX == numberHorizontalChunks + cameraChunk.x - 1)
			{
				ChunkX = (int) cameraChunk.x - (int) (Math.ceil((double) (numberHorizontalChunks / 2)));
				System.out.println("Y: " + ChunkY);
				ChunkY++;
			}
		}*/
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tex = new Texture("ShallowWater.png");
		tex2 = new Texture("Sand.png");
		render = new ChunkRender();
		camera = new OrthographicCamera();
		menu = new Texture("menu.png");
		menuKeys = new Texture("menuKeys.png");

		chunkArray = new Array<ChunkObject>();
		chunkPool = new Pool<ChunkObject>()
		{
			@Override
			protected ChunkObject newObject() {
				return new ChunkObject();
			}
		};

		//cameraChunk = new Vector2();
		//cameraChunkOld = new Vector2();

		cameraChunk = new Vector2(((float) Math.ceil(camera.position.x / ChunkSize) - 1),
				((float) Math.ceil(camera.position.y / ChunkSize) - 1));
		cameraChunkOld = new Vector2(cameraChunk);
		
		generate();
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		cameraChunk.set(((float) Math.ceil(camera.position.x / ChunkSize) - 1), ((float) Math.ceil(camera.position.y / ChunkSize) - 1));
		
		// Based on which chunk the camera is in delete certain chunks and generate new ones based on the screen size
		
		if(!cameraChunk.equals(cameraChunkOld))
		{
			//Back up regenerates all chunks but works perfectly on diagonals
			/*chunkPool.freeAll(chunkArray);
			chunkPool.clear();
			chunkArray.clear();

			generate();*/
			//System.out.println("Camera moved!");
			for (byte chunkNumber = 0; chunkNumber < chunkArray.size; chunkNumber++)
			{
				// Clear unnecessary chunks from the array and reset them for later use
				if (chunkArray.get(chunkNumber).position.x < cameraChunk.x - (int) Math.ceil((double) numberHorizontalChunks / 2) || 
						chunkArray.get(chunkNumber).position.x > cameraChunk.x + (int) Math.ceil((double) numberHorizontalChunks / 2))
				{
					chunkPool.free(chunkArray.get(chunkNumber));
					chunkArray.removeIndex(chunkNumber);
				}
				else if (chunkArray.get(chunkNumber).position.y < cameraChunk.y - (int) Math.ceil((double) numberVerticalChunks / 2) || 
						chunkArray.get(chunkNumber).position.y > cameraChunk.y + (int) Math.ceil((double) numberVerticalChunks / 2))
				{
					chunkPool.free(chunkArray.get(chunkNumber));
					chunkArray.removeIndex(chunkNumber);		
				}
			}
			
			// Generate new chunks has issues especially if moving diagonally
			//Up and Down generation
			if (cameraChunk.y > cameraChunkOld.y)
			{
				for (int x = (int) (cameraChunk.x - (int) Math.ceil((double) numberHorizontalChunks / 2));
						x <= cameraChunk.x + (int) Math.ceil((double) numberHorizontalChunks / 2); x++)
				{
					ChunkObject chunk = chunkPool.obtain();
					chunk.init(x, (int) cameraChunk.y + (int) Math.ceil((double) numberVerticalChunks / 2));
					chunkArray.add(chunk);
				}
			}
			else if (cameraChunk.y < cameraChunkOld.y)
			{
				for (int x = (int) (cameraChunk.x - (int) Math.ceil((double) numberHorizontalChunks / 2));
						x <= cameraChunk.x + (int) Math.ceil((double) numberHorizontalChunks  / 2); x++)
				{
					ChunkObject chunk = chunkPool.obtain();
					chunk.init(x, (int) cameraChunk.y - (int) Math.ceil((double) numberVerticalChunks / 2));
					chunkArray.add(chunk);
				}
			}
			
			//Left and Right generation
			if (cameraChunk.x < cameraChunkOld.x)
			{
				for (int y = (int) (cameraChunk.y - (int) Math.ceil((double) numberVerticalChunks / 2));
						y <= cameraChunk.y + (int) Math.ceil((double) numberVerticalChunks / 2); y++)
				{
					ChunkObject chunk = chunkPool.obtain();
					chunk.init((int) cameraChunk.x - (int) Math.ceil((double) numberHorizontalChunks / 2), y);
					chunkArray.add(chunk);
				}
			}
			else if(cameraChunk.x > cameraChunkOld.x)
			{
				for (int y = (int) (cameraChunk.y - (int) Math.ceil((double) numberVerticalChunks / 2));
						y <= cameraChunk.y + (int) Math.ceil((double) numberVerticalChunks / 2); y++)
				{
					ChunkObject chunk = chunkPool.obtain();
					chunk.init((int) cameraChunk.x + (int) Math.ceil((double) numberHorizontalChunks / 2), y);
					chunkArray.add(chunk);
				}
			}
			cameraChunkOld.set(cameraChunk);
			//System.out.println("Generation");*/
			///System.out.println("X: " + cameraChunk.x + " Y: " + cameraChunk.y);
		}
		
		if (Gdx.app.getInput().isKeyPressed(Keys.W) || Gdx.app.getInput().isKeyPressed(Keys.UP))
			camera.translate(0, 60 * delta);
		if (Gdx.app.getInput().isKeyPressed(Keys.A) || Gdx.app.getInput().isKeyPressed(Keys.LEFT))
			camera.translate(-60 * delta, 0);
		if (Gdx.app.getInput().isKeyPressed(Keys.S) || Gdx.app.getInput().isKeyPressed(Keys.DOWN))
			camera.translate(0, -60 * delta);
		if (Gdx.app.getInput().isKeyPressed(Keys.D) || Gdx.app.getInput().isKeyPressed(Keys.RIGHT))
			camera.translate(60 * delta, 0);

		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			currentSeed = (int) System.currentTimeMillis();
			rand.setSeed(currentSeed);
			changeSeed(rand.nextInt());
		}
		if (Gdx.input.isKeyJustPressed(Keys.E))
			changeSeed(currentSeed += 1);
		if (Gdx.input.isKeyJustPressed(Keys.Q))
			changeSeed(currentSeed -= 1);
		if (Gdx.input.isKeyJustPressed(Keys.R))
			changeSeed(currentSeed = seed);
		if (Gdx.input.isKeyJustPressed(Keys.Z))
			changeSeed(currentSeed -= 10);
		if (Gdx.input.isKeyJustPressed(Keys.C))
			changeSeed(currentSeed += 10);
		if (Gdx.input.isKeyJustPressed(Keys.X))
			changeSeed(currentSeed -= 100);
		if (Gdx.input.isKeyJustPressed(Keys.V))
			changeSeed(currentSeed += 100);

		if (Gdx.app.getInput().isKeyJustPressed(Keys.ENTER))
			main.setScreenPlus(new Midpoint(main));
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			main.setScreenPlus(new Menu(main));

		if (main.control)
		{
			if (main.controller.getAxis(1) <= -0.25f)
				camera.translate(0, 60 * delta);
			if (main.controller.getAxis(0) <= -0.25f)
				camera.translate(-60 * delta, 0);
			if (main.controller.getAxis(1) >= 0.25f)
				camera.translate(0, -60 * delta);
			if (main.controller.getAxis(0) >= 0.25f)
				camera.translate(60 * delta, 0);

			if (main.controllerPlus.buttonJustPressed(main.controller, 0))
			{
				currentSeed = (int) System.currentTimeMillis();
				rand.setSeed(currentSeed);
				changeSeed(rand.nextInt());
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 1))
				changeSeed(currentSeed += 1);
			if (main.controllerPlus.buttonJustPressed(main.controller, 2))
				changeSeed(currentSeed -= 1);
			if (main.controllerPlus.buttonJustPressed(main.controller, 3))
				changeSeed(currentSeed = seed);
			if (main.controllerPlus.buttonJustPressed(main.controller, 4))
				changeSeed(currentSeed -= 10);
			if (main.controllerPlus.buttonJustPressed(main.controller, 5))
				changeSeed(currentSeed += 10);
			if (main.controllerPlus.buttonJustPressed(main.controller, 6))
				changeSeed(currentSeed -= 100);
			if (main.controllerPlus.buttonJustPressed(main.controller, 7))
				changeSeed(currentSeed += 100);

			if (main.controllerPlus.buttonJustPressed(main.controller, 12))//main.controller.getButton(12))
				main.setScreenPlus(new Menu(main));
		}
		//System.out.println(chunkArray.size);
		//System.out.println("X: " + cameraChunk.x + ", Y: " + cameraChunk.y);
		
		batch.begin();
			render.render(batch, tex, tex2, chunkArray);
			//main.font.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), camera.position.x - (Gdx.graphics.getWidth() / 2),
					//camera.position.y - (Gdx.graphics.getHeight() / 2) + main.font.getCapHeight());
			main.font.draw(batch, String.valueOf(currentSeed), camera.position.x - (Gdx.graphics.getWidth() / 2),
					camera.position.y - (Gdx.graphics.getHeight() / 2) + main.font.getCapHeight());
		if (Gdx.input.isKeyPressed(Keys.M))
			batch.draw(menuKeys, camera.position.x - menu.getWidth() / 2, camera.position.y - menu.getHeight() / 2);
		if (main.control)
			if (main.controller.getButton(9))
				batch.draw(menu, camera.position.x - menu.getWidth() / 2, camera.position.y - menu.getHeight() / 2);
			//batch.draw(menu, Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2 + camera.position.x / 2,
			//		Gdx.graphics.getHeight() / 2 - menu.getHeight()/ 2 + camera.position.y / 2);
		batch.end();
	}

	public void changeSeed(int newSeed) {
		// Clear all previously created chunk data
		chunkPool.freeAll(chunkArray);
		chunkPool.clear();
		chunkArray.clear();

		generate();
		cameraChunkOld.set(cameraChunk);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		// This code will stop the the camera from reseting when the window is resized, 
		// but I do not intend to resize the screen
		//camera.viewportWidth = width;
		//camera.viewportHeight = height;
		
		// Clear all previously created chunk data
		chunkPool.freeAll(chunkArray);
		chunkPool.clear();
		chunkArray.clear();

		generate();
		cameraChunkOld.set(cameraChunk);
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
		batch.dispose();
		tex.dispose();
		tex2.dispose();
		menu.dispose();
		menuKeys.dispose();
		
		chunkPool.freeAll(chunkArray);
		chunkPool.clear();
		chunkArray.clear();
	}
}
package com.d3v.senior.project.music;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.d3v.senior.project.Main;
import com.d3v.senior.project.Menu;
import com.d3v.senior.project.noise.SimplexNoise;
import com.d3v.senior.project.noise.SimplexNoise_octave;

public class Music implements Screen {
	
	//Last # 5000 by default
	int seed = 5000;
	int currentSeed = 5000;
	Wrapper wrapper = new Wrapper(100 , 0.1, 5000);
	SimplexNoise noise = new SimplexNoise(1000, 0.1, 1);
	int i = 0;
	MidiHelper midiHelper = new MidiHelper();
	Note note = new Note(-1.0d, 1.0d);
	SimplexNoise_octave test = new SimplexNoise_octave(seed); //Produces some really haunting bits
	
	float timer = 2f;
	float elapsedTime = 2f;
	
	int tempNote = -1;
	int previousNote = 0;
	ArrayList<Integer> notes = new ArrayList<Integer>();
	int index = 0;

	SpriteBatch batch;
	Texture natKey;
	Texture sharpKey;
	Texture highlighted;
	Texture menu;
	Texture menuKeys;

	int keyHeight;
	int keyWidth;

	Array<Rectangle> keyArray;
	Array<Rectangle> keyArraySharp;
	Rectangle keyC3;

	OrthographicCamera camera;
	Main main;

	Random rand;

	public Music(Main main)
	{
		this.main = main;
	}
	
	@Override
	public void show() {
		//tempNote = note.getNote(noise.getNoise(i, 0));
		//tempNote = note.getNote(wrapper.getNoise(i) * 10);

		batch = new SpriteBatch();
		natKey = new Texture("whiteKey.png");
		sharpKey = new Texture("blackKey.png");
		highlighted = new Texture("selected.png");
		menu = new Texture("menu.png");
		menuKeys = new Texture("menuKeys.png");

		keyArray = new Array<Rectangle>();
		keyArraySharp = new Array<Rectangle>();
		keyC3 = new Rectangle();

		camera = new OrthographicCamera();

		rand = new Random(seed);

		while (notes.size() < 10) {
			tempNote = note.getNote(test.noise(i, 0));

			if (tempNote != previousNote) {
				notes.add(tempNote);
				previousNote = tempNote;
				System.out.println("Midi code: " + tempNote);
				//System.out.println("Noise was: " + wrapper.getNoise(i) * 10);
			}
			i++;
		}


		//Code for haunting sound
		/*for (int i = 0; i < 100; i++)
		{
			//tempNote = note.getNote(wrapper.getNoise(i) * 10);
			tempNote = note.getNote(test.noise(i, 0));
			
			if (tempNote != previousNote) {
				notes.add(tempNote);
				previousNote = tempNote;
				System.out.println("Midi code: " + tempNote);
				//System.out.println("Noise was: " + wrapper.getNoise(i) * 10);
			}
		}*/
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		elapsedTime += delta;

		if(index + 1 <= notes.size()) {
			if(elapsedTime >= timer) {
				midiHelper.play(notes.get(index));
				index++;
				elapsedTime = 0f;
			}
		}

		batch.begin();
		for (Rectangle keyRect : keyArray)
		{
			batch.draw(natKey, keyRect.x, keyRect.y, keyRect.width, keyRect.height);
		}
		for (Rectangle keyRect : keyArraySharp)
		{
			batch.draw(sharpKey, keyRect.x, keyRect.y, keyRect.width, keyRect.height);
		}
		//batch.draw(natKey, keyC3.x, keyC3.y, keyC3.width, keyC3.height);
		if (Gdx.input.isKeyPressed(Keys.M))
			batch.draw(menuKeys, Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight()/ 2);
		if (main.control)
			if (main.controller.getButton(9))
				batch.draw(menu, Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight()/ 2);
		main.font.draw(batch, String.valueOf(currentSeed), 0, main.font.getCapHeight());
		batch.end();

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
		{
			currentSeed = seed;
			changeSeed(currentSeed);
		}
		if (Gdx.input.isKeyJustPressed(Keys.Z))
			changeSeed(currentSeed -= 10);
		if (Gdx.input.isKeyJustPressed(Keys.C))
			changeSeed(currentSeed += 10);
		if (Gdx.input.isKeyJustPressed(Keys.X))
			changeSeed(currentSeed -= 100);
		if (Gdx.input.isKeyJustPressed(Keys.V))
			changeSeed(currentSeed += 100);
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))// || main.controllerPlus.buttonJustPressed(main.controller, 12))
			main.setScreenPlus(new Menu(main));

		//Stand in
		if (main.control)
		{
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
			{
				currentSeed = seed;
				changeSeed(currentSeed);
			}
			if (main.controllerPlus.buttonJustPressed(main.controller, 4))
				changeSeed(currentSeed -= 10);
			if (main.controllerPlus.buttonJustPressed(main.controller, 5))
				changeSeed(currentSeed += 10);
			if (main.controllerPlus.buttonJustPressed(main.controller, 6))
				changeSeed(currentSeed -= 100);
			if (main.controllerPlus.buttonJustPressed(main.controller, 7))
				changeSeed(currentSeed += 100);
			if (main.controllerPlus.buttonJustPressed(main.controller, 12))
				main.setScreenPlus(new Menu(main));
		}
			
		if (Gdx.input.justTouched()) {
			midiHelper.play(96);
		}
	}

	public void changeSeed(int newSeed) {
		test = new SimplexNoise_octave(newSeed);
		System.out.println(newSeed);

		i = 0;
		notes.clear();
		while (notes.size() < 10) {
			tempNote = note.getNote(test.noise(i, 0));

			if (tempNote != previousNote) {
				notes.add(tempNote);
				previousNote = tempNote;
				System.out.println("Midi code: " + tempNote);
				//System.out.println("Noise was: " + wrapper.getNoise(i) * 10);
			}
			i++;
		}

		index = 0;
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		keyWidth = width / 17;
		keyHeight = height / 2;

		keyArray.clear();
		keyArraySharp.clear();

		//Naturals
		for(int i = 0; i < 15; i++) {
			Rectangle keyRect = new Rectangle();
			keyRect.set((i+1)*keyWidth, keyHeight/2, keyWidth-5, keyHeight);
			keyArray.add(keyRect);
		}
		//Sharps
		for(int i = 0; i < 2; i++)
		{
			int increase = 0;
			if (i == 1)
				increase = 7;
			//2 pair
			for(int j = 0; j < 2; j++) {
				Rectangle keyRect = new Rectangle();
				keyRect.set((j+1+increase)*keyWidth + (keyWidth/2), keyHeight, keyWidth-5, keyHeight/2);
				keyArraySharp.add(keyRect);
			}
			//3 pair
			for(int j = 0; j < 3; j++) {
				Rectangle keyRect = new Rectangle();
				keyRect.set((j+4+increase)*keyWidth + (keyWidth/2), keyHeight, keyWidth-5, keyHeight/2);
				keyArraySharp.add(keyRect);
			}
		}


		keyC3.set(keyWidth, keyHeight/2, 2*(keyWidth-5), keyHeight);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
		midiHelper.end();
		notes.clear();
		notes.trimToSize();
		menu.dispose();
		menuKeys.dispose();
		batch.dispose();
		natKey.dispose();
		sharpKey.dispose();
	}
}

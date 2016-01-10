package com.d3v.senior.project;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Screen;

import javax.sound.midi.ControllerEventListener;

public class Main extends Game
{
	Array<Screen> screens = new Array<Screen>();
	public boolean control = false;
	public BitmapFont font;
	public ControllerPlus controllerPlus;
	public Controller controller;

	public void create() {
		/*System.out.println(Controllers.getControllers().size);
		for (Controller control: Controllers.getControllers())
		{
			System.out.println(control.getName());
		}*/
		font = new BitmapFont();
		if (Controllers.getControllers().size > 0)
			control = true;

		if (control)
		{
			controller = Controllers.getControllers().first();
			System.out.println(controller.getName());
			controllerPlus = new ControllerPlus();
			//Controllers.addListener(new ControllerPlus());
		}
		System.out.println("Control: " + control);
		System.out.println();
		this.setScreen(new Menu(this));
	}

	public void setScreenPlus(Screen next)
	{
		screens.add(next);
		this.setScreen(next);
	}

	public void dispose()
	{
		System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		for (byte index = 0; index < screens.size; index++)
			screens.get(index).dispose();
		screens.clear();
		font.dispose();
	}
}

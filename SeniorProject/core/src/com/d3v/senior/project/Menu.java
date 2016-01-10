package com.d3v.senior.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.d3v.senior.project.chunk.Chunk;
import com.d3v.senior.project.midpoint.Midpoint;
import com.d3v.senior.project.music.Music;

/**
 * Created by Developer on 8/1/2015.
 */
public class Menu implements Screen
{
    Rectangle chunkRect;
    Texture chunkText;

    Rectangle midpointRect;
    Texture midpointText;

    Rectangle musicRect;
    Texture musicText;

    SpriteBatch batch;
    OrthographicCamera camera;

    byte position = 0;
    Rectangle selectRect;
    Texture selectText;

    Main main;

    public Menu(Main main)
    {
        this.main = main;
    }

    public void show()
    {
        chunkRect = new Rectangle(0,Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3);
        chunkText = new Texture("chunk.png");

        midpointRect = new Rectangle(0, Gdx.graphics.getHeight() / 3, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3);
        midpointText = new Texture("midpoint.png");

        musicRect = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3);
        musicText = new Texture("music.png");

        selectRect = new Rectangle(chunkRect);
        selectText = new Texture("selected.png");

        batch = new SpriteBatch();
    }

    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP))// || main.controllerPlus.axisReleased(main.controller, 1, -0.25f))
        {
            position--;
            if (position < 0)
                position = 2;
        }
        if (Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DOWN))// || main.controllerPlus.axisReleased(main.controller, 1, 0.25f))
        {
            position++;
            if (position > 2)
                position = 0;
        }
        if (main.control)
        {
            if (main.controllerPlus.axisReleased(main.controller, 1, -0.25f))
            {
                position--;
                if (position < 0)
                    position = 2;
            }
            if (main.controllerPlus.axisReleased(main.controller, 1, 0.25f))
            {
                position++;
                if (position > 2)
                    position = 0;
            }
        }

        switch (position)
        {
            case 0:
                selectRect.set(chunkRect);
                break;
            case 1:
                selectRect.set(midpointRect);
                break;
            case 2:
                selectRect.set(musicRect);
                break;
        }

        if(Gdx.input.isKeyJustPressed(Keys.ENTER))// || main.controllerPlus.buttonJustPressed(main.controller, 1))
            switch (position)
            {
                case 0:
                    main.setScreenPlus(new Chunk(main));
                    break;
                case 1:
                    main.setScreenPlus(new Midpoint(main));
                    break;
                case 2:
                    main.setScreenPlus(new Music(main));
                    break;
            }

            if (main.control)
                if (main.controllerPlus.buttonJustPressed(main.controller, 1))
                    switch (position)
                    {
                        case 0:
                            main.setScreenPlus(new Chunk(main));
                            break;
                        case 1:
                            main.setScreenPlus(new Midpoint(main));
                            break;
                        case 2:
                            main.setScreenPlus(new Music(main));
                            break;
                    }


        batch.begin();
        batch.draw(chunkText, chunkRect.x, chunkRect.y, chunkRect.getWidth(), chunkRect.getHeight());
        batch.draw(midpointText, midpointRect.x, midpointRect.y, midpointRect.getWidth(), midpointRect.getHeight());
        batch.draw(musicText, musicRect.x, musicRect.y, musicRect.getWidth(), musicRect.getHeight());
        batch.draw(selectText, selectRect.x, selectRect.y, selectRect.getWidth(), selectRect.getHeight());
        batch.end();
    }

    public void resize(int width, int height)
    {

    }

    public void pause()
    {

    }

    public void resume()
    {

    }

    public void hide()
    {

    }

    public void dispose()
    {
        chunkText.dispose();
        midpointText.dispose();
        musicText.dispose();
        selectText.dispose();
        batch.dispose();
    }
}

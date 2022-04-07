package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.pirategame.pathfinding.PathFinder;
import com.mygdx.pirategame.screen.GameScreen;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

/**
 * Useful util methods used to mock specific classes within the game
 */
public class MockClass {

    /**
     * Used to mock the Hud class so all the static methods can be used and init the points and score to 0
     */
    public static void mockHudStatic() {
        Hud hud = Mockito.mock(Hud.class);
        Whitebox.setInternalState(hud, "scoreLabel", new Label(String.format("%03d", 0), new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        Whitebox.setInternalState(hud, "coinLabel", new Label(String.format("%03d", 0), new Label.LabelStyle(new BitmapFont(), Color.YELLOW)));
        Whitebox.setInternalState(hud, "coinMulti", 1);

        // setting up score and coins to 0
        Hud.setPoints(0);
        Hud.setCoins(0);
    }

    /**
     * Used to mock the game screen so it can be used in tests
     * @return The created game screen
     */
    public static GameScreen mockGameScreen() {
        // creating required variables, and mocking return values
        GameScreen screen = Mockito.mock(GameScreen.class);
        
        Mockito.when(screen.getWorld()).thenReturn(new World(new Vector2(0, 0), true));

        // mocking the map
        TmxMapLoader mapLoader = new TmxMapLoader();
        Mockito.when(screen.getMap()).thenReturn(mapLoader.load("map/map.tmx"));
        Mockito.when(screen.getTileMapWidth()).thenReturn(128 * 64);
        Mockito.when(screen.getTileMapHeight()).thenReturn(128 * 64);
        Mockito.when(screen.getTileWidth()).thenReturn(64);

        // mocking the path finder
        Mockito.when(screen.getPathFinder()).thenReturn(new PathFinder(screen, 64));

        return screen;
    }

    public static GameScreen mockGameScreenWithPlayer() {
        GameScreen screen = mockGameScreen();

        Mockito.when(screen.getPlayerPos()).thenReturn(new Vector2(13*64 / PirateGame.PPM, 11*64 / PirateGame.PPM));

        return screen;
    }

}

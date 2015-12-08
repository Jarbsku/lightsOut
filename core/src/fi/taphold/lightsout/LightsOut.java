package fi.taphold.lightsout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import fi.taphold.lightsout.resources.World;

public class LightsOut extends ApplicationAdapter {
	public static final int VIRTUAL_WIDTH = 512;
	public static final int VIRTUAL_HEIGHT = 256;

	World _world;
	@Override
	public void create () {
		_world = new World("mapp.tmx");
	}

	@Override
	public void render() {
		_world.render();
	}
}
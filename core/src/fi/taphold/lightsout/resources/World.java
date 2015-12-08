package fi.taphold.lightsout.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import fi.taphold.lightsout.entities.DynamicEntity;
import fi.taphold.lightsout.entities.Entity;
import fi.taphold.lightsout.entities.Monster;
import fi.taphold.lightsout.entities.Player;
import fi.taphold.lightsout.resources.weather.WeatherEffects;

/**
 * @author Jarno Lahti.
 */
public class World {
    private final String BACKGROUND_LAYER = "backgroundLayer";
    private final String COLLISION_LAYER = "collisionLayer";
    private final String FOREGROUND_LAYER = "foregroundLayer";
    private final String OBJECT_LAYER = "objectLayer";
    private final String TREE_BACKGROUND = "treeBG";
    private final String TREE_BACKGROUND2 = "treeBG2";
    private final String TREE_BACKGROUND3 = "treeBG3";

    public static final Vector2 GRAVITY = new Vector2(0, -.25f);

    private final String MAPS_PATH = "maps/";

    private TiledMap _map;
    private OrthogonalTiledMapRenderer _mapRenderer;
    private TmxMapLoader _mapLoader;

    private WorldCamera _camera;

    private TiledMapTileLayer _collisionLayer;
    private TiledMapTileLayer _objectLayer;

    private float tileWidth;
    private float tileHeight;

    private Player _player;

    private ArrayList<Entity> _entities;

    private SpriteBatch _batch;

    private MapObject _playerStart;

    public World(String mapFile) {
        _mapLoader = new TmxMapLoader();
        _map = _mapLoader.load(MAPS_PATH + mapFile);

        _mapRenderer = new OrthogonalTiledMapRenderer(_map);

        _collisionLayer = (TiledMapTileLayer) _map.getLayers().get(COLLISION_LAYER);
        _objectLayer = (TiledMapTileLayer) _map.getLayers().get(OBJECT_LAYER);
        _playerStart = _objectLayer.getObjects().get("playerStart");



        tileWidth = _collisionLayer.getTileWidth();
        tileHeight = _collisionLayer.getTileHeight();

        _camera = new WorldCamera(_collisionLayer);

        _player = new Player(new Vector2(_objectLayer.getObjects().get("playerStart")., 70));

        _entities = new ArrayList();
        _entities.add(_player);
        _entities.add(new Monster(new Vector2(100, 70)));
        _entities.add(new Monster(new Vector2(420, 70)));

        _batch = (SpriteBatch) _mapRenderer.getBatch();
    }

    public void render() {
        WeatherController.getInstance().update();

        Gdx.gl.glClearColor(WeatherController.getInstance().getLightningBackgroundColor().r,
                WeatherController.getInstance().getLightningBackgroundColor().g,
                WeatherController.getInstance().getLightningBackgroundColor().b,
                1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _mapRenderer.setView(_camera);

        _mapRenderer.getBatch().begin();
            //_mapRenderer.renderTileLayer((TiledMapTileLayer) _map.getLayers().get(TREE_BACKGROUND));
            //_mapRenderer.renderTileLayer((TiledMapTileLayer) _map.getLayers().get(TREE_BACKGROUND2));
            //_mapRenderer.renderTileLayer((TiledMapTileLayer) _map.getLayers().get(TREE_BACKGROUND3));
        //_mapRenderer.renderTileLayer((TiledMapTileLayer) _map.getLayers().get(BACKGROUND_LAYER));
            _mapRenderer.renderTileLayer((TiledMapTileLayer) _map.getLayers().get(COLLISION_LAYER));
        _mapRenderer.getBatch().end();

        _camera.updateCamera(_player);
        _player.controls();

        for(Entity e : _entities){
            e.updateVelocity();
            if(e instanceof DynamicEntity){
                checkWorldCollision(e);
            }
            e.updatePosition();
            if(e instanceof Player) {
                e.resetHorizontialVelocity();
                ((Player) e).updateLight(_batch, _camera);
            }
            e.render(_batch);
        }

        _mapRenderer.getBatch().begin();
            //_mapRenderer.renderTileLayer((TiledMapTileLayer)_map.getLayers().get(FOREGROUND_LAYER));
        _mapRenderer.getBatch().end();
    }

    private void checkWorldCollision(Entity e) {
        for (int y = 0; y < _collisionLayer.getHeight(); y++) {
            for (int x = 0; x < _collisionLayer.getWidth(); x++) {

                try {
                    _collisionLayer.getCell(x, y).getTile();
                } catch (NullPointerException ex) {
                    continue;
                }

                if (_collisionLayer.getCell(x, y).getTile().getProperties().containsKey("collide")) {

                    if ((e.getPosition().x + e.getSprite().getWidth() > (x * tileWidth))
                            && e.getPosition().x < ((x * tileWidth) + tileWidth)
                            && (e.getPosition().y + e.getVelocity().y + DynamicEntity.GROUND_COLLISION_POINT_HEIGHT > (y * tileHeight))
                            && (e.getPosition().y + e.getVelocity().y) < ((y * tileHeight) + tileHeight)) {

                        ((DynamicEntity) e).setGroundedCheck();
                    }

                    if ((e.getPosition().x + e.getVelocity().x + e.getSprite().getWidth() > (x * tileWidth))
                            && (e.getPosition().x + e.getVelocity().x) < ((x * tileWidth) + tileWidth)
                            && (e.getPosition().y + e.getSprite().getWidth() > (y * tileHeight))
                            && e.getPosition().y < ((y * tileHeight) + tileHeight)) {

                        ((DynamicEntity) e).onHorizontialCollide();
                    }

                    if ((e.getPosition().x + e.getSprite().getWidth() > (x * tileWidth))
                            && (e.getPosition().x) < ((x * tileWidth) + tileWidth)
                            && (e.getPosition().y + e.getVelocity().y + e.getSprite().getHeight() > (y * tileHeight))
                            && (e.getPosition().y + e.getVelocity().y) < ((y * tileHeight) + tileHeight)) {

                            ((DynamicEntity) e).onVerticalCollide();
                    }
                }
            }
        }
        ((DynamicEntity) e).setGrounded();
    }
}


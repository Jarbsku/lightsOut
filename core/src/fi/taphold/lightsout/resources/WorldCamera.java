package fi.taphold.lightsout.resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fi.taphold.lightsout.entities.Player;

/**
 * @author Jarno Lahti.
 */
public class WorldCamera extends OrthographicCamera {
    public static final int VIRTUAL_WIDTH = 512;
    public static final int VIRTUAL_HEIGHT = 256;

    private float mapWidth;
    private float mapHeight;

    public WorldCamera(TiledMapTileLayer collisionLayer){
        setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        mapWidth = collisionLayer.getWidth() * collisionLayer.getTileWidth();
        mapHeight = collisionLayer.getHeight() * collisionLayer.getTileHeight();
    }

    public void updateCamera(Player player){

        //camera X
        if((player.getPosition().x + (player.getSprite().getWidth() / 2)) - (VIRTUAL_WIDTH / 2) <= 0) {
            position.x = (VIRTUAL_WIDTH / 2);
        }else if((player.getPosition().x + (player.getSprite().getWidth() / 2)) + (VIRTUAL_WIDTH / 2) >= mapWidth){
            position.x = mapWidth - (VIRTUAL_WIDTH / 2);
        }else{
            position.x = player.getPosition().x + (player.getSprite().getWidth() / 2);
        }

        //camera Y
        if((player.getPosition().y + (player.getSprite().getHeight() / 2)) - (VIRTUAL_HEIGHT / 2) <= 0) {
            position.y = (VIRTUAL_HEIGHT / 2);
        }else if((player.getPosition().y + (player.getSprite().getHeight() / 2)) + (VIRTUAL_HEIGHT / 2) >= mapHeight){
            position.y = mapHeight - (VIRTUAL_HEIGHT / 2);
        }else{
            position.y = player.getPosition().y + (player.getSprite().getHeight() / 2);
        }

        update();
    }
}

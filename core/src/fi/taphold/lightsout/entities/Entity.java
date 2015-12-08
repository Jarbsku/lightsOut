package fi.taphold.lightsout.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import fi.taphold.lightsout.resources.World;

/**
 * @author Jarno Lahti.
 */
public class Entity {

    protected Vector2 _position;
    protected Vector2 _velocity;
    protected Sprite _sprite;

    public Entity(){
        _position = new Vector2();
        _velocity = new Vector2();
    }

    public Vector2 getPosition(){ return _position; }

    public Vector2 getVelocity(){ return _velocity; }

    public Sprite getSprite(){ return _sprite; }

    public void render(SpriteBatch batch){
        batch.begin();
        _sprite.draw(batch);
        batch.end();
    };

    public void updateVelocity(){
        _velocity.add(World.GRAVITY);
    }

    public void updatePosition(){
        _position.add(_velocity);
        _sprite.setPosition(_position.x, _position.y);
    }

    public void resetHorizontialVelocity(){
        _velocity.set(0, _velocity.y);
    }
}

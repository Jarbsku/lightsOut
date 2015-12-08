package fi.taphold.lightsout.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jarno on 24.11.2015.
 */
public class Monster extends DynamicEntity{

    private boolean facingLeft = false;

    public Monster(Vector2 position){
        super();
        _position.set(position);
        _sprite = new Sprite(new Texture(Gdx.files.internal("sprites/2.png")));
        _sprite.setPosition(_position.x, _position.y);
        _sprite.setOriginCenter();
        _velocity.set(1, _velocity.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(_velocity.x < 0 && !facingLeft){
            facingLeft = true;
            _sprite.flip(true, false);
        }else if (_velocity.x > 0 && facingLeft){
            facingLeft = false;
            _sprite.flip(true, false);
        }
    }

    @Override
    public void onVerticalCollide() {
        super.onVerticalCollide();
        _velocity.set(_velocity.x, 0);
    }

    @Override
    public void onHorizontialCollide() {
        super.onHorizontialCollide();
        _velocity.set(_velocity.x * (-1), _velocity.y);
    }
}

package fi.taphold.lightsout.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import fi.taphold.lightsout.resources.PointLight;

/**
 * @author Jarno Lahti.
 */
public class Player extends DynamicEntity {

    private PointLight _pointLight;

    private Animation _animation;
    private float animationTime = 0;

    private boolean facingLeft = false;

    public Player(Vector2 position){
        super();
        _position.set(position);
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites/RunningRobo_Atlas.pack"));
        _animation = new Animation(1/8f, atlas.findRegions("robo"));
        _animation.setPlayMode(Animation.PlayMode.LOOP);

        _sprite = new Sprite(_animation.getKeyFrame(0));
        _sprite.setPosition(_position.x, _position.y);
        _sprite.setOriginCenter();

        _pointLight = new PointLight();
    }

    public void controls(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.W) && isGrounded()){
            _velocity.set(_velocity.x, 6f);
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            _velocity.set(_velocity.x, -1f);
        }else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            _velocity.set(-2f, _velocity.y);
            facingLeft = true;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            _velocity.set(2f, _velocity.y);
            facingLeft = false;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            _pointLight.switchLights();
        }else{
            _sprite.setRegion(_animation.getKeyFrame(0));
            _sprite.flip(facingLeft, false);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        animationTime += Gdx.graphics.getDeltaTime();
        if(isGrounded()) {
            _sprite.setRegion(_animation.getKeyFrame(animationTime));
        }else{
            _sprite.setRegion(_animation.getKeyFrame(0));
        }
        _sprite.flip(facingLeft, false);

    }

    public void updateLight(SpriteBatch batch, OrthographicCamera camera){
        _pointLight.setLightBoundsPos(_position.x + (_sprite.getWidth() / 2) - _pointLight.getLightBounds().width / 2,
                _position.y + (_sprite.getHeight() / 2) - _pointLight.getLightBounds().height / 2);
        _pointLight.update(batch, camera);
    }

    @Override
    public void onHorizontialCollide() {
        super.onHorizontialCollide();
        _velocity.set(0, _velocity.y);
    }

    @Override
    public void onVerticalCollide() {
        super.onVerticalCollide();
        _velocity.set(_velocity.x, 0);
    }
}

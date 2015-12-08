package fi.taphold.lightsout.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.taphold.lightsout.LightsOut;
import fi.taphold.lightsout.entities.Player;

/**
 * Created by Jarbsku on 5.10.2015.
 */
public class PointLight {
    private final int LIGHT_WIDTH = 200  * (Gdx.graphics.getWidth() / LightsOut.VIRTUAL_WIDTH);
    private final int LIGHT_HEIGHT = 200 * (Gdx.graphics.getHeight() / LightsOut.VIRTUAL_HEIGHT);

    private Rectangle _lightBounds;
    private String _vertexShader;
    private String _fragmentShader;
    private ShaderProgram _shaderProgram;

    private boolean lightOn = false;

    public PointLight(){
        _lightBounds = new Rectangle(0, 0, LIGHT_WIDTH, LIGHT_HEIGHT);

        _vertexShader = Gdx.files.internal("shaders/testShdr.vert").readString();
        _fragmentShader = Gdx.files.internal("shaders/testFragShdr.frag").readString();

        ShaderProgram.pedantic = false;
        _shaderProgram = new ShaderProgram(_vertexShader, _fragmentShader);

        if(!_shaderProgram.isCompiled()){
            System.out.println(_shaderProgram.getLog());
        }
    }

    public void update(SpriteBatch batch, OrthographicCamera camera){
        Vector3 temp = new Vector3(_lightBounds.getX(), _lightBounds.getY(), 0);
        camera.project(temp);

        _shaderProgram.begin();
        _shaderProgram.setUniformf("u_resolution", _lightBounds.width, _lightBounds.height);
        _shaderProgram.setUniformf("u_position", temp);
        _shaderProgram.end();

        if(lightOn) {
            batch.setShader(_shaderProgram);
        }else{
            batch.setShader(null);
        }
    }

    public Rectangle getLightBounds() {
        return _lightBounds;
    }

    public void setLightBoundsPos(float posX, float posY){
        _lightBounds.setPosition(posX, posY);
    }

    public void switchLights(){

        if(lightOn){
            lightOn = false;
        }else{
            lightOn = true;
        }
    }
}

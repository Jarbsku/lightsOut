package fi.taphold.lightsout.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import fi.taphold.lightsout.resources.weather.WeatherEffects;

/**
 * @author Jarno Lahti.
 */
public class WeatherController {
    private final int LIGHTNING_DELAY_MIN = 5000; // milliseconds;
    private final int LIGHTNING_DELAY_MAX = 10000; // milliseconds;

    private final float LIGHTNING_R = 0.00784f;
    private final float LIGHTNING_G = 0.0235f;
    private final float LIGHTNING_B = 0.1f;

    private long lightningTimeStamp;
    private long lightningDelay;

    private Random _random;

    private static WeatherController _instance;

    private Color _lightningBackgroundColor;
    private Color _lightning;

    private WeatherController(){
        initLightningVariables();
    }

    public static WeatherController getInstance(){
        if(_instance == null){
            _instance = new WeatherController();
        }
        return _instance;
    }

    public Color getLightningBackgroundColor(){
        return _lightningBackgroundColor;
    }

    public void update(){
        WeatherEffects.getInstance().update();
        updateLightning();
    }

    private void updateLightning(){
        if(System.currentTimeMillis() - lightningTimeStamp > lightningDelay){
            WeatherEffects.getInstance().thunderEffect(_lightningBackgroundColor, _lightning, .1f, .8f);
            lightningDelay = (long)_random.nextInt(LIGHTNING_DELAY_MAX - LIGHTNING_DELAY_MIN) + LIGHTNING_DELAY_MIN;
            lightningTimeStamp = System.currentTimeMillis();
        }
    }

    private void initLightningVariables(){
        _lightningBackgroundColor = new Color(0, 0, 0, 1);
        _lightning = new Color(LIGHTNING_R, LIGHTNING_G, LIGHTNING_B, 1);
        lightningTimeStamp = System.currentTimeMillis();
        _random = new Random();
        lightningDelay = (long)_random.nextInt(LIGHTNING_DELAY_MAX - LIGHTNING_DELAY_MIN) + LIGHTNING_DELAY_MIN;
    }
}

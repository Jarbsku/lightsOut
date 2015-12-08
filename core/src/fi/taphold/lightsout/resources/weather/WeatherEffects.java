package fi.taphold.lightsout.resources.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import fi.taphold.lightsout.resources.WeatherController;

/**
 * @author Jarno Lahti.
 */
public class WeatherEffects {

    private static WeatherEffects _instance;
    private TweenManager _weatherManager;

    private WeatherEffects(){
        Tween.registerAccessor(Color.class, new ColorAccessor());
        _weatherManager = new TweenManager();
    }

    public static WeatherEffects getInstance(){
        if(_instance == null){
            _instance = new WeatherEffects();
        }

        return _instance;
    }

    public void update(){
        _weatherManager.update(Gdx.graphics.getDeltaTime());
    }

    public void thunderEffect(final Color color, final Color colorTo, final float inTime, final float outTime){
        Tween
                .to(color, ColorAccessor.RGB, inTime)
                .target(colorTo.r, colorTo.g, colorTo.b)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Tween
                                .to(color, ColorAccessor.RGB, outTime)
                                .target(0, 0, 0)
                                .start(_weatherManager);
                    }
                })
                .start(_weatherManager);
    }

}

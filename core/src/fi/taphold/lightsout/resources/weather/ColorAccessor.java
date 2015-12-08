package fi.taphold.lightsout.resources.weather;

import com.badlogic.gdx.graphics.Color;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * @author Jarno Lahti.
 */
public class ColorAccessor implements TweenAccessor<Color> {

    public static final int RGB = 0;

    @Override
    public int getValues(Color target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case RGB:
                returnValues[0] = target.r;
                returnValues[1] = target.g;
                returnValues[2] = target.b;
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public void setValues(Color target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case RGB:
                target.set( newValues[0],  newValues[1],  newValues[2], 1);
                break;
            default:
                assert false;
        }
    }
}

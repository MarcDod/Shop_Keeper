package de.marcdoderer.shop_keeper.shop.time;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.hud.HudElement;

/**
 * The DayNightCircle class keeps track of the time in game time.
 *
 * Stores the time in seconds, minutes and hours.
 * Has a way to render the time as a String.
 * Has a speedUp variable to change the time speed.
 * Includes methods the keep track of the sun power.
 */
public class DayNightCircle implements HudElement {

    // For manipulating the time speed (288f == 24h in 5 Minutes, 1 == 24h in 24h)
    public final float speedUp;

    // Time in seconds
    private float seconds;
    // Time in minutes between 0 and 60
    private int minutes;
    // Time in hour between 0 and 24
    private int hours;

    // if visible == true the Time will be render
    private boolean visible;


    private final BitmapFont font;

    /**
     * Initialises the current time given in seconds
     * Ensures getVisible() == true;
     * @param seconds
     */
    public DayNightCircle(final float seconds){
        this.seconds = seconds;
        this.speedUp = 288;
        this.minutes = (int) ((seconds * speedUp) / 60f);;
        this.hours = (int) ((minutes) / 60f);
        minutes %= 60;
        hours %= 24;
        this.font = new BitmapFont();
        this.visible = true;
    }

    public float getSeconds(){
        return this.seconds;
    }

    /**
     * this.seconds = seconds
     * calls update(0); to update minutes and hours based on the seconds
     * @param seconds the seconds the DayNightCircle should have.
     */
    public void setSeconds(final float seconds){
        this.seconds = seconds;
        update(0f);
    }

    /**
     * updates the time based on the delta time
     *
     * increases the time by delta * speedUP;
     * calculates the minutes and hours based on seconds
     * if it is after 24 hours the seconds get resetted to 0.
     *
     * @param delta the delta time since last update call.
     */
    @Override
    public final void update(final float delta){
        seconds += delta * speedUp;
        minutes = (int) ((seconds) / 60f);
        hours = (int) ((minutes) / 60f);
        minutes %= 60;

        if(hours >= 24){
            seconds = 0;
        }
        hours %= 24;

    }

    /**
     * calculates the sunIntensity based on the time.
     * @return the intensity of the sun
     */
    private float getSunIntensity(){
        return Math.max(Math.abs((Math.min((float)Math.cos(seconds * 0.000036f * 2), 0.6f))), 0.3f);
    }

    /**
     * Creates the Sun color.
     * If it is Night the sun color will have a little blue touch.
     * If it is Day the sun color will be white.
     * @return The color of the sun.
     */
    public Color getSunColor(){
        if(!isNight()){
            return new Color(1f, 1f, 1f, getSunIntensity());
        }else{
            return new Color(0f, 0f, 1f, getSunIntensity() * 0.8f) ;
        }
    }

    /**
     * calculates the ambient light intensity based on the time.
     * @return the intensity of the sun
     */
    public float getAmbientLightIntensity(){
        return (Math.max((float)Math.sin(seconds * 0.000036f), 0.3f));
    }

    /**
     * returns true if it is after 18 and 7 clock.
     * returns false if it is after 7 and before 18 a clock.
     * @return
     */
    public boolean isNight(){
        return hours >= 18 || hours <= 7;
    }

    /**
     * renders a little text at the top right of the screen with the current in game time.
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch){
        if(!visible) return;
        font.draw(batch, timeToString(hours) + " : " + timeToString(minutes), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 30);
    }

    /**
     * disposes the font.
     */
    public void dispose(){
        this.font.dispose();
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private String timeToString(int time){
        String timeString = String.valueOf(time);
        if(timeString.length() > 1)
            return timeString;
        else
            return "0" + timeString;
    }
}

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

public class DayNightCircle implements HudElement {

    private float seconds;
    private final float speedUp;
    private int minutes;
    private int hours;
    private boolean visible;


    private final BitmapFont font;


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
    public void setSeconds(final float seconds){
        this.seconds = seconds;
        System.out.println((int) ((seconds * speedUp) / 60f) / 60f);
        update(0f);
    }

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

    private float getSunIntensity(){
        return Math.max(Math.abs((Math.min((float)Math.cos(seconds * 0.000036f * 2), 0.6f))), 0.3f);
    }

    public Color getSunColor(){
        if(!isNight()){
            return new Color(1f, 1f, 1f, getSunIntensity());
        }else{
            return new Color(0f, 0f, 1f, getSunIntensity() * 0.8f) ;
        }
    }

    public float getAmbientLight(){
        return (Math.max((float)Math.sin(seconds * 0.000036f), 0.3f));
    }

    public boolean isNight(){
        return hours >= 18 || hours <= 7;
    }

    @Override
    public void render(SpriteBatch batch){
        if(!visible) return;
        font.draw(batch, timeToString(hours) + " : " + timeToString(minutes), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 30);
    }

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

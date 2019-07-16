package com.reactivegames.flappybirdclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.reactivegames.flappybirdclone.FlappyBirds;

public class GameOver extends State {

    private Texture background;
    private Texture gameOver;
    private Music gameOverMusic;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyBirds.WIDTH/2,FlappyBirds.HEIGHT/2);
        this.background = new Texture("sprites/background-day.png");
        this.gameOver = new Texture("sprites/gameover.png");
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/gameOverMusic.mp3"));
        gameOverMusic.setLooping(true);
        gameOverMusic.setVolume(0.1f);
    }

    @Override
    protected void handledInput() {
        if(Gdx.input.isTouched()|| Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handledInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        gameOverMusic.play();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameOver, camera.position.x - gameOver.getWidth() / 2, camera.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameOver.dispose();
        gameOverMusic.dispose();
        System.out.println("GameOver disposed");
    }
}


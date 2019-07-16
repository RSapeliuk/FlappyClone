package com.reactivegames.flappybirdclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.reactivegames.flappybirdclone.FlappyBirds;

public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    private Music menuMusic;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyBirds.WIDTH / 2, FlappyBirds.HEIGHT / 2);
        this.background = new Texture("sprites/background-day.png");
        this.playBtn = new Texture("sprites/message.png");
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menuMusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.1f);
    }

    @Override
    protected void handledInput() {
        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handledInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        menuMusic.play();
        sb.begin();
        sb.draw(background, 0, 0, FlappyBirds.WIDTH, FlappyBirds.HEIGHT);
        sb.draw(playBtn, camera.position.x - playBtn.getWidth() / 2, camera.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        menuMusic.dispose();
        System.out.println("MenuState disposed");
    }
}

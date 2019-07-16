package com.reactivegames.flappybirdclone.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.reactivegames.flappybirdclone.FlappyBirds;
import com.reactivegames.flappybirdclone.sprites.Bird;
import com.reactivegames.flappybirdclone.sprites.Tube;



public class PlayState extends State {

    public static final int TUBE_SPACING = 125;
    public static final int TUBE_COUNT = 6;
    public static final int GROUND_Y_OFFSET = -80;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Music playMusic;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        background = new Texture("sprites/background-day.png");
        ground = new Texture("sprites/base.png");
        camera.setToOrtho(false, FlappyBirds.WIDTH / 2, FlappyBirds.HEIGHT / 2);
        tubes = new Array<Tube>();
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth/2,GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth/2)+ ground.getWidth(),GROUND_Y_OFFSET);
        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
        playMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/gameMusic.mp3"));
        playMusic.setLooping(true);
        playMusic.setVolume(0.1f);
    }

    @Override
    protected void handledInput() {
        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handledInput();
        updateGround();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i<tubes.size; i++) {
            Tube tube = tubes.get(i);
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if(tube.collider(bird.getBounds())){
                gsm.set(new GameOver(gsm));
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        playMusic.play();
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground,groundPos1.x,groundPos2.y);
        sb.draw(ground,groundPos2.x,groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        playMusic.dispose();
        System.out.println("PlayState disposed");
    }


    private void updateGround(){
        if(camera.position.x-(camera.viewportWidth/2)>groundPos1.x+ground.getWidth())
            groundPos1.add(ground.getWidth()*2,0);
        if(camera.position.x - (camera.viewportWidth/2)>groundPos2.x+ground.getWidth())
            groundPos2.add(ground.getWidth()*2,0);
    }
}

package com.vanjav.sproots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class Sproots extends ApplicationAdapter {
    /* ---------- VARIABLES ---------- */

    /* properties */
	private int width, height;
	private float density, dpi;

	/* state */
    private boolean gameStarted = false;
    private boolean gameOver = false;

    /* touch */
    private float currX, currY;

    /* iteration */
    private PointF prevPoint, currPoint;
    private int i, j;
    private List<Sproot> currSprootsList;
    private Sproot currSproot;

    /* controller */
    private Controller controller;

    /* sizes */
    private float sprootWidth, sprootHeight, leafHeight;
    private float halfSprootWidth, halfLeafWidth;
    private float groundHeight;

    /* graphics */
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    /* graphics - colors */
    private Color skyColor = new Color(0.5f, 1, 1, 1);
    private Color groundColor = new Color(0, 0.5f, 0, 1);

    /* graphics - textures and sprites */
    private TextureAtlas textureAtlas;
    private Sprite sprootSprite;
    private Sprite leafSprite;

    private Texture.TextureFilter filter = Texture.TextureFilter.Linear;

    /* graphics - fonts */
    private BitmapFont font;
    private float textScale, textHeight;
    private float titlePositionY;

    /* data */
    private Preferences preferences;
    private boolean vibrate;

    /* ---------- CREATION ---------- */

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        density = Gdx.graphics.getDensity();

        dpi = density * 160f;

        sprootWidth = 0.33f * this.dpi; // 0.33 inches
        groundHeight = 0.25f * height;  // 1/4 of screen

        textureAtlas = new TextureAtlas("textures.atlas");
        for (i = 0; i < textureAtlas.getRegions().size; i++) {
            textureAtlas.getRegions().get(i).getTexture().setFilter(filter, filter);
        }

        sprootSprite = textureAtlas.createSprite("sproot");
        sprootSprite.setScale(sprootWidth / sprootSprite.getWidth());
        sprootSprite.setOrigin(0, 0);

        sprootHeight = sprootSprite.getHeight() * sprootSprite.getScaleY();

        leafSprite = textureAtlas.createSprite("leaf");
        leafSprite.setScale(sprootSprite.getScaleX());
        leafSprite.setOrigin(0, 0);

        leafHeight = leafSprite.getHeight() * leafSprite.getScaleY();

        halfSprootWidth = sprootWidth/2;
        halfLeafWidth = leafSprite.getWidth() * leafSprite.getScaleX() / 2;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        preferences = Gdx.app.getPreferences("preferences");
        vibrate = preferences.getBoolean("vibrate", true);

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        /* ---------- TOUCH ---------- */

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                currX = screenX;
                currY = height - screenY;

                currSprootsList = controller.getSproots();

                for (i = 0; i < currSprootsList.size(); i++) {
                    currSproot = currSprootsList.get(i);

                    if (currX > currSproot.getPosition().x - halfSprootWidth
                            && currX < currSproot.getPosition().x + halfSprootWidth
                            && currY > currSproot.getPosition().y
                            && currY < currSproot.getPosition().y + sprootHeight){
                        currSproot.addLeaf();
                    }
                }

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                currX = screenX;
                currY = height - screenY;

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return true;
            }
        });

        startNewGame();
    }

    public void startNewGame() {
        controller = new Controller(
                width,
                height,
                density,
                sprootWidth,
                groundHeight
        );

        gameStarted = false;
        gameOver = false;
    }

    /* ---------- FRAME ---------- */

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
        draw();
    }

    private void update(float deltaTimeSeconds) {
        controller.update(deltaTimeSeconds);
    }

    private void draw() {
        Gdx.gl.glClearColor(skyColor.r, skyColor.g, skyColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(groundColor.r, groundColor.g, groundColor.b, 1);
        shapeRenderer.rect(0, 0, width, groundHeight);

        shapeRenderer.end();

        batch.begin();

        currSprootsList = controller.getSproots();
        for (i = 0; i < currSprootsList.size(); i++) {
            currSproot = currSprootsList.get(i);
            sprootSprite.setPosition(currSproot.getPosition().x - halfSprootWidth, currSproot.getPosition().y);
            sprootSprite.draw(batch);

            for (j = 0; j < currSproot.getNumLeaves(); j++) {
                leafSprite.setPosition(
                        currSproot.getPosition().x - halfLeafWidth,
                        currSproot.getPosition().y + sprootHeight + (j * leafHeight)
                );
                leafSprite.draw(batch);
            }
        }

        batch.end();
    }
}

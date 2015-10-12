package com.slm.games.magicwheather;
import java.io.IOException;
import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;


public class ResourcesManager {

	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
	// Game Sounds
	
	private Sound snd_tuono;
	private Sound snd_rain;

	private AssetBitmapTexture cloudTexture;
	private TextureRegion cloudTextureRegion;
	
	private AssetBitmapTexture snowflickTexture;
	private TextureRegion snowflickTextureRegion;

	private AssetBitmapTexture gocciaTexture;
	private TextureRegion gocciaTextureRegion;
		
	 public void loadGameResources()
	    {
	        try {
				loadGameGfx();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //loadGameFonts();
	        loadGameAudio();
	    }
	 
	private void loadGameAudio()
    {
    	SoundFactory.setAssetBasePath("sounds/");
		try {
			this.snd_tuono = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().engine.getSoundManager(), activity, "tuono.wav");
		    this.snd_rain = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().engine.getSoundManager(), activity, "rain.wav");
		} catch (final IOException e) {
			Debug.e(e);
		}
    }
	
	private void loadGameGfx() throws IOException
	{
		cloudTexture = new AssetBitmapTexture(this.activity.getTextureManager(), this.activity.getAssets(), "gfx/trasparent_cloud.png");
		cloudTextureRegion = TextureRegionFactory.extractFromTexture(cloudTexture);
		cloudTexture.load();
		
		snowflickTexture = new AssetBitmapTexture(this.activity.getTextureManager(), this.activity.getAssets(), "gfx/snowflick.png");
		snowflickTextureRegion = TextureRegionFactory.extractFromTexture(snowflickTexture);
		snowflickTexture.load();
		
		 gocciaTexture = new AssetBitmapTexture(this.activity.getTextureManager(), this.activity.getAssets(), "gfx/goccia_001.png");
		 gocciaTextureRegion = TextureRegionFactory.extractFromTexture(gocciaTexture);
		 gocciaTexture.load();
	}
	
	public Sprite getCloud(float x,float y)
	{
		return new Sprite(x, y, this.cloudTextureRegion, this.vbom);
	}
	
	public Sprite getDrop(float x, float y)
	{
		return new Sprite(x, y, this.gocciaTextureRegion, this.vbom);
	}
	
	public Sprite getSnowflick(float x, float y)
	{
		return new Sprite(x, y, this.snowflickTextureRegion, this.vbom);
	}
	
	/**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
	
    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    public SpriteGroup getRain(int numGocce, int scene_width, int scene_height, boolean animate)
	{
		SpriteGroup gocce = new SpriteGroup(gocciaTexture, numGocce, this.vbom); 
		float minX = 0.0f;
		float maxX = scene_width;
		
		float minY = scene_height - 400;
		float maxY = scene_height;
		
		float dmin = 1.0f;
		float dmax = 2.0f;
		
		float scaleMin = 0.05f;
		float scaleMax = 0.1f;
		
		for (int i=0; i<numGocce;i++)
		{
			
             
			Random rand = new Random();

			float gocciaX = rand.nextFloat() * (maxX - minX) + minX;
			float gocciaY = rand.nextFloat() * (maxY - minY) + minY;
			float dur = rand.nextFloat() * (dmax - dmin) + dmin;
			float scale = rand.nextFloat() * (scaleMax - scaleMin) + scaleMin;
			
			final Sprite goccia = getDrop(gocciaX, gocciaY);
			goccia.setScale(scale, scale);
			
			if (animate)
			{
				SequenceEntityModifier em = new SequenceEntityModifier(new MoveYModifier(dur, gocciaY,-200),
						new DelayModifier(0.5f));
				LoopEntityModifier le = new LoopEntityModifier(em);

				goccia.registerEntityModifier(le);
			}
			
			
			gocce.attachChild(goccia);
		}
		return gocce;
	}
    
    
    public SpriteGroup getSnow(int numFlicks, int scene_width, int scene_height, boolean animate)
	{
		SpriteGroup flicks = new SpriteGroup(snowflickTexture, numFlicks, this.vbom); 
		float minX = 0.0f;
		float maxX = scene_width;
		
		float minY = scene_height - 400;
		float maxY = scene_height;
		
		float dmin = 3.0f;
		float dmax = 6.0f;
		
		float scaleMin = 0.5f;
		float scaleMax = 1.0f;
		
		for (int i=0; i<numFlicks;i++)
		{
			
             
			Random rand = new Random();

			float flickX = rand.nextFloat() * (maxX - minX) + minX;
			float flickY = rand.nextFloat() * (maxY - minY) + minY;
			float dur = rand.nextFloat() * (dmax - dmin) + dmin;
			float scale = rand.nextFloat() * (scaleMax - scaleMin) + scaleMin;
			
			final Sprite flick = getSnowflick(flickX, flickY);
			flick.setScale(scale, scale);
			
			if (animate)
			{
				SequenceEntityModifier em = new SequenceEntityModifier(new MoveYModifier(dur, flickY,-200),
						new DelayModifier(0.5f));
				LoopEntityModifier le = new LoopEntityModifier(em);

				flick.registerEntityModifier(le);
			}
			
			
			flicks.attachChild(flick);
		}
		return flicks;
	}
    
    public void playTuono(){
    	this.snd_tuono.play();
    }
    
    public void playRain(){
    	this.snd_rain.setLoopCount(-1);
    	this.snd_rain.setLooping(true);
    	this.snd_rain.play();
    }
    
    public void stopRain(){
    	if (this.snd_rain!=null)
    	this.snd_rain.stop();
    }
    
	public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
	
}

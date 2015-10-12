package com.slm.games.magicwheather;

import java.io.IOException;


//import net.jayschwa.android.preference.SliderPreference;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
 
import org.andengine.entity.modifier.MoveModifier;
 
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;

import org.andengine.extension.augmentedreality.SimpleBaseAugmentedRealityGameActivity;
 
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.util.adt.color.Color;

import com.slm.games.magicweather.R;

 
import android.content.Intent;
import android.content.SharedPreferences;
 
import android.opengl.EGLSurface;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.widget.Toast;

 


public class GameActivity extends SimpleBaseAugmentedRealityGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	private Surface inputSurface;

	// ===========================================================
	// Fields
	// ===========================================================

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  
	}
	
	  
	private Camera camera;
	 
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		//Toast.makeText(this, "If you don't see a sprite moving over the screen, try starting this while already being in Landscape orientation!!", Toast.LENGTH_LONG).show();

		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(false, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    
		final ConfigChooserOptions configChooserOptions = engineOptions.getRenderOptions().getConfigChooserOptions();
		
		configChooserOptions.setRequestedRedSize(8);
		configChooserOptions.setRequestedGreenSize(8);
		configChooserOptions.setRequestedBlueSize(8);
		configChooserOptions.setRequestedAlphaSize(8);
		configChooserOptions.setRequestedDepthSize(16);
		return engineOptions;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	  
	private void showOptionsDialog()
	{
// 		PreferencesFragment prefsFragment =  new PreferencesFragment();
////		SliderPreference prefs = new SliderPreference();
// 		FragmentManager fragmentManager = getFragmentManager();
// 		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// 		//prefsFragment.show(fragmentTransaction, "prefs_fragment");
// 		
// 	// Display the fragment as the main content.
// 		fragmentTransaction.replace(android.R.id.content,  prefsFragment).commit();
		// Intent intent = new Intent();
	    //    intent.setClass(GameActivity.this, SetPreferenceActivity.class);
	    //    startActivityForResult(intent, 0); 
	  
	}
	 
	protected void startRecording()
	{
		Toast.makeText(this, "Starting recordings....", Toast.LENGTH_LONG).show();
		//this.inputSurface
		VideoRecorder recorder = new VideoRecorder();
		recorder.recordSurface(this.inputSurface);
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // TODO Auto-generated method stub
	  //super.onActivityResult(requestCode, resultCode, data);
	  
	  /*
	   * To make it simple, always re-load Preference setting.
	   */
	  
	  loadPref();
	 }
	    
	 private void loadPref(){
	  SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	  
//	  boolean my_checkbox_preference = mySharedPreferences.getBoolean("checkbox_preference", false);
//	  prefCheckBox.setChecked(my_checkbox_preference);
//
//	  String my_edittext_preference = mySharedPreferences.getString("edittext_preference", "");
//	     prefEditText.setText(my_edittext_preference);

	 }
	 
	
	private void showQuitDialog()
	{
		finish();
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	      
	        case R.id.mnu_quit:
	        	this.showQuitDialog();
	        	return true;
	        case R.id.mnu_options:
	        	this.showOptionsDialog();
	        	return true;
	        case R.id.mnu_record:
	        	this.startRecording();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onCreateResources() throws IOException {
		 //ResourcesManager.getInstance();
		 ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		 ResourcesManager.getInstance().loadGameResources();
	}

	@Override
	public Scene onCreateScene() {
		//Toast.makeText(this, "ON CREATE SCENE", 600).show();
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.getBackground().setColor(Color.TRANSPARENT);

		final float centerX = CAMERA_WIDTH / 2;
		final float centerY = CAMERA_HEIGHT / 2;

		final Sprite cloud1 = ResourcesManager.getInstance().getCloud(centerX, centerY);
		final Sprite cloud2 = ResourcesManager.getInstance().getCloud(centerX, CAMERA_HEIGHT-70);
		final Sprite cloud3 = ResourcesManager.getInstance().getCloud(centerX-100, CAMERA_HEIGHT-100);
		 
		final Rectangle background = new Rectangle(CAMERA_WIDTH/2.0f,CAMERA_HEIGHT/2.0f, CAMERA_WIDTH, CAMERA_HEIGHT, getVertexBufferObjectManager());
		background.setColor(0.0f, 0.0f, 0.0f, 0.8f);
		cloud1.registerEntityModifier(new MoveModifier(35, 0, CAMERA_HEIGHT-70, CAMERA_WIDTH+300, CAMERA_HEIGHT-80));
		cloud2.registerEntityModifier(new MoveModifier(25, -100, CAMERA_HEIGHT-130, CAMERA_WIDTH+300, CAMERA_HEIGHT-160));
		
		SpriteGroup rain = ResourcesManager.getInstance().getRain(500, CAMERA_WIDTH, CAMERA_HEIGHT, true);
		//SpriteGroup snow = ResourcesManager.getInstance().getSnow(500, CAMERA_WIDTH, CAMERA_HEIGHT, true);
		
		scene.attachChild(rain);
		//scene.attachChild(snow);	
		scene.attachChild(cloud1);
		scene.attachChild(cloud2);
		scene.attachChild(cloud3);
		//scene.attachChild(background);
		ResourcesManager.getInstance().playTuono();
		ResourcesManager.getInstance().playRain();
		
		 
		this.inputSurface =  this.mRenderSurfaceView.getHolder().getSurface();
		return scene;
		
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	
//	@Override
//	protected void onPause(){
//		ResourcesManager.getInstance().stopRain();
//		super.onPause();
//	}
//	
	@Override
	protected void onDestroy() {
		 
		super.onDestroy();

	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

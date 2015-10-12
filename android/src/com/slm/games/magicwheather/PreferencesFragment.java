package com.slm.games.magicwheather;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;


public class PreferencesFragment  extends PreferenceFragment {
	 
	private String TAG = "F0";
	 	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	     // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences);
	        Log.d(TAG, "onCreate");  
	        }
	 	
	 	 
	 	
	 	@Override
	 	public void onActivityCreated (Bundle savedInstanceState)
	 	{   
	 		super.onActivityCreated(savedInstanceState);
	 		
	 		//MainActivity ma = (MainActivity) getActivity();
	 		 Log.d(TAG, "onActivityCreated ");  
	 		//SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(ma);
			//sharedPreferences.registerOnSharedPreferenceChangeListener(ma);
			
			//PreferenceManager.setDefaultValues(ma, R.xml.preferences, false);
			
//			// Display the fragment as the main content.
//	        getFragmentManager().beginTransaction()
//	                .replace(android.R.id.content, new SlmVocalPreferencesFragment())
//	                .commit();
          
			//ma.updatePreferencesTitles();
			
	 	}
}

/*
public class PreferencesFragment extends DialogFragment {
//	
//	@Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TransparentTheme);
//        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
//        p.width = LayoutParams.MATCH_PARENT;
//        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
//        p.x = 200;
//  
//        getDialog().getWindow().setAttributes(p);
//    }
//	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.TransparentTheme0);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_prefs, null));
         
        // Add action buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	PreferencesFragment.this.getDialog().cancel();
            }
        });      
        // Create the AlertDialog object and return it
//        return builder.create();
        
        
 	Dialog d =builder.create();
 	d.requestWindowFeature(Window.FEATURE_NO_TITLE);
    WindowManager.LayoutParams wmlp = d.getWindow().getAttributes();
    wmlp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    //wmlp.x = 100;   //x position
    //wmlp.y = 100;   //y position
    d.getWindow().setAttributes(wmlp);
    d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    
 	//d.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
 	//d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        d.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);      
//        d.setContentView(R.layout.layout_prefs);
//       
//       // set color transparent
//        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        
 	 
        return d;
    }


}
*/
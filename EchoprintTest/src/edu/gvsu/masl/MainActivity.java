package edu.gvsu.masl;


import java.util.Hashtable;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;

import edu.gvsu.masl.echoprint.AudioFingerprinter;
import edu.gvsu.masl.echoprint.AudioFingerprinter.AudioFingerprinterListener;

public class MainActivity extends ActivityBase implements AudioFingerprinterListener {
	
	private AnimationDrawable mAnim;
	boolean recording, resolved;
	AudioFingerprinter fingerprinter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//remove actionbar(will replace this with actionbar overlay later)
		ActionBar actionBar=getActionBar();
		actionBar.show();
		
		ImageView imageView = (ImageView) findViewById(R.id.animation_frame);
		imageView.setBackgroundResource(R.drawable.view_animation);
		mAnim = (AnimationDrawable) imageView.getBackground();
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playAnimation();
			}
		});

		
		RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.swipe);
		relativeLayout.setOnTouchListener(new View.OnTouchListener() {
			float x1,x2;
            float y1, y2;
            
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
                {
                       // when user first touches the screen we get x and y coordinate
                        case MotionEvent.ACTION_DOWN:
                        {
                            x1 = event.getX();
                            y1 = event.getY();
                            break;
                       }
                        case MotionEvent.ACTION_UP:
                        {
                            x2 = event.getX();
                            y2 = event.getY(); 

                            //if left to right sweep event on screen
                            if (x1 < x2)
                            {
                            	if(mAnim.isRunning()){
                            		mAnim.stop();
                            	}
                            	Intent intent = new Intent(MainActivity.this,
        								PlayRecoActivity.class);
                            	startActivity(intent);
                            }
                          
                            // if right to left sweep event on screen
                            else if (x1 > x2)
                            {
                            	Intent intent = new Intent(MainActivity.this,
        								PlayRecoActivity.class);
                            	startActivity(intent);
                            }
                          
                            // if UP to Down sweep event on screen
                            else if (y1 < y2)
                            {
                                
                            }
                          
                            //if Down to UP sweep event on screen
                            else if (y1 > y2)
                            {
                                
                            }
                            break;
                        }
                }
				return true;
			}
		});
	}
	
	public void playAnimation(){
		if(!mAnim.isRunning()){
			mAnim.setEnterFadeDuration(250);
			mAnim.setExitFadeDuration(250);
			mAnim.start();
		}
		// Perform action on click
    	if(recording)
    	{            		 
			fingerprinter.stop();        			
    	}
    	else
    	{            		
    		if(fingerprinter == null)
    			fingerprinter = new AudioFingerprinter(MainActivity.this);
    		
    		fingerprinter.fingerprint(15);
    	}
	}
	
	@Override
	public void didFinishListening() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void didFinishListeningPass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void willStartListening() {
		// TODO Auto-generated method stub
		recording = true;
		resolved = false;
	}

	@Override
	public void willStartListeningPass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void didGenerateFingerprintCode(String code) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Will fetch info for code starting:\n" 
		+ code.substring(0, Math.min(50, code.length())), Toast.LENGTH_LONG).show();
	}

	@Override
	public void didFindMatchForCode(Hashtable<String, String> table, String code) {
		// TODO Auto-generated method stub
		resolved = true;
		Toast.makeText(getApplicationContext(), "Match: \n" + table, Toast.LENGTH_LONG).show();
		goToSearchNewsActivity(table);
	}

	@Override
	public void didNotFindMatchForCode(String code) {
		// TODO Auto-generated method stub
		resolved = true;
		Toast.makeText(getApplicationContext(), "No match for code starting with: \n" +
		code.substring(0, Math.min(50, code.length())), Toast.LENGTH_LONG).show();
		goToSearchNewsActivity(null);
	}

	@Override
	public void didFailWithException(Exception e) {
		// TODO Auto-generated method stub
		resolved = true;
		recording=false;
		Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
		goToSearchNewsActivity(null);
	}
    
	public void goToSearchNewsActivity(Hashtable<String,String> table){
		Intent intent = new Intent(MainActivity.this,
				SearchNewsActivity.class);
		if(table!=null){
			intent.putExtra("artist", table.get("artist"));
			intent.putExtra("track", table.get("track"));
		}
    	startActivity(intent);
	}
}

package com.example.swissapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash_screen);
		
		
		 Thread background = new Thread() {
				public void run() {

					try {
						// Thread will sleep for 3 seconds
						sleep(3 * 1000);
					
					
							Intent i = new Intent(SplashScreen.this , MainActivity.class);
							startActivity(i);
					
						
					}
					
					catch(Exception e){
						
					}
	        
		}

	        };
	     // start thread
	     	background.start();
	        
		}
	}



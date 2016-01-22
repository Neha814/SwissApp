package com.example.fragments;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.swissapp.MainActivity;
import com.example.swissapp.R;

public class SettingsFragment extends Fragment{
	
	SeekBar seekBar1;
	
	RadioGroup shre_mode;
	RadioButton rb;
	RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
	int selectedId;
	String language;
	Locale myLocale;
	SharedPreferences sp;
	int RadioButtonId;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
	        
	        sp = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
	        
	        RadioButtonId = sp.getInt("radioButtonId", 0);
	    
	        seekBar1 = (SeekBar) rootView.findViewById(R.id.seekBar1);
	        shre_mode = (RadioGroup) rootView.findViewById(R.id.shre_mode);
	        radioButton1 = (RadioButton) rootView.findViewById(R.id.radioButton1);
	       	radioButton2 = (RadioButton) rootView.findViewById(R.id.radioButton2);
	        radioButton3 = (RadioButton) rootView.findViewById(R.id.radioButton3);
	        radioButton4 = (RadioButton) rootView.findViewById(R.id.radioButton4);
	        
	        sp.getInt("radius", 0);
	        if( sp.getInt("radius", 50)>40){
	        seekBar1.setProgress(50);
	        }
	        
	        else if(sp.getInt("radius", 50)<=5){
	        	seekBar1.setProgress(0);
	        }
	        else {
	        	int progress = sp.getInt("radius", 0);
	        	seekBar1.setProgress(progress);
	        }
	        
	        if(RadioButtonId==0){
	        
	        radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
			radioButton2.setBackgroundColor(Color.parseColor("#fef6df"));
			radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
			radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
			radioButton2.setChecked(true);
	        }
	        
	        else if(RadioButtonId==R.id.radioButton1){
	        	radioButton1.setBackgroundColor(Color.parseColor("#fef6df"));
				  radioButton2.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton1.setChecked(true);
	        }
	        else if(RadioButtonId==R.id.radioButton2){
	        	radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton2.setBackgroundColor(Color.parseColor("#fef6df"));
				  radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton2.setChecked(true);
	        }
	        else if(RadioButtonId==R.id.radioButton3){
	        	radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton2.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton3.setBackgroundColor(Color.parseColor("#fef6df"));
				  radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton3.setChecked(true);
	        }
	        else if(RadioButtonId==R.id.radioButton4){
	        	radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton2.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
				  radioButton4.setBackgroundColor(Color.parseColor("#fef6df")); 
				  radioButton4.setChecked(true);
	        }
			  
	        shre_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if(checkedId==R.id.radioButton1){
						  radioButton1.setBackgroundColor(Color.parseColor("#fef6df"));
						  radioButton2.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  Editor e = sp.edit();
							e.putInt("radioButtonId", R.id.radioButton1);
							e.putString("language", "german");
							e.commit();
						  setLocale("nl");
						  
					  } else if(checkedId==R.id.radioButton2){
						  radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton2.setBackgroundColor(Color.parseColor("#fef6df"));
						  radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  Editor e = sp.edit();
							e.putInt("radioButtonId", R.id.radioButton2);
							e.putString("language", "english");
							e.commit();
						  setLocale("en");
					  } else if(checkedId==R.id.radioButton3){
						  radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton2.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton3.setBackgroundColor(Color.parseColor("#fef6df"));
						  radioButton4.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  Editor e = sp.edit();
							e.putInt("radioButtonId", R.id.radioButton3);
							e.putString("language", "french");
							e.commit();
						  setLocale("fr");
					  } else if(checkedId==R.id.radioButton4){
						  radioButton1.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton2.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton3.setBackgroundColor(Color.parseColor("#f4f4f4"));
						  radioButton4.setBackgroundColor(Color.parseColor("#fef6df")); 
						  Editor e = sp.edit();
							e.putInt("radioButtonId", R.id.radioButton4);
							e.putString("language", "italian");
							e.commit();
						  setLocale("it");
					  }
					
				}
			});
	        
			  
			  
	        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					Log.e("",""+String.valueOf(progress)+" Km"); 
					Editor e = sp.edit();
					if(progress<=5){
						e.putInt("radius",5);
					}else {
					e.putInt("radius", progress);
					}
					e.commit();
				}
			});
	        return rootView;
	 }
	 
	 public void setLocale(String lang) { 
		    myLocale = new Locale(lang); 
		    Resources res = getResources(); 
		    DisplayMetrics dm = res.getDisplayMetrics(); 
		    Configuration conf = res.getConfiguration(); 
		    conf.locale = myLocale; 
		    res.updateConfiguration(conf, dm); 
		    Editor e = sp.edit();
			e.putInt("CurrentTab", 2);
			e.commit();
		    Intent refresh = new Intent(getActivity(), MainActivity.class); 
		    startActivity(refresh); 
		    
//		    FragmentManager fm = getActivity().getSupportFragmentManager();
//	        FragmentTransaction ft = fm.beginTransaction();
//	        SettingsFragment  fragment = new SettingsFragment();
//	       
//	        if(fragment != null) {
//	            // Replace current fragment by this new one
//	            
//	        	ft.replace(android.R.id.tabcontent, fragment);
//	        	
//	       }
//	            else{
//	            	 ft.add(android.R.id.tabcontent, fragment);
//	            }
//	        ft.addToBackStack(null);
//	 
//	        ft.commit();   
		   
		}
	 
	 
	
}

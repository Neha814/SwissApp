package com.example.swissapp;

import com.example.fragments.ListFragment;
import com.example.fragments.MapFragment;
import com.example.fragments.SettingsFragment;
import com.example.fragments.FavoritesFragment;
import com.example.fragments.InfosFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.Data;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static FragmentTabHost mTabHost;
	static SharedPreferences sp;
	public static ContentResolver appContext;
	public static Uri uri ;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		sp = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		
		
//		if (!((LocationManager) getSystemService("location"))
//				.isProviderEnabled("gps")) {
//			showGPSDisabledAlertToUser();
//			return;
//		}
		
		Editor e = sp.edit();
		e.putInt("CurrentTab", 0);
		e.commit();
		
		appContext = getContentResolver();
		uri = Data.CONTENT_URI;
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);

		mTabHost.getTabWidget().setDividerDrawable(null);
		
		//mTabHost.getTabWidget().setDividerDrawable(R.drawable.line);
		
		Resources ressources = getResources();

		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator(
						getTabIndicator(mTabHost.getContext(), getString(R.string.list),
								R.drawable.list_white)), ListFragment.class, null);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator(
						getTabIndicator(mTabHost.getContext(), getString(R.string.map),
								R.drawable.map_white)), ListFragment.class, null);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab3").setIndicator(
						getTabIndicator(mTabHost.getContext(), getString(R.string.setting),
								R.drawable.setting_white)), SettingsFragment.class, null);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab4").setIndicator(
						getTabIndicator(mTabHost.getContext(), getString(R.string.favorites),
								R.drawable.favorites_white)), FavoritesFragment.class, null);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab5").setIndicator(
						getTabIndicator(mTabHost.getContext(), getString(R.string.infos),
								R.drawable.infos_white)), InfosFragment.class, null);

		mTabHost.setCurrentTab(sp.getInt("CurrentTab", 0));
		setTabColor(mTabHost);

		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				setTabColor(mTabHost);
				
			}

		});

	}

	private static void setTabColor(FragmentTabHost mTabHost) {
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#3d7cce"));

			View view = mTabHost.getTabWidget().getChildTabViewAt(i);
			TextView text = (TextView) view.findViewById(R.id.text);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			if (i == 0) {
				text.setTextColor(Color.parseColor("#ffffff"));
				image.setImageResource(R.drawable.list_white);
			}

			else if (i == 1) {
				text.setTextColor(Color.parseColor("#ffffff"));
				image.setImageResource(R.drawable.map_white);
			}

			else if (i == 2) {
				text.setTextColor(Color.parseColor("#ffffff"));
				image.setImageResource(R.drawable.setting_white);
			}

			else if (i == 3) {
				text.setTextColor(Color.parseColor("#ffffff"));
				image.setImageResource(R.drawable.favorites_white);
			}

			else if (i == 4) {
				text.setTextColor(Color.parseColor("#ffffff"));
				image.setImageResource(R.drawable.infos_white);
			}
		}

		int tab = mTabHost.getCurrentTab();
		
		Editor e = sp.edit();
		e.putInt("current_tab", tab);
		e.commit();

		mTabHost.getTabWidget().getChildAt(tab)
				.setBackgroundColor(Color.parseColor("#ffbc0b"));

		View view = mTabHost.getTabWidget().getChildTabViewAt(tab);
		TextView text = (TextView) view.findViewById(R.id.text);
		ImageView image = (ImageView) view.findViewById(R.id.image);
		if (tab == 0) {
			text.setTextColor(Color.parseColor("#000000"));
			image.setImageResource(R.drawable.list_grey);
		}

		else if (tab == 1) {
			text.setTextColor(Color.parseColor("#000000"));
			image.setImageResource(R.drawable.map_grey);
		}

		else if (tab == 2) {
			text.setTextColor(Color.parseColor("#000000"));
			image.setImageResource(R.drawable.setting_greyu);
		}

		else if (tab == 3) {
			text.setTextColor(Color.parseColor("#000000"));
			image.setImageResource(R.drawable.favorites_grey);
		}

		else if (tab == 4) {
			text.setTextColor(Color.parseColor("#000000"));
			image.setImageResource(R.drawable.infos_grey);
		}

	}

	private View getTabIndicator(Context context, String title, int icon) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.blue_tab_layout, null);

		ImageView image = (ImageView) view.findViewById(R.id.image);
		image.setImageResource(icon);
		TextView text = (TextView) view.findViewById(R.id.text);
		text.setText(title);
		return view;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  // refresh your views here
	  super.onConfigurationChanged(newConfig);
	  restartActivity();
	}
	private void restartActivity() {
		Intent intent = getIntent();
	    finish();
	    startActivity(intent);
		
	}

	public static void setParticularTab() {
		mTabHost.setCurrentTab(4);
		Editor e = sp.edit();
		e.putInt("CurrentTab", 4);
		e.commit();
		setTabColor(mTabHost);
		
	} 
}

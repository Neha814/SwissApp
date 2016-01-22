package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.functions.Functions;
import com.example.functions.Lists;
import com.example.swissapp.Contact;
import com.example.swissapp.DatabaseHandler;
import com.example.swissapp.MainActivity;
import com.example.swissapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.imageloader.ImageLoader;


import com.macrew.utils.NetConnection;

public class Detail_Fragment extends Fragment {

	protected static final int INSERT_CONTACT_REQUEST = 0;
	String cat_id, cat_name, subcat_name, subcat_name_eng;
	String subcat_id;
	Boolean isConnected;
	SharedPreferences sp;
	Button back;
	String radius;
	String lat, lng;
	ImageView logo;
	public ImageLoader imageLoader;
	TextView textView4, textView5;
	// TextView company_name, address;
	Button contacts, favourites;
	RelativeLayout phone_layout, email_layout, website_layout, address_layout;
	Boolean noResultFound = false;
	FragmentManager fm;
	SupportMapFragment fragment;
	GoogleMap mGoogleMap;
	ImageView transparent_image;
	ScrollView SS1;
	int screen_width, screen_height;
	private CustomHorizontalScrollView horizontalScrollView;
	ImageView img;

	String bundle_telephone, bundle_email, bundle_url, bundle_company,
			bundle_street, bundle_place;
	String bundle_postcode, bundle_services_en, bundle_services_fr,
			bundle_services_de, bundle_services_it;
	String bundle_description_en, bundle_description_de, bundle_description_fr,
			bundle_description_it;
	String bundle_Latitude_place, bundle_Longitude_place;
	String bundle_logo, bundle_video_link;

	TextView phone_text, email_text, website_text, address_text, services,
			description, video_link;
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_info_fragment,
				container, false);

		address_text = (TextView) rootView.findViewById(R.id.address_text);
		phone_text = (TextView) rootView.findViewById(R.id.phone_text);
		email_text = (TextView) rootView.findViewById(R.id.email_text);
		website_text = (TextView) rootView.findViewById(R.id.website_text);
		services = (TextView) rootView.findViewById(R.id.services);
		description = (TextView) rootView.findViewById(R.id.description);
		back = (Button) rootView.findViewById(R.id.back);
		logo = (ImageView) rootView.findViewById(R.id.logo);
		transparent_image = (ImageView) rootView
				.findViewById(R.id.transparent_image);
		SS1 = (ScrollView) rootView.findViewById(R.id.SS1);
		// company_name = (TextView) rootView.findViewById(R.id.company_name);
		textView4 = (TextView) rootView.findViewById(R.id.textView4);
		contacts = (Button) rootView.findViewById(R.id.contacts);
		favourites = (Button) rootView.findViewById(R.id.favourites);
		phone_layout = (RelativeLayout) rootView
				.findViewById(R.id.phone_layout);
		email_layout = (RelativeLayout) rootView
				.findViewById(R.id.email_layout);
		website_layout = (RelativeLayout) rootView
				.findViewById(R.id.website_layout);
		address_layout = (RelativeLayout) rootView
				.findViewById(R.id.address_layout);
		textView5 = (TextView) rootView.findViewById(R.id.textView5);
		// address = (TextView) rootView.findViewById(R.id.address);
		video_link = (TextView) rootView.findViewById(R.id.video_link);

		final Bundle bundle = getArguments();
		cat_id = bundle.getString("cat_id");
		cat_name = bundle.getString("cat_name");
		subcat_name = bundle.getString("subcat_name");
		subcat_name_eng = bundle.getString("subcat_name_eng");
		subcat_id = bundle.getString("subcat_id");
		radius = bundle.getString("radius");
		lat = bundle.getString("lat");
		lng = bundle.getString("lng");

		bundle_telephone = bundle.getString("Telephone");
		bundle_email = bundle.getString("Email");
		bundle_url = bundle.getString("Url");
		bundle_company = bundle.getString("Company");
		bundle_street = bundle.getString("Street");
		bundle_place = bundle.getString("Place");
		bundle_postcode = bundle.getString("Postcode");
		bundle_services_en = bundle.getString("Services_en");
		bundle_services_fr = bundle.getString("Services_fr");
		bundle_services_de = bundle.getString("Services_de");
		bundle_services_it = bundle.getString("Services_it");
		bundle_description_en = bundle.getString("Description_en");
		bundle_description_de = bundle.getString("Description_de");
		bundle_description_fr = bundle.getString("Description_fr");
		bundle_description_it = bundle.getString("Description_it");
		bundle_logo = bundle.getString("Logo");
		bundle_Latitude_place = bundle.getString("Latitude_place");
		bundle_Longitude_place = bundle.getString("Longitude_place");
		bundle_video_link = bundle.getString("Video_Link");

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		screen_height = displaymetrics.heightPixels;
		screen_width = displaymetrics.widthPixels;

		SpannableString content1 = new SpannableString(bundle_video_link);
		content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);

		video_link.setText(content1);

		// address.setText(subcat_name + " Spenglerei");
		textView5.setText(subcat_name + " - Spenglerei");

		fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment).commit();
		}

		if (mGoogleMap == null) {
			mGoogleMap = fragment.getMap();
		}

		video_link.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog;
				dialog = new Dialog(getActivity(), R.style.full_screen_dialog);
				dialog.setCancelable(true);

				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

				/**
				 * to get screen width and height
				 */

				dialog.setContentView(R.layout.video_layout);
				WebView browser;
				browser = (WebView) dialog.findViewById(R.id.webview);
				String video_id = bundle_video_link.replace(
						"https://vimeo.com/", "");

				Log.e("video_id==", "" + video_id);

				browser.getSettings().setJavaScriptEnabled(true);
				browser.getSettings().setPluginState(PluginState.ON);

				// browser.loadData("<iframe src=\"http://player.vimeo.com/video/"+video_id+"\" width=\""+(screen_width)+"\" height=\""+(screen_height)+"\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>",
				// "text/html", "utf-8");
				// browser.setWebViewClient(new MyBrowser());
				String data_html = "<!DOCTYPE HTML> <html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:og=\"http://opengraphprotocol.org/schema/\" xmlns:fb=\"http://www.facebook.com/2008/fbml\"> <head></head> <body style=\"margin:0 0 0 0; padding:0 0 0 0;\"> <iframe width='"
						+ (screen_width / 2)
						+ "' height='"
						+ ((screen_height - 10) / 2)
						+ "' src=\"http://player.vimeo.com/video/"
						+ video_id
						+ "\" frameborder=\"0\"></iframe> </body> </html> ";

				browser.setWebViewClient(new MyBrowser());

				browser.loadDataWithBaseURL("http://vimeo.com", data_html,
						"text/html", "UTF-8", null);

				dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				dialog.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				dialog.show();

			}
		});

		logo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog;
				dialog = new Dialog(getActivity(), R.style.full_screen_dialog);
				dialog.setCancelable(true);

				LinearLayout LL1;

				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.pic_full_screen);

				LL1 = (LinearLayout) dialog.findViewById(R.id.LL1);
				
				int LL1_width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
				int LL1_height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
				
				

				
				Log.e("dimesions==","width=="+LL1_width+"heigth=="+LL1_height);
				
				int videosSize = Lists.images_list.size();

				horizontalScrollView = new CustomHorizontalScrollView(
						getActivity(), videosSize, LL1_width);
				horizontalScrollView.setVerticalScrollBarEnabled(false);
				horizontalScrollView.setHorizontalScrollBarEnabled(false);
				LL1.addView(horizontalScrollView);
				
				LinearLayout container = new LinearLayout(getActivity());
				container.setLayoutParams(new LayoutParams(LL1_width,
						LL1_height));
				container.removeAllViews();

				// imageLoader.DisplayImage(bundle_logo,
				// R.drawable.background_back, pic);
				
				if (videosSize <= 0) {

					ImageView img1 = new ImageView(getActivity());
					LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(
							LL1_width, LL1_height);
					img1.setLayoutParams(parms);

					img1.setScaleType(ImageView.ScaleType.FIT_XY);

					img1.setBackgroundResource(R.drawable.background_back);
					container.addView(img1);

				}

				else {

					for (int i = 0; i < videosSize; i++) {
						
						Log.i("i videosSize**===", "==" + videosSize);
						img = new ImageView(getActivity());

						
						LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(
								LL1_width, LL1_height);
						img.setLayoutParams(parms);

						img.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
						Log.e("url==",""+Lists.images_list.get(i));
						imageLoader.DisplayImage1(Lists.images_list.get(i),R.drawable.background_back, img ,LL1_width,LL1_height);

						container.addView(img);

					}

				} horizontalScrollView.addView(container);

				dialog.getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

				dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				dialog.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				dialog.show();

			}
		});

		transparent_image.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					// Disallow ScrollView to intercept touch events.
					SS1.requestDisallowInterceptTouchEvent(true);
					// Disable touch on transparent view
					return false;

				case MotionEvent.ACTION_UP:
					// Allow ScrollView to intercept touch events.
					SS1.requestDisallowInterceptTouchEvent(false);
					return true;

				case MotionEvent.ACTION_MOVE:
					SS1.requestDisallowInterceptTouchEvent(true);
					return false;

				default:
					return true;
				}
			}
		});

		phone_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = phone_text.getText().toString();
				if (phone != "" || phone != " " || phone != null) {
					try {
						Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + phone));
						startActivity(callIntent);
					} catch (Exception e) {
						Toast.makeText(getActivity(), "Invalid phone number.",
								1).show();
					}
				} else {
					Toast.makeText(getActivity(), "Phone number is missing.", 1)
							.show();
				}
			}
		});

		email_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { email_text.getText().toString() });
				// emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				// "Put subject here");
				// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
				// "Put the body of email content here");

				emailIntent.setType("plain/text/image/video");

				startActivity(emailIntent);

			}
		});

		website_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(website_text.getText().toString()));
				startActivity(i);

			}
		});

		contacts.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ContentValues values = new ContentValues();
				values.put(People.NUMBER, phone_text.getText().toString());
				values.put(People.TYPE, Phone.TYPE_CUSTOM);
				values.put(People.LABEL, bundle_company);
				values.put(People.PRIMARY_EMAIL_ID, email_text.getText()
						.toString());
				values.put(People.NAME, bundle_company);

				Uri dataUri = MainActivity.appContext.insert(
						People.CONTENT_URI, values);
				Uri updateUri = Uri.withAppendedPath(dataUri,
						People.Phones.CONTENT_DIRECTORY);
				values.clear();
				values.put(People.Phones.TYPE, People.TYPE_MOBILE);
				values.put(People.NUMBER, phone_text.getText().toString());
				updateUri = MainActivity.appContext.insert(updateUri, values);

				Toast.makeText(getActivity(), "Contact added successfully.", 1)
						.show();

			}
		});

		favourites.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				DatabaseHandler db = new DatabaseHandler(getActivity());
				Boolean isExist = db.getContact_comp(subcat_id);

				/**
				 * CRUD Operations
				 * */
				// Inserting Contacts

				Log.d("is already Exist: ", " " + isExist);

				/*
				 * if(isExist){ Toast.makeText(getActivity(),
				 * "Already added to favourites.", 1).show(); }
				 */

				// else {
				Log.d("Insert: ", "Inserting ..");
				Log.d("Name: ", "" + subcat_name_eng);
				Log.d("Phone: ", " " + phone_text.getText().toString());
				Log.d("email: ", " " + email_text.getText().toString());
				Log.d("address: ", " " + address_text.getText().toString());
				Log.d("id: ", " " + subcat_id);
				db.addContact(new Contact(subcat_name_eng, phone_text.getText()
						.toString(), email_text.getText().toString(),
						address_text.getText().toString()));

				Toast.makeText(getActivity(),
						"Added to favourites successfully.", 1).show();
				// }

			}
		});

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				SubListing fragment = new SubListing();
				Bundle bundle = new Bundle();
				bundle.putString("cat_id", cat_id);
				bundle.putString("cat_name", cat_name);

				bundle.putString("cat_id", cat_id);
				bundle.putString("cat_name", cat_name);
				bundle.putString("subcat_name", subcat_name);
				bundle.putString("subcat_name_eng", subcat_name_eng);
				bundle.putString("subcat_id", subcat_id);
				bundle.putString("radius_value", radius);
				bundle.putString("lat", lat);
				bundle.putString("long", lng);

				fragment.setArguments(bundle);
				if (fragment != null) {
					// Replace current fragment by this new one

					ft.replace(android.R.id.tabcontent, fragment);
				} else {
					ft.add(android.R.id.tabcontent, fragment);
				}
				ft.addToBackStack(null);

				ft.commit();

			}
		});

		isConnected = NetConnection.checkInternetConnectionn(getActivity());
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		imageLoader = new ImageLoader(getActivity());

		phone_text.setText(bundle_telephone);
		email_text.setText(bundle_email);
		website_text.setText(bundle_url);
		address_text.setText(bundle_company + ", " + bundle_street + ", "
				+ bundle_place + ", " + bundle_postcode);

		// company_name.setText(bundle_company);
		textView4.setText(bundle_company);

		imageLoader.DisplayImage(bundle_logo, R.drawable.transparent, logo);

		if (sp.getString("language", "english").equalsIgnoreCase("english")) {
			services.setText(bundle_services_en);
			description.setText(bundle_description_en);
		} else if (sp.getString("language", "german")
				.equalsIgnoreCase("german")) {
			services.setText(bundle_services_de);
			description.setText(bundle_description_de);
		} else if (sp.getString("language", "french")
				.equalsIgnoreCase("french")) {
			services.setText(bundle_services_fr);
			description.setText(bundle_description_fr);
		} else if (sp.getString("language", "italian").equalsIgnoreCase(
				"italian")) {
			services.setText(bundle_services_it);
			description.setText(bundle_description_it);
		}

		double lat_temp = Double.parseDouble((String) bundle_Latitude_place);
		double long_temp = Double.parseDouble((String) bundle_Longitude_place);
		LatLng utilis;
		utilis = new LatLng(lat_temp, long_temp);
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(utilis));
		Marker TP = mGoogleMap.addMarker(new MarkerOptions().position(utilis));
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(utilis));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0F));

		return rootView;
	}

	private class MyBrowser extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);
			return true;
		}
	}

	public class CustomHorizontalScrollView extends HorizontalScrollView
			implements OnTouchListener, OnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 300;

		private static final int SWIPE_THRESHOLD_VELOCITY = 300;
		private static final int SWIPE_PAGE_ON_FACTOR = 10;

		private GestureDetector gestureDetector;
		private int scrollTo = 0;
		private int maxItem = 0;
		private int activeItem = 0;
		private float prevScrollX = 0;
		private boolean start = true;
		private int itemWidth = 0;
		private float currentScrollX;
		private boolean flingDisable = true;

		public CustomHorizontalScrollView(Context context) {
			super(context);
			setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));
		}

		public CustomHorizontalScrollView(Context context, int maxItem,
				int itemWidth) {
			this(context);
			this.maxItem = maxItem;
			this.itemWidth = itemWidth;
			gestureDetector = new GestureDetector(this);
			this.setOnTouchListener(this);
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (gestureDetector.onTouchEvent(event)) {
				return true;
			}
			Boolean returnValue = gestureDetector.onTouchEvent(event);

			int x = (int) event.getRawX();

			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if (start) {
					this.prevScrollX = x;
					start = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				start = true;
				this.currentScrollX = x;
				int minFactor = itemWidth / SWIPE_PAGE_ON_FACTOR;

				if ((this.prevScrollX - this.currentScrollX) > minFactor) {
					if (activeItem < maxItem - 1)
						activeItem = activeItem + 1;

				} else if ((this.currentScrollX - this.prevScrollX) > minFactor) {
					if (activeItem > 0)
						activeItem = activeItem - 1;
				}
				System.out.println("horizontal : " + activeItem);
				scrollTo = activeItem * itemWidth;
				this.smoothScrollTo(scrollTo, 0);
				returnValue = true;
				break;
			}
			return returnValue;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (flingDisable)
				return false;
			boolean returnValue = false;
			float ptx1 = 0, ptx2 = 0;
			if (e1 == null || e2 == null)
				return false;
			ptx1 = e1.getX();
			ptx2 = e2.getX();
			// right to left

			Configuration config = getResources().getConfiguration();

			if (ptx1 - ptx2 > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				if (activeItem < maxItem - 1)
					activeItem = activeItem + 1;

				returnValue = true;

			} else if (ptx2 - ptx1 > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				if (activeItem > 0)
					activeItem = activeItem - 1;

				returnValue = true;
			}
			scrollTo = activeItem * itemWidth;
			this.smoothScrollTo(0, scrollTo);
			return returnValue;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
	}


}

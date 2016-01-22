package com.example.fragments;



import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.functions.Functions;
import com.example.swissapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.utils.GPSTracker;

public class MapFragment extends Fragment {

	TextView textView1;
	SupportMapFragment fragment;
	GoogleMap mGoogleMap;
	double latitude;
	double longitude;
	LatLng utilis;
	GPSTracker gps;
	Boolean inMapFirstTime = true;
	FragmentManager fm;
	String cat_id , cat_name;
	String radius;
	ArrayList<HashMap<String, String>> catListingList ;
	View rootView;
	Button back;
	String radius_toSet ;
	
	public MapFragment() {

	}
	
	private void showAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();

					}
				});
		localBuilder.create().show();
	}
	
	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
		localBuilder
				.setMessage(
						"GPS is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Goto Settings Page To Enable GPS",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								
								Intent localIntent2 = new Intent(
										"android.settings.LOCATION_SOURCE_SETTINGS");
								startActivity(localIntent2);
							}
						});
		localBuilder.create().show();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (rootView != null) {
	        ViewGroup parent = (ViewGroup) rootView.getParent();
	        if (parent != null)
	            parent.removeView(rootView);
	    }
		try {
		 rootView = inflater.inflate(R.layout.map_fragment, container,
				false);
		}
		
		
		
		catch(Exception e){
			Log.e("exception e==",""+e);
			return rootView;
		}
		
		textView1 = (TextView) rootView.findViewById(R.id.textView1);
		back = (Button) rootView.findViewById(R.id.back);
		
		gps = new GPSTracker(getActivity());
		
		final Bundle bundle = getArguments();
        cat_id = bundle.getString("cat_id");
        radius = bundle.getString("radius");
        cat_name = bundle.getString("cat_name");
       
        textView1.setText(cat_name);
        
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
		        FragmentTransaction ft = fm.beginTransaction();
		        ListFragment fragment = new ListFragment();
		   
		        if(fragment != null) {
		            // Replace current fragment by this new one
		            
		        	ft.replace(android.R.id.tabcontent, fragment);
		       }
		            else{
		            	 ft.add(android.R.id.tabcontent, fragment);
		            }
		        ft.addToBackStack(null);
		 
		        ft.commit();  
				
			}
		});
        
		 fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment).commit();
		}

		if (mGoogleMap == null) {
			mGoogleMap = fragment.getMap();
		
//			mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())));
			
		}
		
		if(gps.getLatitude()==0.0 && gps.getLongitude() == 0.0){
			showGPSDisabledAlertToUser();
		}
		
		 /**
         * execute web service to gte lat lng to show markers
         */
        
        new catListing(gps.getLongitude(), gps.getLatitude(), radius , cat_id ).execute(new Void[0]);
		
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
		mGoogleMap.setMyLocationEnabled(true);

		
		mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				LatLng Markerlatlong = arg0.getPosition();
				double d1 = Markerlatlong.latitude;
				double d2 = Markerlatlong.longitude;
				
				for (int i = 0; i < catListingList.size(); i++) {
					
					String local_lat = catListingList.get(i).get("Latitude_place");
					String local_lng = catListingList.get(i).get("Longitude_place");
					
					if(local_lat.contains(String.valueOf(d1)) && local_lng.contains(String.valueOf(d2))){
						
						HashMap Map = catListingList.get(i);
						
						Log.e("marker details==",""+Map);
						
						FragmentManager fm = getActivity().getSupportFragmentManager();
				        FragmentTransaction ft = fm.beginTransaction();
				        MapDetail_Fragment fragment = new MapDetail_Fragment();
				        Bundle bundle = new Bundle();
				        bundle.putString("cat_id",cat_id);
				        bundle.putSerializable("HashMap",Map);
				        bundle.putSerializable("cat_name",cat_name);
				        bundle.putString("radius",radius);
				        
				        fragment.setArguments(bundle);
				        if(fragment != null) {
				            // Replace current fragment by this new one
				            
				        	ft.replace(android.R.id.tabcontent, fragment);
				       }
				            else{
				            	 ft.add(android.R.id.tabcontent, fragment);
				            }
				        ft.addToBackStack(null);
				 
				        ft.commit(); 
							 
					}
				}
				return false;
			}
		});

		return rootView;
	}
	
	public class catListing extends AsyncTask<Void, Void, Void> {
		Functions function = new Functions();
		Dialog dialog;
		
		double lng;
		double lat;
		String radius;
		String cat_id;
		
		ArrayList result;
		ArrayList localArrayList = new ArrayList();

		public catListing(double longitude, double latitude, String radius,
				String cat_id) {
			this.lng = longitude;
			this.lat = latitude;
			this.radius = radius;
			this.cat_id = cat_id;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			try {
				localArrayList.add(new BasicNameValuePair("long",String.valueOf(this.lng)));
				localArrayList.add(new BasicNameValuePair("lat",String.valueOf(this.lat)));
				localArrayList.add(new BasicNameValuePair("radius",this.radius));
				localArrayList.add(new BasicNameValuePair("cat_id",this.cat_id));
				result = function.catListing(localArrayList);
				
		
				
			} catch (Exception localException) {

			}

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			dialog.dismiss();
			
			catListingList = new ArrayList<HashMap<String, String>>();
			
			try{
				if(result.size()>0){
				
					catListingList.addAll(result);
					for(int i = 0 ; i< catListingList.size() ; i++){
						Log.i("lat=="+i,""+catListingList.get(i).get("Latitude_place"));
						Log.i("long=="+i,""+catListingList.get(i).get("Longitude_place"));
					
						double lat_temp = Double.parseDouble(catListingList.get(i).get("Latitude_place"));
						double long_temp = Double.parseDouble(catListingList.get(i).get("Longitude_place"));
						radius_toSet = catListingList.get(i).get("Radius");
						textView1.setText("");
						textView1.setText(cat_name+" ("+radius_toSet+")");
						utilis = new LatLng(lat_temp, long_temp);
						mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(utilis));
						Marker TP = mGoogleMap.addMarker(new MarkerOptions()
						.position(utilis).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
						
						
					}
					
					
				}
			}
			
			catch (Exception ae){
				Log.e("Exception==",""+ae);
				showAlertToUser("Something went wrong while processing your request.Please try again.");
			}

		}

		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(getActivity(),
                    "Loading...", "Please Wait", true, false);
			dialog.show();
		}

	}

	
}

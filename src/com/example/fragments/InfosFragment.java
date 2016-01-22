package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragments.MapFragment.catListing;
import com.example.functions.Functions;
import com.example.functions.Lists;
import com.example.swissapp.Contact;
import com.example.swissapp.DatabaseHandler;
import com.example.swissapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class InfosFragment extends Fragment{
	
	TextView textView1 ,h1,h2,h3,v1,v2,v3;
	String info_id;
	int ID;
	SharedPreferences sp;
	TextView address , phone , name;
	
	ArrayList<HashMap<String, String>> infoList;
	String lang;
	
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
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.info_fragment, container, false);
	        
	        address = (TextView) rootView.findViewById(R.id.address);
	        phone = (TextView) rootView.findViewById(R.id.phone);
	        name = (TextView) rootView.findViewById(R.id.name);
	        h1 = (TextView) rootView.findViewById(R.id.h1);
	        h2 = (TextView) rootView.findViewById(R.id.h2);
	        h3 = (TextView) rootView.findViewById(R.id.h3);
	        
	        v1 = (TextView) rootView.findViewById(R.id.v1);
	        v2 = (TextView) rootView.findViewById(R.id.v2);
	        v3 = (TextView) rootView.findViewById(R.id.v3);
	       
	        sp = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
	        new info().execute(new Void[0]);
	      
	        return rootView;
	 }
	 
		public class info extends AsyncTask<Void, Void, Void> {
			Functions function = new Functions();
			Dialog dialog;
			
		
			ArrayList result;
			ArrayList localArrayList = new ArrayList();

		

			protected Void doInBackground(Void... paramVarArgs) {
				try {
					localArrayList.add(new BasicNameValuePair("",""));
				
					result = function.info(localArrayList);
					Log.e("result in info==",""+result);
					Log.i("result size==",""+result.size());
			
					
				} catch (Exception localException) {

				}

				return null;
			}

			protected void onPostExecute(Void paramVoid) {
				dialog.dismiss();
				infoList = new ArrayList<HashMap<String, String>>();
				
				try{
					if(result.size()>0){
						infoList.addAll(result);
						Log.i("infoList size==",""+infoList.size());
						
					lang = sp.getString("language","");
					if (lang.equalsIgnoreCase("english")){
						lang = "en";
					}else if(lang.equalsIgnoreCase("german")){
						lang = "de";
					}else if(lang.equalsIgnoreCase("french")){
						lang = "fr";
					}else if(lang.equalsIgnoreCase("italian")){
						lang = "it";
					}
					else {
						lang = "et";
					}
					
					Log.i("text==",""+infoList.get(0).get("h_"+lang+"0"));
					Log.i("text==",""+"h_"+lang+"0");
				
					h1.setText(infoList.get(0).get("h_"+lang+"0"));
					h2.setText(infoList.get(0).get("h_"+lang+"1"));
					h3.setText(infoList.get(0).get("h_"+lang+"2"));
					
					v1.setText(infoList.get(0).get("v_"+lang+"0"));
					v2.setText(infoList.get(0).get("v_"+lang+"1"));
					v3.setText(infoList.get(0).get("v_"+lang+"2"));
					
					} else {
						
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

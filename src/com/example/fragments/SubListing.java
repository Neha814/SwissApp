package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import com.example.fragments.SubCategories_Fragment.LazyAdapter;
import com.example.fragments.SubCategories_Fragment.ViewHolder;
import com.example.functions.Functions;
import com.example.swissapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.utils.NetConnection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SubListing extends Fragment{
	
	Boolean isConnected;
	String cat_id , cat_name , subcat_name,subcat_name_eng,subcat_id,radius,lat,lng;
	TextView cat_text;
	ListView listview;
	LazyAdapter mAdapter;
	SharedPreferences sp;
	Button back;
	public static ArrayList<HashMap<String, String>> SubCatList;
	
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
		
		View rootView = inflater.inflate(R.layout.subcategory_fragment, container, false);
		
		  isConnected = NetConnection
					.checkInternetConnectionn(getActivity());
		  sp = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
		
		  cat_text = (TextView) rootView.findViewById(R.id.cat_text);
		  listview = (ListView) rootView.findViewById(R.id.listview);
		  back = (Button) rootView.findViewById(R.id.back);
		 
		 final Bundle bundle = getArguments();
	        cat_id = bundle.getString("cat_id");
	        cat_name = bundle.getString("cat_name");
	        subcat_name = bundle.getString("subcat_name");
	        subcat_name_eng = bundle.getString("subcat_name_eng");
	        subcat_id = bundle.getString("subcat_id");
	        radius = bundle.getString("radius_value");
	        lat = bundle.getString("lat");
	        lng = bundle.getString("long");
	        
	        
	        cat_text.setText(subcat_name);
	        
	        listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					FragmentManager fm = getActivity().getSupportFragmentManager();
			        FragmentTransaction ft = fm.beginTransaction();
			        Detail_Fragment fragment = new Detail_Fragment();
			        Bundle bundle = new Bundle();
			        bundle.putString("cat_id",cat_id);
			        bundle.putString("cat_name",cat_name);
			        bundle.putString("subcat_name",subcat_name);
			        bundle.putString("subcat_name_eng",subcat_name_eng);
			        bundle.putString("subcat_id",subcat_id);
			        bundle.putString("radius",radius);
			        bundle.putString("lat",lat);
			        bundle.putString("lng",lng);
			        
			        bundle.putString("Telephone", SubCatList.get(position).get("Telephone"));
			        bundle.putString("Email", SubCatList.get(position).get("Email"));
			        bundle.putString("Url", SubCatList.get(position).get("Url"));
			        bundle.putString("Company", SubCatList.get(position).get("Company"));
			        bundle.putString("Street", SubCatList.get(position).get("Street"));
			        bundle.putString("Place", SubCatList.get(position).get("Place"));
			        bundle.putString("Postcode", SubCatList.get(position).get("Postcode"));
			        bundle.putString("Services_en", SubCatList.get(position).get("Services_en"));
			        bundle.putString("Services_de", SubCatList.get(position).get("Services_de"));
			        bundle.putString("Services_it", SubCatList.get(position).get("Services_it"));
			        bundle.putString("Services_fr", SubCatList.get(position).get("Services_fr"));
			        bundle.putString("Description_en", SubCatList.get(position).get("Description_en"));
			        bundle.putString("Description_de", SubCatList.get(position).get("Description_de"));
			        bundle.putString("Description_fr", SubCatList.get(position).get("Description_fr"));
			        bundle.putString("Description_it", SubCatList.get(position).get("Description_it"));
			        bundle.putString("Logo", SubCatList.get(position).get("Logo"));
			        bundle.putString("Latitude_place", SubCatList.get(position).get("Latitude_place"));
			        bundle.putString("Longitude_place", SubCatList.get(position).get("Longitude_place"));
			        bundle.putString("Video_Link", SubCatList.get(position).get("VideoLink"));
			        
			        Log.i("bundle vidoe linkl==",""+SubCatList.get(position).get("VideoLink"));
			     
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
			});
	        
	        back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fm = getActivity().getSupportFragmentManager();
			        FragmentTransaction ft = fm.beginTransaction();
			        SubCategories_Fragment fragment = new SubCategories_Fragment();
			        Bundle bundle = new Bundle();
			        bundle.putString("cat_id",cat_id);
			        bundle.putString("cat_name",cat_name);
			     
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
			});
	        
	        if (isConnected == true) {
	            new listing(subcat_id,radius,lat,lng).execute(new Void[0]);
	            } else {
	            	showAlertToUser("No internet connection");
	            }
				return rootView;
		
		
	}
	
	public class listing extends AsyncTask<Void, Void, Void> {
		Functions function = new Functions();
		Dialog dialog;
		
		
		String subcat_id;
		String radius;
		String lat;
		String lng;
		
		ArrayList<HashMap<String, String>> result;
		ArrayList localArrayList = new ArrayList();

		public listing(String subcat_id,String radius, String lat, String lng) {
			this.subcat_id = subcat_id;
			this.radius = radius;
			this.lat = lat;
			this.lng = lng;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			try {
				localArrayList.add(new BasicNameValuePair("subcat",this.subcat_id));
				localArrayList.add(new BasicNameValuePair("radius",this.radius));
				localArrayList.add(new BasicNameValuePair("lat",this.lat));
				localArrayList.add(new BasicNameValuePair("long",this.lng));
				
				result = function.listing(localArrayList);
				
			} catch (Exception localException) {

			}

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			dialog.dismiss();
			Log.e("result in post==",""+result);
			try{
				if(result.size()>0){
					mAdapter = new LazyAdapter(result, getActivity());
					listview.setAdapter(mAdapter);
				}
				
				else {
					showAlertToUser("No List found");
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
	class LazyAdapter extends BaseAdapter {

		LayoutInflater mInflater = null;

		public LazyAdapter(ArrayList<HashMap<String, String>> result, FragmentActivity activity) {
			SubCatList = new ArrayList<HashMap<String,String>>();
			SubCatList.addAll(result);
			mInflater = LayoutInflater.from(activity);
		}

		@Override
		public int getCount() {

			return SubCatList.size();
		}

		@Override
		public Object getItem(int position) {

			return SubCatList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {

				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.fav_listitem,
						null);
				
				holder.comp_name = (TextView) convertView.findViewById(R.id.comp_name);
				holder.address = (TextView) convertView.findViewById(R.id.address);
				holder.distance = (TextView) convertView.findViewById(R.id.distance);
				
				convertView.setTag(holder);

			}

			else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.comp_name.setText(SubCatList.get(position).get("Listname"));
			holder.address.setText(SubCatList.get(position).get("Company")+", "+SubCatList.get(position).get("Street")+", "+
					SubCatList.get(position).get("Place")+", "+SubCatList.get(position).get("Postcode"));
			holder.distance.setText(SubCatList.get(position).get("Radius"));
			
			return convertView;
		}

	}

	/****************** ENDING OF ADAPTER CLASS ************************************/
	class ViewHolder {
		TextView comp_name ,address, distance;
}
	
}

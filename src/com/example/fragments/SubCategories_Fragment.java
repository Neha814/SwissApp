package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import com.example.fragments.ListFragment.LazyAdapter;

import com.example.fragments.ListFragment.cat_list;
import com.example.functions.Functions;
import com.example.functions.Lists;
import com.example.swissapp.R;
import com.macrew.utils.GPSTracker;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SubCategories_Fragment extends Fragment{
	
	ListView listview;
	LazyAdapter mAdapter;
	SharedPreferences sp;
	Boolean isConnected;
	public static ArrayList<HashMap<String, String>> SubCatList;
	String cat_id , cat_name;
	TextView cat_text;
	Button back;
	GPSTracker gps;
	
	
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
        
        gps = new GPSTracker(getActivity());
        
       
        
        listview = (ListView) rootView.findViewById(R.id.listview);
        cat_text = (TextView) rootView.findViewById(R.id.cat_text);
        back = (Button) rootView.findViewById(R.id.back);
        isConnected = NetConnection
				.checkInternetConnectionn(getActivity());
        sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
        
        final Bundle bundle = getArguments();
        cat_id = bundle.getString("cat_id");
        cat_name = bundle.getString("cat_name");
        
        cat_text.setText(cat_name);
       
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				double latitude;
				double longitude; 
				String radius_value;
				String subcat_name = "";
				
				 latitude = gps.getLatitude();
			     longitude = gps.getLongitude();
			     
			     int radius = sp.getInt("radius", 50);
					if(radius==50){
						radius_value = "all";
					}else {
						radius_value = String.valueOf(sp.getInt("radius", 50));
					}
					
					if(sp.getString("language", "english").equalsIgnoreCase("english")){
						 subcat_name= SubCatList.get(position).get("en");
			        }else if(sp.getString("language", "german").equalsIgnoreCase("german")){
			        	subcat_name= SubCatList.get(position).get("de");
			        }else if(sp.getString("language", "french").equalsIgnoreCase("french")){
			        	subcat_name= SubCatList.get(position).get("fr");
			        }else if(sp.getString("language", "italian").equalsIgnoreCase("italian")){
			        	subcat_name= SubCatList.get(position).get("it");
			        }
					
				String subcat_id = SubCatList.get(position).get("id");
				Log.e("subcat_id", ""+subcat_id);
				FragmentManager fm = getActivity().getSupportFragmentManager();
		        FragmentTransaction ft = fm.beginTransaction();
		        if(sp.getInt("current_tab", 0)==0){
		        	SubListing fragment = new SubListing();
		        	
		        	Bundle bundle = new Bundle();
			        bundle.putString("cat_id",cat_id);
			        bundle.putString("subcat_id",subcat_id);
			        bundle.putString("radius_value",radius_value);
			        bundle.putString("lat",String.valueOf(latitude));
			        bundle.putString("long",String.valueOf(longitude));
			        bundle.putString("cat_name",cat_name);
			        bundle.putString("subcat_name",subcat_name);
			        bundle.putString("subcat_name_eng",SubCatList.get(position).get("en"));
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
		});
        
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
        
       
        if (isConnected == true) {
        new subCat_list(cat_id).execute(new Void[0]);
        } else {
        	showAlertToUser("No internet connection");
        }
		return rootView;
        
	}
	public class subCat_list extends AsyncTask<Void, Void, Void> {
		Functions function = new Functions();
		Dialog dialog;
		
		String cat_id;
		
		ArrayList result;
		ArrayList localArrayList = new ArrayList();

		public subCat_list(String cat_id) {
			this.cat_id = cat_id;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			try {
				localArrayList.add(new BasicNameValuePair("cat_id",this.cat_id));
				result = function.subCat_list(localArrayList);
				
			} catch (Exception localException) {

			}

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			dialog.dismiss();
			
			try{
			
			if (result.size()>0) {
			
				mAdapter = new LazyAdapter(result, getActivity());
				listview.setAdapter(mAdapter);
			}
			
		else {
			showAlertToUser("No result found.Please try again.");
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
				convertView = mInflater.inflate(R.layout.list_item_listtab,
						null);
				
				holder.text = (TextView) convertView.findViewById(R.id.text);
				
				convertView.setTag(holder);

			}

			else {
				holder = (ViewHolder) convertView.getTag();
			}
			if(sp.getString("language", "").equals("english")){
				holder.text.setText(SubCatList.get(position).get("en"));
			}else if(sp.getString("language", "").equals("german")){
				holder.text.setText(SubCatList.get(position).get("de"));	
			}else if(sp.getString("language", "").equals("french")){
				holder.text.setText(SubCatList.get(position).get("fr"));	
			}else if(sp.getString("language", "").equals("italian")){
				holder.text.setText(SubCatList.get(position).get("it"));	
			}else {
				holder.text.setText(SubCatList.get(position).get("en"));
			}
			
			
			return convertView;
		}

	}

	/****************** ENDING OF ADAPTER CLASS ************************************/
	class ViewHolder {
		TextView text;
}
	
}


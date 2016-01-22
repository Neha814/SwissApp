package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


import com.example.functions.Functions;
import com.example.functions.Lists;
import com.example.swissapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import com.macrew.imageloader.ImageLoader;
import com.macrew.utils.GPSTracker;
import com.macrew.utils.NetConnection;


public class ListFragment extends Fragment{
	
	ListView listview;
	LazyAdapter mAdapter;
	SharedPreferences sp;
	Boolean isConnected;
	public ImageLoader imageLoader;
	EditText search;
	
	
	public ArrayList<HashMap<String, String>> local_cat_list;
	
	
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
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        
        listview = (ListView) rootView.findViewById(R.id.listview);
        search = (EditText) rootView.findViewById(R.id.search);
        
       
        
        search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				String text = search.getText().toString()
						.toLowerCase(Locale.getDefault());
				mAdapter.filter(text);
				
			}
		});
        
        isConnected = NetConnection
				.checkInternetConnectionn(getActivity());
        sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
        
        imageLoader = new ImageLoader(getActivity());
        
        if (isConnected == true) {
        	if(Lists.CatList.equals(null)||Lists.CatList.size()<=0){
        new cat_list().execute(new Void[0]);
        	}
        	else {
        		mAdapter = new LazyAdapter(Lists.CatList, getActivity());
				listview.setAdapter(mAdapter);	
        	}
        } else {
        	showAlertToUser("No internet connection");
        }
        
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				
				
				String cat_id;
				String subcat;
				String radius_value;
				String cat_name = "";
				
				cat_id = Lists.CatList.get(position).get("cat_id");
				subcat = Lists.CatList.get(position).get("subcat");
				
				int radius = sp.getInt("radius", 50);
				if(radius==50){
					radius_value = "all";
				}else {
					radius_value = String.valueOf(sp.getInt("radius", 50));
				}
				 if(sp.getString("language", "english").equalsIgnoreCase("english")){
					 cat_name= Lists.CatList.get(position).get("cat_eng");
		        }else if(sp.getString("language", "german").equalsIgnoreCase("german")){
		        	 cat_name= Lists.CatList.get(position).get("cat_german");
		        }else if(sp.getString("language", "french").equalsIgnoreCase("french")){
		        	 cat_name= Lists.CatList.get(position).get("cat_french");
		        }else if(sp.getString("language", "italian").equalsIgnoreCase("italian")){
		        	 cat_name= Lists.CatList.get(position).get("cat_it");
		        }
				
				
				Log.e("cat_id", ""+cat_id);
				if(subcat.equalsIgnoreCase("yes")){
					  if(sp.getInt("current_tab", 0)==0){
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
					  
					  else if(sp.getInt("current_tab", 0)==1){
						  FragmentManager fm = getActivity().getSupportFragmentManager();
					        FragmentTransaction ft = fm.beginTransaction();
					        MapFragment fragment = new MapFragment();
					        Bundle bundle = new Bundle();
					        bundle.putString("cat_id",cat_id);
					        bundle.putString("cat_name",cat_name);
					        bundle.putString("radius",radius_value);
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
				
				} else {
					showAlertToUser("No sub-category found.");
				}
			}
		});
        
        return rootView;
 }
	public class cat_list extends AsyncTask<Void, Void, Void> {
		Functions function = new Functions();
		Dialog dialog;
		
		ArrayList result;
		ArrayList localArrayList = new ArrayList();

		protected Void doInBackground(Void... paramVarArgs) {
			try {
				localArrayList.add(new BasicNameValuePair("",""));
				result = function.cat_list(localArrayList);
				
			} catch (Exception localException) {

			}

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			dialog.dismiss();
			
			try{
			
			if (result.size()>0) {
				Lists.CatList.addAll(result);
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
		private ArrayList<HashMap<String, String>> mDisplayedValues;

		public LazyAdapter(ArrayList<HashMap<String, String>> result, FragmentActivity activity) {
			local_cat_list = new ArrayList<HashMap<String,String>>();
			local_cat_list.addAll(result);
			mDisplayedValues = new ArrayList<HashMap<String, String>>();
			mDisplayedValues.addAll(local_cat_list);
			mInflater = LayoutInflater.from(activity);
		}

		public void filter(String charText) {
		
				String langToShow = "";
				charText = charText.toLowerCase(Locale.getDefault());

				local_cat_list.clear();
				if (charText.length() == 0) {

					local_cat_list.addAll(mDisplayedValues);
				} else {
					
					String language = sp.getString("language", "");
					if(language.equalsIgnoreCase("english")){
						langToShow = "cat_eng";
					}else if(language.equalsIgnoreCase("german")){
						langToShow = "cat_german";
					} else if(language.equalsIgnoreCase("french")){
						langToShow = "cat_french";
					}else if(language.equalsIgnoreCase("italian")){
						langToShow = "cat_it";
					}
					for (int i = 0; i <mDisplayedValues.size() ; i++) {
						
						
						if (mDisplayedValues.get(i).get(langToShow).toLowerCase(Locale.getDefault())
								.startsWith(charText)) {

							local_cat_list.add(mDisplayedValues.get(i));

						}
					}
				}
				notifyDataSetChanged();

			
			
		}

		@Override
		public int getCount() {

			return local_cat_list.size();
		}

		@Override
		public Object getItem(int position) {

			return local_cat_list.get(position);
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
				convertView = mInflater.inflate(R.layout.cat_listitem,
						null);

				holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				
				convertView.setTag(holder);

			}

			else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			String image_name = local_cat_list.get(position).get("cat_icon");
			
			image_name = image_name.replace("-", "_");
			image_name = image_name.replace(".png", "");
			String uri = "@drawable/"+image_name;
			
			
			int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
			

			Drawable res = getResources().getDrawable(imageResource);
			holder.image.setImageDrawable(res);
			if(sp.getString("language", "").equals("english")){
				holder.text.setText(local_cat_list.get(position).get("cat_eng"));
			}else if(sp.getString("language", "").equals("german")){
				holder.text.setText(local_cat_list.get(position).get("cat_german"));	
			}else if(sp.getString("language", "").equals("french")){
				holder.text.setText(local_cat_list.get(position).get("cat_french"));	
			}else if(sp.getString("language", "").equals("italian")){
				holder.text.setText(local_cat_list.get(position).get("cat_it"));	
			}else{
				holder.text.setText(local_cat_list.get(position).get("cat_eng"));
			}
			
			
			return convertView;
		}

	}

	/****************** ENDING OF ADAPTER CLASS ************************************/
	class ViewHolder {
		TextView text;
		ImageView image;
}
	
}



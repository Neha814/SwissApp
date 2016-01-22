package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.functions.Lists;
import com.example.swissapp.Contact;
import com.example.swissapp.DatabaseHandler;
import com.example.swissapp.MainActivity;
import com.example.swissapp.R;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

public class FavoritesFragment extends Fragment{
	
	TextView textView1;
	LazyAdapter mAdapter;
	SwipeListView listview;
	private ArrayList<HashMap<String , String>> Contacts_to_display = new ArrayList<HashMap<String , String>>();
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.favourites_fragment, container, false);
	        
	        listview = (SwipeListView) rootView.findViewById(R.id.listview);
	       
	        
	        DatabaseHandler db = new DatabaseHandler(getActivity());
	        // Reading all contacts
	        Log.d("Reading: ", "Reading all contacts.."); 
	        List<Contact> contacts = db.getAllContacts();   
	        Log.i("contacts: ",""+contacts);
	       
	        Contacts_to_display.clear();
	        for (Contact cn : contacts) {
	        	HashMap localHashMap = new HashMap();
	            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.getEmail()
	            		+ " ,Address: " + cn.getAddress();;
	                // Writing Contacts to log
	       
	        
	        localHashMap.put("ID", cn.getID());
	        localHashMap.put("Name", cn.getName());
	        localHashMap.put("Phone", cn.getPhoneNumber());
	        localHashMap.put("Email", cn.getEmail());
	        localHashMap.put("Address", cn.getAddress());
	        
	        Contacts_to_display.add(localHashMap);
	        }
	        
	        mAdapter = new LazyAdapter(Contacts_to_display, getActivity());
	        
	       
	        
	        
	        
	        listview.setSwipeListViewListener(new BaseSwipeListViewListener() {
	            @Override
	            public void onOpened(int position, boolean toRight) {
	            }

	            @Override
	            public void onClosed(int position, boolean fromRight) {
	            }

	            @Override
	            public void onListChanged() {
	            }

	            @Override
	            public void onMove(int position, float x) {
	            }

	            @Override
	            public void onStartOpen(int position, int action, boolean right) {
	                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
	            }

	            @Override
	            public void onStartClose(int position, boolean right) {
	                Log.d("swipe", String.format("onStartClose %d", position));
	            }

	            @Override
	            public void onClickFrontView(int position) {
	                Log.d("swipe", String.format("onClickFrontView %d", position));
	                
	             
	                listview.openAnimate(position); //when you touch front view it will open
	              
	             
	            }

	            @Override
	            public void onClickBackView(int position) {
	                Log.d("swipe", String.format("onClickBackView %d", position));
	                
	                listview.closeAnimate(position);//when you touch back view it will close
	            }

	            @Override
	            public void onDismiss(int[] reverseSortedPositions) {
	            	
	            }

	        });
	        
	        //These are the swipe listview settings. you can change these
	        //setting as your requirement 
	        listview.setSwipeMode(SwipeListView.SWIPE_MODE_BOTH); // there are five swiping modes
	        listview.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_DISMISS); //there are four swipe actions 
	        listview.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
	        listview.setOffsetLeft(convertDpToPixel(0f)); // left side offset
	        listview.setOffsetRight(convertDpToPixel(80f)); // right side offset
	        listview.setAnimationTime(500); // Animation time
	        listview.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
	        
	        listview.setAdapter(mAdapter);
	        mAdapter.notifyDataSetChanged();
	        
	        return rootView;
	 }
	 
	 public int convertDpToPixel(float dp) {
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        float px = dp * (metrics.densityDpi / 160f);
	        return (int) px;
	    }
	 
	 class LazyAdapter extends BaseAdapter {

			LayoutInflater mInflater = null;
			
			public LazyAdapter(ArrayList<HashMap<String , String>> Contacts_to_display, FragmentActivity fav) {
				// TODO Auto-generated constructor stub
				mInflater = LayoutInflater.from(fav);
			
			
			}

			@Override
			public int getCount() {
				return Contacts_to_display.size();
			}

			@Override
			public Object getItem(int position) {

				return Contacts_to_display.get(position);
			}

			@Override
			public long getItemId(int position) {

				return position;
			}

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {

				final ViewHolder holder;

				if (convertView == null) {
					holder = new ViewHolder();
					
					//convertView = mInflater.inflate(R.layout.fav_listitem, null);
					convertView = mInflater.inflate(R.layout.custom_row, null);
					holder.comp_name = (TextView) convertView.findViewById(R.id.comp_name);
					holder.address = (TextView) convertView.findViewById(R.id.address);
					holder.distance = (TextView) convertView.findViewById(R.id.distance);
					holder.swipe_button1 = (Button) convertView.findViewById(R.id.swipe_button1);
					holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
					holder.back = (LinearLayout) convertView.findViewById(R.id.back);
					
					convertView.setTag(holder);

				} else {
					holder = (ViewHolder) convertView.getTag();

				}
				holder.swipe_button1.setTag(position);
				holder.arrow.setTag(position);
				holder.back.setTag(position);
				holder.comp_name.setText(Contacts_to_display.get(position).get("Name")+String.valueOf(Contacts_to_display.get(position).get("ID")));
				holder.address.setText(Contacts_to_display.get(position).get("Address"));
				holder.distance.setText("4.2km"+String.valueOf(position));
				
				holder.arrow.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int tag_temp = (Integer) v.getTag();
						Lists.info_id = Contacts_to_display.get(tag_temp).get("Name");
						Log.e("info id==",""+Lists.info_id);
		        		MainActivity.setParticularTab();
					}
				});
				
				
				holder.swipe_button1.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int tag_temp = (Integer) v.getTag();
						Log.i("tag_temp==",""+tag_temp);
						
						String name = String.valueOf(Contacts_to_display.get(tag_temp).get("Name"));
						Log.i("name to del==",""+name);
						DatabaseHandler db = new DatabaseHandler(getActivity());
						db.deleteContact(name);
						Contacts_to_display.remove(tag_temp);
//						if((Integer)holder.front.getTag()==tag_temp){
//							holder.back.removeViewAt(tag_temp);
//							holder.front.removeViewAt(tag_temp);
//						}
						listview.setSwipeCloseAllItemsWhenMoveList(true);
						mAdapter.notifyDataSetChanged();
					}
				});
			
				return convertView;
			}

		}

		class ViewHolder {
			TextView comp_name, address , distance;
			Button swipe_button1;
			ImageView arrow;
			LinearLayout back;
			
		}
}

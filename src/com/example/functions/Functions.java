package com.example.functions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.Html;
import android.util.Log;

public class Functions {
	JSONParser json = new JSONParser();
	public static String uri = "http://dev.macrew.net/swissapp/api/";
	
	/**
	 * 
	 * To get categories
	 *
	 */

	public ArrayList<HashMap<String, String>> cat_list(ArrayList localArrayList) {
		ArrayList<HashMap<String, String>> locallist = new ArrayList<HashMap<String, String>>();
		HashMap localHashMap = new HashMap();
		try {

			JSONObject localJSONObject = new JSONObject(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "cat_new.php?", "POST",
							localArrayList)).toString());

			if (localJSONObject.getString("status").equalsIgnoreCase("FALSE")) {

				return locallist;
			} else if (localJSONObject.getString("status").equalsIgnoreCase(
					"TRUE")) {
				localHashMap.put("status", localJSONObject.getString("status"));
				JSONArray localJSONarray = localJSONObject.getJSONArray("data");
				for (int i = 0; i < localJSONarray.length(); i++) {
					HashMap localHashMap2 = new HashMap();
					localHashMap2.put("cat_id", localJSONarray.getJSONObject(i)
							.get("id"));
					localHashMap2.put("cat_eng", localJSONarray
							.getJSONObject(i).get("en"));
					localHashMap2.put("cat_german", localJSONarray
							.getJSONObject(i).get("de"));
					localHashMap2.put("cat_french", localJSONarray
							.getJSONObject(i).get("fr"));
					localHashMap2.put("cat_it", localJSONarray.getJSONObject(i)
							.get("it"));
					
					localHashMap2.put("subcat", localJSONarray.getJSONObject(i)
							.get("subcat"));
					localHashMap2.put("cat_icon", localJSONarray.getJSONObject(i)
							.get("cat_icon"));

					locallist.add(localHashMap2);
				}
				return locallist;
			}

		} catch (Exception ae) {
			Log.e("ae==", "" + ae);

			return locallist;

		}
		return locallist;

	}
	
	/**
	 * 
	 * to get sub categories
	 * 
	 */
	
	public ArrayList<HashMap<String, String>> subCat_list(ArrayList localArrayList) {
		ArrayList<HashMap<String, String>> locallist = new ArrayList<HashMap<String, String>>();
		HashMap localHashMap = new HashMap();
		try {

			JSONObject localJSONObject = new JSONObject(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "subcat.php?", "POST",
							localArrayList)).toString());

			if (localJSONObject.getString("status").equalsIgnoreCase("FALSE")) {

				return locallist;
			} else if (localJSONObject.getString("status").equalsIgnoreCase(
					"TRUE")) {
				localHashMap.put("status", localJSONObject.getString("status"));
				JSONArray localJSONarray = localJSONObject.getJSONArray("data");
				for (int i = 0; i < localJSONarray.length(); i++) {
					HashMap localHashMap2 = new HashMap();
					localHashMap2.put("id", localJSONarray.getJSONObject(i)
							.get("id"));
					localHashMap2.put("en", localJSONarray
							.getJSONObject(i).get("en"));
					localHashMap2.put("de", localJSONarray
							.getJSONObject(i).get("de"));
					localHashMap2.put("fr", localJSONarray
							.getJSONObject(i).get("fr"));
					localHashMap2.put("it", localJSONarray.getJSONObject(i)
							.get("it"));
					

					locallist.add(localHashMap2);
				}
				return locallist;
			}

		} catch (Exception ae) {
			Log.e("ae==", "" + ae);

			return locallist;

		}
		return locallist;

	}
	
	/**
	 * 
	 * to get sub cat listing details
	 * 
	 */
	
	public ArrayList<HashMap<String, String>> listing(ArrayList localArrayList) {
		//ArrayList<HashMap<String, String>> locallist = new ArrayList<HashMap<String, String>>();
		HashMap localHashMap = new HashMap();
		ArrayList<HashMap<String, String>> locallist = new ArrayList<HashMap<String, String>>();
		try {

			JSONObject localJSONObject = new JSONObject(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "subcatlisting.php?", "POST",
							localArrayList)).toString());

			if (localJSONObject.getString("status").equalsIgnoreCase("FALSE")) {
				
				localHashMap.put("status", "false");
				
				return locallist;
			} else if (localJSONObject.getString("status").equalsIgnoreCase(
					"TRUE")) {
				
				JSONArray localJSONarray = localJSONObject.getJSONArray("data");
				for (int i = 0; i < localJSONarray.length(); i++) {
					HashMap localHashMap2 = new HashMap();
					
					JSONObject listing = localJSONarray.getJSONObject(i).getJSONObject("Listing");
					
					
					JSONArray logos_array  = localJSONarray.getJSONObject(i).getJSONArray("Logo");
					
					Lists.images_list.clear();
					for(int j = 0 ; j<logos_array.length();j++){
						localHashMap2.put("Logo"+j, logos_array.getString(j));
						
						Lists.images_list.add(logos_array.getString(j));
					
					}
					
					localHashMap2.put("status", "true");
					localHashMap2.put("ID", listing.getString("ID"));
					localHashMap2.put("Listname", listing.getString("Listname"));
					localHashMap2.put("Logo", listing.getString("Logo"));
					localHashMap2.put("Url", listing.getString("Url"));
					localHashMap2.put("VideoLink", listing.getString("VideoLink"));
					localHashMap2.put("Email", listing.getString("Email"));
					localHashMap2.put("Telephone", listing.getString("Telephone"));
					
					JSONObject address = localJSONarray.getJSONObject(i).getJSONObject("Address");
					localHashMap2.put("Company", address.getString("Company"));
					localHashMap2.put("Street", address.getString("Street"));
					localHashMap2.put("address_Telephone", address.getString("Telephone"));
					localHashMap2.put("Fax", address.getString("Fax"));
					localHashMap2.put("Postcode", address.getString("Postcode"));
					localHashMap2.put("Place", address.getString("Place"));
					localHashMap2.put("Radius", address.getString("Radius"));
					
					JSONObject radius = localJSONarray.getJSONObject(i).getJSONObject("Focus");
					localHashMap2.put("Latitude_focus", radius.getString("Latitude"));
					localHashMap2.put("Longitude_focus", radius.getString("Longitude"));
					
					JSONObject place = localJSONarray.getJSONObject(i).getJSONObject("Place");
					localHashMap2.put("Latitude_place", place.getString("Latitude"));
					localHashMap2.put("Longitude_place", place.getString("Longitude"));
					
					JSONObject en = localJSONarray.getJSONObject(i).getJSONObject("en");
					localHashMap2.put("Services_en", en.getString("Services"));
					localHashMap2.put("Other-services_en", en.getString("Other-services"));
					localHashMap2.put("Description_en", en.getString("Description"));
					
					JSONObject it = localJSONarray.getJSONObject(i).getJSONObject("it");
					localHashMap2.put("Services_it", it.getString("Services"));
					localHashMap2.put("Other-services_it", it.getString("Other-services"));
					localHashMap2.put("Description_it", it.getString("Description"));
					
					JSONObject fr = localJSONarray.getJSONObject(i).getJSONObject("fr");
					localHashMap2.put("Services_fr", fr.getString("Services"));
					localHashMap2.put("Other-services_fr", fr.getString("Other-services"));
					localHashMap2.put("Description_fr", fr.getString("Description"));
					
					JSONObject de = localJSONarray.getJSONObject(i).getJSONObject("de");
					localHashMap2.put("Services_de", de.getString("Services"));
					localHashMap2.put("Other-services_de", de.getString("Other-services"));
					localHashMap2.put("Description_de", de.getString("Description"));
					
					locallist.add(localHashMap2);
					
				}
				return locallist;
			}

		} catch (Exception ae) {
			Log.e("ae==", "" + ae);
			localHashMap.put("status", "error");
			return locallist;
			

		}
		localHashMap.put("status", "error");
		return locallist;

	}

	/**
	 * 
	 * to get sub cat listing details
	 * 
	 */
	
	public ArrayList catListing(ArrayList localArrayList) {
		ArrayList<HashMap<String, String>> locallist = new ArrayList<HashMap<String, String>>();
		HashMap localHashMap = new HashMap();
		try {

			JSONObject localJSONObject = new JSONObject(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "catlisting.php?", "POST",
							localArrayList)).toString());

			if (localJSONObject.getString("status").equalsIgnoreCase("FALSE")) {
				
				localHashMap.put("status", "false");
				
				return locallist;
			} else if (localJSONObject.getString("status").equalsIgnoreCase(
					"TRUE")) {
				
				JSONArray localJSONarray = localJSONObject.getJSONArray("data");
				Log.e("array size==",""+localJSONarray.length());
				for (int i = 0; i < localJSONarray.length(); i++) {
					HashMap localHashMap2 = new HashMap();
					
					
					JSONObject listing = localJSONarray.getJSONObject(i).getJSONObject("Listing");
					
					
					JSONArray logos_array  = localJSONarray.getJSONObject(i).getJSONArray("Logo");
					
					Lists.images_list_map.clear();
					for(int j = 0 ; j<logos_array.length();j++){
						localHashMap2.put("Logo"+j, logos_array.getString(j));
						
						Lists.images_list_map.add(logos_array.getString(j));
					
					}
					
					localHashMap2.put("status", "true");
					localHashMap2.put("ID", listing.getString("ID"));
					localHashMap2.put("Listname", listing.getString("Listname"));
					
					localHashMap2.put("Url", listing.getString("Url"));
					localHashMap2.put("Video_Link", listing.getString("Video Link"));
					localHashMap2.put("Email", listing.getString("Email"));
					localHashMap2.put("Telephone", listing.getString("Telephone"));
					
					JSONObject address = localJSONarray.getJSONObject(i).getJSONObject("Address");
					localHashMap2.put("Company", address.getString("Company"));
					localHashMap2.put("Street", address.getString("Street"));
					localHashMap2.put("Telephone", address.getString("Telephone"));
					localHashMap2.put("Fax", address.getString("Fax"));
					localHashMap2.put("Postcode", address.getString("Postcode"));
					localHashMap2.put("Place", address.getString("Place"));
					localHashMap2.put("Radius", address.getString("Radius"));
					
					JSONObject radius = localJSONarray.getJSONObject(i).getJSONObject("Focus");
					localHashMap2.put("Latitude_focus", radius.getString("Latitude"));
					localHashMap2.put("Longitude_focus", radius.getString("Longitude"));
					
					JSONObject place = localJSONarray.getJSONObject(i).getJSONObject("Place");
					localHashMap2.put("Latitude_place", place.getString("Latitude"));
					localHashMap2.put("Longitude_place", place.getString("Longitude"));
					
					JSONObject en = localJSONarray.getJSONObject(i).getJSONObject("en");
					localHashMap2.put("Services_en", en.getString("Services"));
					localHashMap2.put("Other-services_en", en.getString("Other-services"));
					localHashMap2.put("Description_en", en.getString("Description"));
					localHashMap2.put("Cat_en", en.getString("Cat"));
					localHashMap2.put("SubCat_en", en.getString("SubCat"));
					
					JSONObject it = localJSONarray.getJSONObject(i).getJSONObject("it");
					localHashMap2.put("Services_it", it.getString("Services"));
					localHashMap2.put("Other-services_it", it.getString("Other-services"));
					localHashMap2.put("Description_it", it.getString("Description"));
					localHashMap2.put("Cat_it", it.getString("Cat"));
					localHashMap2.put("SubCat_it", it.getString("SubCat"));
					
					JSONObject fr = localJSONarray.getJSONObject(i).getJSONObject("fr");
					localHashMap2.put("Services_fr", fr.getString("Services"));
					localHashMap2.put("Other-services_fr", fr.getString("Other-services"));
					localHashMap2.put("Description_fr", fr.getString("Description"));
					localHashMap2.put("Cat_fr", fr.getString("Cat"));
					localHashMap2.put("SubCat_fr", fr.getString("SubCat"));
					
					JSONObject de = localJSONarray.getJSONObject(i).getJSONObject("de");
					localHashMap2.put("Services_de", de.getString("Services"));
					localHashMap2.put("Other-services_de", de.getString("Other-services"));
					localHashMap2.put("Description_de", de.getString("Description"));
					localHashMap2.put("Cat_de", de.getString("Cat"));
					localHashMap2.put("SubCat_de", de.getString("SubCat"));
					
					
					locallist.add(localHashMap2);
					
					
				}
				return locallist;
			}

		} catch (Exception ae) {
			Log.e("ae==", "" + ae);
			localHashMap.put("status", "error");
			return locallist;
			

		}
		localHashMap.put("status", "error");
		return locallist;

	}
	
	/**
	 * 
	 * web servie to get info
	 * 
	 */
	public ArrayList info(ArrayList localArrayList) {
		ArrayList<HashMap<String, String>> locallist = new ArrayList<HashMap<String, String>>();
		HashMap localHashMap = new HashMap();
		try {

			JSONObject localJSONObject = new JSONObject(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "info.php?", "POST",
							localArrayList)).toString());

			if (localJSONObject.getString("status").equalsIgnoreCase("FALSE")) {
				
				localHashMap.put("status", "false");
				
				return locallist;
			} else if (localJSONObject.getString("status").equalsIgnoreCase(
					"TRUE")) {
				
				JSONArray data = localJSONObject.getJSONArray("data");
				
				for (int i = 0; i < data.length(); i++) {
					HashMap localHashMap2 = new HashMap();
					
					
					JSONArray en = data.getJSONObject(i).getJSONArray("en");
					
					for(int j=0;j<en.length();j++){
					
					JSONObject array = en.getJSONObject(j);
					localHashMap2.put("status", "true");
					localHashMap2.put("h_en"+j, array.get("h"));
					localHashMap2.put("v_en"+j, array.get("v"));
				
			
					}
					
					JSONArray de = data.getJSONObject(i).getJSONArray("de");
					
					for(int j=0;j<en.length();j++){
					
					JSONObject array = de.getJSONObject(j);
					localHashMap2.put("status", "true");
					localHashMap2.put("h_de"+j, array.get("h"));
					localHashMap2.put("v_de"+j, array.get("v"));
				
			
					}
					
					JSONArray it = data.getJSONObject(i).getJSONArray("it");
					
					for(int j=0;j<it.length();j++){
					
					JSONObject array = it.getJSONObject(j);
					localHashMap2.put("status", "true");
					localHashMap2.put("h_it"+j, array.get("h"));
					localHashMap2.put("v_it"+j, array.get("v"));
				
			
					}
					
					JSONArray fr = data.getJSONObject(i).getJSONArray("fr");
					
					for(int j=0;j<it.length();j++){
					
					JSONObject array = fr.getJSONObject(j);
					localHashMap2.put("status", "true");
					localHashMap2.put("h_fr"+j, array.get("h"));
					localHashMap2.put("v_fr"+j, array.get("v"));
				
			
					}
					
					
					locallist.add(localHashMap2);
					
					
				}
				return locallist;
			}

		} catch (Exception ae) {
			Log.e("ae==", "" + ae);
			localHashMap.put("status", "error");
			return locallist;
			

		}
		localHashMap.put("status", "error");
		return locallist;

	}
}

package com.example.swissapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	public static final String DATABASE_NAME = "favourites";

	// favourites_info table name(table name inside database)
	public static final String FAV_TABLE = "fav_contacts_info";

	// Contacts Table Columns names
	public static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PH_NO = "phone_number";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_ADDRESS = "address";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * called when database is created.
	 */
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_FAV_TABLE = "CREATE TABLE " + FAV_TABLE + "("
                + KEY_ID + " INTEGER," + KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT,"+ KEY_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_FAV_TABLE);

	}

	/**
	 * This method is called when database is upgraded
	 */
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + FAV_TABLE);
		 
        // Create tables again
        onCreate(db);

	}
	
	/**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    public // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        Log.e("KEY_NAME",""+contact.getName());
        Log.e("KEY_PH_NO",""+contact.getPhoneNumber());
        Log.e("KEY_EMAIL",""+contact.getEmail());
        Log.e("KEY_ADDRESS",""+contact.getAddress());
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_ID, contact.getID());
        // Inserting Row
        db.insert(FAV_TABLE, null, values);
        db.close(); // Closing database connection
    }
    
// // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(FAV_TABLE, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }
    
 // Deleting single contact
    public void deleteContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAV_TABLE, KEY_NAME + " = ?",
                new String[] { name });
        db.close();
    }
    
 // Getting contact on the basis of company name
    public Boolean getContact_comp(String subcat_name) {
    	
    	Boolean isExist;
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(FAV_TABLE, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO, KEY_EMAIL, KEY_ADDRESS }, KEY_NAME + "=?",
                new String[] { subcat_name }, null, null, null, null);
       // Log.e("cursor==",""+cursor.getString(1)+"=="+cursor.getString(2)+"=="+cursor.getString(3)+"=="+cursor.getString(4));
        if (cursor == null){
        	isExist = false;
        }
        else {
        	isExist = true;
        }

        return isExist;
    }
 
 
    // Getting single contact
    Contact getContact(String  name) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(FAV_TABLE, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO, KEY_EMAIL, KEY_ADDRESS }, KEY_NAME + "=?",
                new String[] {name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2) , cursor.getString(3) , cursor.getString(4));
        // return contact
        return contact;
    }
     
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FAV_TABLE;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setAddress(cursor.getString(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
    
    
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
 
        // updating row
        return db.update(FAV_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 
    
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + FAV_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

}

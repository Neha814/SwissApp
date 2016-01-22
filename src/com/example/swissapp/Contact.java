package com.example.swissapp;

public class Contact {

	int _id;
	public String _name;
	public String _phone_number;
	public String _email;
	public String _address;
	
	public Contact(){
        
    }
    // constructor
    public Contact(int id, String name, String _phone_number , String email, String address){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._email = email;
        this._address = address;
    }
     
    // constructor
    public Contact(String name, String _phone_number , String email , String address){
        this._name = name;
        this._phone_number = _phone_number;
        this._email = email;
        this._address = address;
    }
    
 // constructor
    public Contact(int id){
    	 this._id = id;
    }
	
	
	public int getID() {
		return _id;
	}
	public void setID(int _id) {
		this._id = _id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String getPhoneNumber() {
		return _phone_number;
	}
	public void setPhoneNumber(String _phone_number) {
		this._phone_number = _phone_number;
	}
	public String getEmail() {
		return _email;
	}
	public void setEmail(String _email) {
		this._email = _email;
	}
	public String getAddress() {
		return _address;
	}
	public void setAddress(String _address) {
		this._address = _address;
	}
	

}

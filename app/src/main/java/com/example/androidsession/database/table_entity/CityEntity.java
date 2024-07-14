package com.example.androidsession.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class CityEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("Name")
	private String name;

	public CityEntity(int UID, String Name, boolean IsActive) {
		this.uID = UID;
		this.name = Name;
		this.isActive = IsActive;
	}

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public String toString() {
		return name;
	}
}
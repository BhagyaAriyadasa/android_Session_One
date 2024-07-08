package com.example.androidsession.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class TeamEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("MaxCount")
	private int maxCount;

	@SerializedName("Name")
	private String name;

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

	public void setMaxCount(int maxCount){
		this.maxCount = maxCount;
	}

	public int getMaxCount(){
		return maxCount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}
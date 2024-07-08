package com.example.androidsession.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class LoginEntity{

	@SerializedName("UID")
	private int uID;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("Password")
	private String password;

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}
}
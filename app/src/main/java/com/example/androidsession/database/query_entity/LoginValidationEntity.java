package com.example.androidsession.database.query_entity;

import com.google.gson.annotations.SerializedName;

public class LoginValidationEntity{

	@SerializedName("IsActive")
	private boolean isActive;

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}
}
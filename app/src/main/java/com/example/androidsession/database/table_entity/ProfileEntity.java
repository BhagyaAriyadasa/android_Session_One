package com.example.androidsession.database.table_entity;

import com.google.gson.annotations.SerializedName;

public class ProfileEntity {

	@SerializedName("UID")
	private int uID;

	@SerializedName("Salary")
	private double salary;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("CityUid")
	private int cityUid;

	@SerializedName("TeamUid")
	private int teamUid;

	@SerializedName("Age")
	private int age;

	@SerializedName("Name")
	private String name;

	public void setUID(int uID){
		this.uID = uID;
	}

	public int getUID(){
		return uID;
	}

	public void setSalary(double salary){
		this.salary = salary;
	}

	public double getSalary(){
		return salary;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setCityUid(int cityUid){
		this.cityUid = cityUid;
	}

	public int getCityUid(){
		return cityUid;
	}

	public void setTeamUid(int teamUid){
		this.teamUid = teamUid;
	}

	public int getTeamUid(){
		return teamUid;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}
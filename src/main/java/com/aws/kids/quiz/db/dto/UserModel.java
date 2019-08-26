package com.aws.kids.quiz.db.dto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.aws.kids.quiz.db.dto.enm.Gender;

@Entity(name="user")
public class UserModel extends ItemModel{
	@Id
	private String userID;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String location;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}

package com.aws.kids.quiz.db.dto;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import com.aws.kids.quiz.db.dto.enm.Gender;
import com.aws.kids.quiz.db.dto.enm.Subject;

@Entity(name="user")
@NamedQuery(name = "GET_USER_BY_LOCATION", query = "from user where location = :location")
@NamedQuery(name = "GET_USER_BY_GENDER", query = "from user where gender = :gender")
public class UserModel extends ItemModel {
	@Id
	private String userID;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String location;
	
	@ElementCollection(targetClass=Subject.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name="user_interest")
    @Column(name="interest") // Column name in person_interest
    private Set<Subject> interests;
	
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
	public Set<Subject> getInterests() {
		return interests;
	}
	public void setInterests(Set<Subject> interests) {
		this.interests = interests;
	}
}

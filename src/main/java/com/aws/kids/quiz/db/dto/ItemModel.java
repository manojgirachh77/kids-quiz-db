package com.aws.kids.quiz.db.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ItemModel {
	
	@Column(name="creationTime", updatable=false)
	private Date creationTime;
	@Column(name="modifyTime")
	private Date modifyTime;

	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}

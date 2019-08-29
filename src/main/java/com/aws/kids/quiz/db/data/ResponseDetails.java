package com.aws.kids.quiz.db.data;

import java.util.List;

public class ResponseDetails {
	private String messageID;
	private String messageReason;
	private List result;
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public String getMessageReason() {
		return messageReason;
	}
	public void setMessageReason(String messageReason) {
		this.messageReason = messageReason;
	}
	public List getResult() {
		return result;
	}
	public void setResult(List result) {
		this.result = result;
	}
	
	
}

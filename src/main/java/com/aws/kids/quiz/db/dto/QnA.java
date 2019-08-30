package com.aws.kids.quiz.db.dto;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.aws.kids.quiz.db.dto.enm.AgeRange;
import com.aws.kids.quiz.db.dto.enm.Level;

@Entity(name = "qna")
public class QnA extends ItemModel {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "PR_KEY")
	private String questionID;
	private String question;
	
	@ElementCollection(targetClass=Answer.class, fetch = FetchType.EAGER)
    @CollectionTable(name="qna_answer")
    @Column(name="answer") 
	private Set<Answer> answer;
	private boolean optionAvailable;
	@Enumerated(EnumType.STRING)
	private Level level;
	@Enumerated(EnumType.STRING)
	private AgeRange ageRange;
	@ElementCollection(targetClass=AddtionalInfo.class, fetch = FetchType.EAGER)
    @CollectionTable(name="qna_addtionalinfo")
    @Column(name="addtionalinfo") 
	private Set<AddtionalInfo> additionalInfo;
	public String getQuestionID() {
		return questionID;
	}
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Set<Answer> getAnswer() {
		return answer;
	}
	public void setAnswer(Set<Answer> answer) {
		this.answer = answer;
	}
	public boolean isOptionAvailable() {
		return optionAvailable;
	}
	public void setOptionAvailable(boolean optionAvailable) {
		this.optionAvailable = optionAvailable;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public AgeRange getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(AgeRange ageRange) {
		this.ageRange = ageRange;
	}
	public Set<AddtionalInfo> getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(Set<AddtionalInfo> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	
}

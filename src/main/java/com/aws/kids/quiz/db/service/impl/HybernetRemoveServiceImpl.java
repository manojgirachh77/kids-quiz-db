package com.aws.kids.quiz.db.service.impl;

import javax.persistence.metamodel.EntityType;

import org.hibernate.Session;

import com.amazonaws.jmespath.ObjectMapperSingleton;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HybernetRemoveServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		Session session = null;
		try {
		session = openSession();
		session.beginTransaction();
		session.delete(populateEntity(request));
		responseDetails.setMessageID("000");
		responseDetails.setMessageReason("Successfully Removed");

		} catch(Exception ex) {
			responseDetails.setMessageID("999");
			responseDetails.setMessageReason("Unable to Registor "+ ex);
		} finally {
			closeSession(session);
		}
		return responseDetails;
	}

	private Object populateEntity(RequestDetails request) {
		EntityType<?> entity = getEntiryType(request.getName());
		try {
			
			ObjectMapper mapper = ObjectMapperSingleton.getObjectMapper();
			return mapper.convertValue(request.getValues(), entity.getJavaType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

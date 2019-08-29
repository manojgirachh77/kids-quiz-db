package com.aws.kids.quiz.db.service.impl;

import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import org.hibernate.Session;

import com.amazonaws.jmespath.ObjectMapperSingleton;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HybernetSearchServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		try {
			Session session = openSession();
			session.beginTransaction();
			Query query = session.getNamedQuery(request.getQuery());
			for (String key : request.getValues().keySet()) {
				EntityType<?> type = getEntiryType(request.getName());
				ObjectMapper mapper = ObjectMapperSingleton.getObjectMapper();
				query.setParameter(key, mapper.convertValue(request.getValues().get(key), type.getAttribute(key).getJavaType()));
			}
			responseDetails.setResult(query.getResultList());
			closeSession(session);
			responseDetails.setMessageID("000");
			responseDetails.setMessageReason("Successfully Get the details");

		} catch (Exception ex) {
			responseDetails.setMessageID("999");
			responseDetails.setMessageReason("Unable to Registor " + ex);
		}

		return responseDetails;
	}
}

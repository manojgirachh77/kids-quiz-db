package com.aws.kids.quiz.db.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.ItemModel;

public class HybernetSelectServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		EntityType<?> entity = getEntiryType(request.getName());
		String value = getPrimaryValue(entity, request);
		List<Object> result = new ArrayList<Object>(); 
		try {
			Session session = openSession();
			session.beginTransaction();
			result.add(session.get(entity.getJavaType(), value));
			responseDetails.setResult(result);
			closeSession(session);
			responseDetails.setMessageID("000");
			responseDetails.setMessageReason("Successfully Get the details");

		} catch (Exception ex) {
			responseDetails.setMessageID("999");
			responseDetails.setMessageReason("Unable to Registor " + ex);
		}

		return responseDetails;
	}

	private String getPrimaryValue(EntityType<?> entity, RequestDetails request) {
		String name = entity.getId(entity.getIdType().getJavaType()).getName();
		return request.getValues().get(name).toString();
	}
}

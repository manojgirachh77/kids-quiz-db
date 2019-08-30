package com.aws.kids.quiz.db.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.amazonaws.jmespath.ObjectMapperSingleton;
import com.amazonaws.util.CollectionUtils;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.ItemModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HybernetSelectServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		EntityType<?> entity = getEntiryType(request.getName());
		List<String> values = getPrimaryValue(entity, request);
		List<Object> result = new ArrayList<Object>(); 
		try {
			Session session = openSession();
			session.beginTransaction();
			for(String value : values) {
				Object bean = session.get(entity.getJavaType(), value);
				if(null != bean) {
					result.add(bean);
				}
			}
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

	private List<String> getPrimaryValue(EntityType<?> entity, RequestDetails request) {
		List<String> values = new ArrayList<String>();
		String name = entity.getId(entity.getIdType().getJavaType()).getName();
		if(null == request || CollectionUtils.isNullOrEmpty(request.getValues())) {
			return values;
		}
		for (Map<String, Object> value : request.getValues()) {
			if(value.containsKey(name)) {
				values.add(value.get(name).toString());
			}
		}
		return values;//request.getValues().get(0).get(name).toString();
	}
}

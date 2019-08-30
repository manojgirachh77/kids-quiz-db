package com.aws.kids.quiz.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.EntityType;

import org.hibernate.Session;

import com.amazonaws.jmespath.ObjectMapperSingleton;
import com.amazonaws.util.CollectionUtils;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.ItemModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HybernetRemoveServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		Session session = null;
		try {
			session = openSession();
			session.beginTransaction();
			List<Object> resultValues = populateEntity(request);
			int cnt = 0;
			for(Object bean : resultValues) {
				session.delete(bean);
				cnt++;
			}
			if(cnt > 0) {
				responseDetails.setMessageID("000");
				responseDetails.setMessageReason(cnt + " Records removed successfully");
			} else {
				responseDetails.setMessageID("001");
				responseDetails.setMessageReason("No Records found for remove");								
			}

		} catch(Exception ex) {
			responseDetails.setMessageID("999");
			responseDetails.setMessageReason("Unable to Registor "+ ex);
		} finally {
			closeSession(session);
		}
		return responseDetails;
	}

	private List<Object> populateEntity(RequestDetails request) {
		List<Object> resultValues = new ArrayList<Object>();
		List<Map<String, Object>> requestValues = request.getValues();
		if (request == null || CollectionUtils.isNullOrEmpty(requestValues)) {
			return resultValues;
		}
		Class<?> entityType = getEntiryType(request.getName()).getJavaType();
		
		try {
			for (Map<String, Object> values : requestValues) {
				ObjectMapper mapper = ObjectMapperSingleton.getObjectMapper();
				Object bean = (ItemModel) mapper.convertValue(values, entityType);
				resultValues.add(bean);
			}
			return resultValues;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultValues;
	}

}

package com.aws.kids.quiz.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Transaction;

import org.hibernate.Session;

import com.amazonaws.jmespath.ObjectMapperSingleton;
import com.amazonaws.util.CollectionUtils;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.ItemModel;
import com.aws.kids.quiz.db.dto.QnA;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;

public class HybernetInsertUpdateServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		Session session = null;
		try {
			session = openSession();
			session.beginTransaction();
			List<Object> resultValues = populateEntity(request);
			int cnt = 0;
			for (Object bean : resultValues) {
				session.saveOrUpdate(bean);
				cnt++;
			}
			responseDetails.setResult(resultValues);
			if (cnt > 0) {
				responseDetails.setMessageID("000");
				responseDetails.setMessageReason(cnt + " Records updated successfully");
			} else {
				responseDetails.setMessageID("001");
				responseDetails.setMessageReason("No Records found for update");
			}

		} catch (Exception ex) {
			responseDetails.setMessageID("999");
			responseDetails.setMessageReason("Unable to Registor " + ex);
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
				if (bean instanceof ItemModel) {
					((ItemModel) bean).setCreationTime(new Date());
					((ItemModel) bean).setModifyTime(new Date());
				}
				resultValues.add(bean);
			}
			return resultValues;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultValues;
	}
}

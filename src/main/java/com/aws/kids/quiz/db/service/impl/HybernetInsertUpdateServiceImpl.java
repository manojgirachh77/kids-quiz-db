package com.aws.kids.quiz.db.service.impl;

import java.util.Date;
import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.amazonaws.jmespath.ObjectMapperSingleton;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.ItemModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HybernetInsertUpdateServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		Session session = null;
		try {
		session = openSession();
		session.beginTransaction();
		session.saveOrUpdate(populateEntity(request));
		responseDetails.setMessageID("000");
		responseDetails.setMessageReason("Successfully updated details");

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
			Object bean = (ItemModel) mapper.convertValue(request.getValues(), entity.getJavaType());
			if(bean instanceof ItemModel) {
				((ItemModel)bean).setCreationTime(new Date());
				((ItemModel)bean).setModifyTime(new Date());
			}
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

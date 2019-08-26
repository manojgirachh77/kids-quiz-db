package com.aws.kids.quiz.db.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;

public class HybernetInsertServiceImpl extends AbstractHybernetService {

	@Override
	public ResponseDetails perform(RequestDetails request) {
		ResponseDetails responseDetails = new ResponseDetails();
		try {
		Session session = openSession();
		session.beginTransaction();
		session.saveOrUpdate(populateEntity(request));
		
		closeSession(session);
		responseDetails.setMessageID("000");
		responseDetails.setMessageReason("Successfully updated details");

		} catch(Exception ex) {
			responseDetails.setMessageID("999");
			responseDetails.setMessageReason("Unable to Registor "+ ex);
		}
		return responseDetails;
	}

	private Object populateEntity(RequestDetails request) {
		String typeName = request.getName();
		Object bean = getEntiryInstance(typeName);
		try {
			BeanUtilsBean.getInstance().populate(bean, request.getValues());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}
	private Object getEntiryInstance(String typeName) {
		try {
			SessionFactory sessionFactory = getSessionFactory();
			Set<EntityType<?>> entities = sessionFactory.getMetamodel().getEntities();
			EntityType<?> entity = entities.stream().filter(item->item.getName().equals(typeName)).findFirst().get(); 
			return entity.getJavaType().newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}

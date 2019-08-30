package com.aws.kids.quiz.db.service.impl;

import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.config.ConfigurationException;

import com.aws.kids.quiz.db.service.HybernetService;

public abstract class AbstractHybernetService implements HybernetService {

	private SessionFactory sessionFactory;

	protected SessionFactory getSessionFactory() {
		if (null != sessionFactory)
			return sessionFactory;
		try {
			sessionFactory = new Configuration().configure("resources/hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException e) {
			if(e instanceof ConfigurationException) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
				return sessionFactory;
			}
			System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
		return sessionFactory;
	}
	protected Session openSession() {
		return getSessionFactory().openSession();
	}
	protected void closeSession(Session session) {
		if(null != session) {
			session.flush();
			session.close();
		}
	}
	protected EntityType<?> getEntiryType(String typeName) {
		try {
			SessionFactory sessionFactory = getSessionFactory();
			Set<EntityType<?>> entities = sessionFactory.getMetamodel().getEntities();
			EntityType<?> entity = entities.stream().filter(item -> item.getName().equals(typeName)).findFirst().get();
			return entity;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}

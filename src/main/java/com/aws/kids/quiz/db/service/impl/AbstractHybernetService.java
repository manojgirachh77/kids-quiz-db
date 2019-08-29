package com.aws.kids.quiz.db.service.impl;

import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.aws.kids.quiz.db.service.HybernetService;

public abstract class AbstractHybernetService implements HybernetService {

	private static SessionFactory sessionFactory;

	protected static SessionFactory getSessionFactory() {
		if (null != sessionFactory)
			return sessionFactory;
		try {
			sessionFactory = new Configuration().configure("resources/hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException e) {
			System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
		return sessionFactory;
	}
	protected Session openSession() {
		return getSessionFactory().openSession();
	}
	protected void closeSession(Session session) {
		session.flush();
		session.close();
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

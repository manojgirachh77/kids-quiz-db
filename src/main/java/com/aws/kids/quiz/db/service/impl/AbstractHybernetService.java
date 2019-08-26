package com.aws.kids.quiz.db.service.impl;

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
			sessionFactory = new Configuration().configure().buildSessionFactory();
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
	
}

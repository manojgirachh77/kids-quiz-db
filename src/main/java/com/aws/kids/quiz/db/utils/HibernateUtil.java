package com.aws.kids.quiz.db.utils;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.util.StringUtils;
import com.aws.kids.quiz.db.service.HybernetService;
import com.aws.kids.quiz.db.service.impl.HybernetInsertServiceImpl;

public class HibernateUtil {
	
	private static Map<String,HybernetService> serviceMap = new HashMap<String, HybernetService>();
	
	public static HybernetService getHybernetService(String methodName) {

		HybernetService service = null;
		if(serviceMap.containsKey(StringUtils.upperCase(methodName))) {
			return serviceMap.get(methodName);
		}
		else if("GET".equalsIgnoreCase(methodName)) {
			
		}
		else if("PUT".equalsIgnoreCase(methodName)) {
			service = new HybernetInsertServiceImpl();
			serviceMap.put("PUT",service);
			
		}
		return service;
	}
}

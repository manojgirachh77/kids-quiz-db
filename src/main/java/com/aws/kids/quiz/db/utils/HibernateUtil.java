package com.aws.kids.quiz.db.utils;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.util.StringUtils;
import com.aws.kids.quiz.db.service.HybernetService;
import com.aws.kids.quiz.db.service.impl.HybernetInsertUpdateServiceImpl;
import com.aws.kids.quiz.db.service.impl.HybernetRemoveServiceImpl;
import com.aws.kids.quiz.db.service.impl.HybernetSearchServiceImpl;
import com.aws.kids.quiz.db.service.impl.HybernetSelectServiceImpl;

public class HibernateUtil {
	
	private static Map<String,HybernetService> serviceMap = new HashMap<String, HybernetService>();
	
	public static HybernetService getHybernetService(String methodName) {

		HybernetService service = null;
		if(serviceMap.containsKey(StringUtils.upperCase(methodName))) {
			return serviceMap.get(methodName);
		}
		else if("GET".equalsIgnoreCase(methodName)) {
			service = new HybernetSelectServiceImpl();
			serviceMap.put("GET",service);
		}
		else if("PUT".equalsIgnoreCase(methodName)) {
			service = new HybernetInsertUpdateServiceImpl();
			serviceMap.put("PUT",service);
		}
		else if("SEARCH".equalsIgnoreCase(methodName)) {
			service = new HybernetSearchServiceImpl();
			serviceMap.put("SEARCH",service);
		}
		else if("DELETE".equalsIgnoreCase(methodName)) {
			service = new HybernetRemoveServiceImpl();
			serviceMap.put("DELETE",service);
		}
		return service;
	}
}

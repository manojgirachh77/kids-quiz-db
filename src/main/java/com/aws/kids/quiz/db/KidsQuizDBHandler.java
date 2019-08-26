package com.aws.kids.quiz.db;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.service.HybernetService;
import com.aws.kids.quiz.db.utils.HibernateUtil;

public class KidsQuizDBHandler implements RequestHandler<RequestDetails, ResponseDetails> {

	@Override
	public ResponseDetails handleRequest(RequestDetails requestDetails, Context context) {
		context.getLogger().log("Input: " + requestDetails);
		HybernetService service = HibernateUtil.getHybernetService(requestDetails.getMethod());
		return service.perform(requestDetails);
	}
}

package com.aws.kids.quiz.db.service;

import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;

public interface HybernetService {

	public ResponseDetails perform(RequestDetails request);
}

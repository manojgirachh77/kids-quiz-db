package com.aws.kids.quiz.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.AddtionalInfo;
import com.aws.kids.quiz.db.dto.Answer;
import com.aws.kids.quiz.db.dto.enm.AgeRange;
import com.aws.kids.quiz.db.dto.enm.Gender;
import com.aws.kids.quiz.db.dto.enm.Level;
import com.aws.kids.quiz.db.dto.enm.Subject;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class KidsQuizDBQnAHandlerTest {

    private static RequestDetails input;
    
    @BeforeClass
    public static void createInput() throws IOException {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

    	input = new RequestDetails();
        input.setMethod("PUT");
        input.setName("addtionalinfo");
        List<Map<String, Object>> requestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("summary", "summary1");
        values.put("description", "description1");
        requestValues.add(values);
        input.setValues(requestValues);
        List<AddtionalInfo> addtionalInfoList = handler.handleRequest(input, ctx).getResult();

        requestValues = new ArrayList<Map<String,Object>>();
        input.setName("answer");
        values = new HashMap<String, Object>();
        values.put("answer", "answer1");
        values.put("priority", 10);
        values.put("weight", 5);
        values.put("correct", true);
        requestValues.add(values);
        input.setValues(requestValues);
        List<Answer> answerList = handler.handleRequest(input, ctx).getResult();

        requestValues = new ArrayList<Map<String,Object>>();
        input.setName("qna");
        
        values = new HashMap<String, Object>();
        values.put("question", "Question1");
        values.put("level", Level.EASY);
        values.put("optionAvailable", true);
        values.put("ageRange", AgeRange.ABOVETHRTY);

//        values.put("questionID", "902e2601-714b-483d-97f2-d82eb2569991");

        Set<AddtionalInfo> addtionalInfoSet = new HashSet<AddtionalInfo>();
        addtionalInfoSet.addAll(addtionalInfoList);
        values.put("additionalInfo", addtionalInfoSet);

        Set<Answer> answerSet = new HashSet<Answer>();
        answerSet.addAll(answerList);
        values.put("answer", answerSet);

        requestValues.add(values);

        input.setValues(requestValues);
        handler.handleRequest(input, ctx);
    }

    private static Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testInsertUser() {
        Assert.assertEquals(true,true);
    }
 }

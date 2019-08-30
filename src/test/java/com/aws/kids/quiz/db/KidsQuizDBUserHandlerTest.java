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
import com.aws.kids.quiz.db.dto.enm.Gender;
import com.aws.kids.quiz.db.dto.enm.Subject;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class KidsQuizDBUserHandlerTest {

    private static RequestDetails input;
    
    @BeforeClass
    public static void createInput() throws IOException {
        input = new RequestDetails();
        input.setMethod("PUT");
        input.setName("user");
        List<Map<String, Object>> requestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User1");
        values.put("gender", Gender.MALE);
        values.put("location", "Noida");
        values.put("dob", new GregorianCalendar(2006, 2, 11).getTime());
        Set<Subject> interests = new HashSet<Subject>();
        interests.add(Subject.ENGLISH);
        interests.add(Subject.GK);
        interests.add(Subject.MATHS);
        values.put("interests", interests);
        requestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User2");
        values.put("gender", Gender.MALE);
        values.put("location", "Pune");
        values.put("dob", new GregorianCalendar(2007, 2, 11).getTime());
        interests = new HashSet<Subject>();
        interests.add(Subject.GK);
        interests.add(Subject.MATHS);
        values.put("interests", interests);
        requestValues.add(values);

        values = new HashMap<String, Object>();
        values.put("userID", "User3");
        values.put("gender", Gender.FEMALE);
        values.put("location", "Pune");
        values.put("dob", new GregorianCalendar(2007, 6, 11).getTime());
        interests = new HashSet<Subject>();
        interests.add(Subject.GK);
        values.put("interests", interests);
        requestValues.add(values);

        values = new HashMap<String, Object>();
        values.put("userID", "User4");
        values.put("gender", Gender.FEMALE);
        values.put("location", "Noida");
        values.put("dob", new GregorianCalendar(2007, 2, 14).getTime());
        interests = new HashSet<Subject>();
        interests.add(Subject.GK);
        interests.add(Subject.MATHS);
        values.put("interests", interests);
        requestValues.add(values);

        input.setValues(requestValues);
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();
        handler.handleRequest(input, ctx);

    }

    private static Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testInsertUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("PUT");
        input.setValues(createInsertData());
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        input.setMethod("GET");
        input.setValues(createInsertValidateData());
        output = handler.handleRequest(input, ctx);
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(2, output.getResult().size());
    }
    @Test
    public void testInsertUpdateUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("PUT");
        input.setValues(createInsertUpdateData());
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        input.setMethod("GET");
        input.setValues(createInsertUpdateValidateData());
        output = handler.handleRequest(input, ctx);
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(2, output.getResult().size());
    }

    @Test
    public void testGetUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("GET");
        input.setValues(createGetData());
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(2, output.getResult().size());
    }
    @Test
    public void testSearchByLocationUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("SEARCH");
        input.setQuery("GET_USER_BY_LOCATION");
        input.setValues(createSearchByLocationData());
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(2, output.getResult().size());
    }
    @Test
    public void testSearchByGenderUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("SEARCH");
        input.setQuery("GET_USER_BY_GENDER");
        input.setValues(createSearchByGenderData());
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(2, output.getResult().size());
    }

    @Test
    public void testUpdateUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("PUT");
        List<Map<String, Object>> updateRequestValues = createUpdateData();
        input.setValues(updateRequestValues);
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        input.setMethod("GET");
        input.setValues(createGetData());
        output = handler.handleRequest(input, ctx);
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(2, output.getResult().size());
    }

    @Test
    public void testDeleteUser() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        input.setMethod("DELETE");
        input.setValues(createDeleteData());
        ResponseDetails output = handler.handleRequest(input, ctx);
        Assert.assertEquals("000", output.getMessageID());
        input.setMethod("GET");
        input.setValues(createInsertValidateData());
        output = handler.handleRequest(input, ctx);
        Assert.assertNotNull(output.getResult());
        Assert.assertEquals(0, output.getResult().size());
    }

    @AfterClass
    public static void cleanupInput() throws IOException {
        input.setMethod("DELETE");
        input.setName("user");
        List<Map<String, Object>> requestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User1");
        requestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User2");
        requestValues.add(values);

        values = new HashMap<String, Object>();
        values.put("userID", "User3");
        requestValues.add(values);

        values = new HashMap<String, Object>();
        values.put("userID", "User4");
        requestValues.add(values);

        values = new HashMap<String, Object>();
        values.put("userID", "User7");
        requestValues.add(values);

        input.setValues(requestValues);
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();
        handler.handleRequest(input, ctx);	
    }
    private List<Map<String, Object>> createInsertData() {
    	List<Map<String, Object>> insertRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User5");
        values.put("gender", Gender.MALE);
        values.put("location", "Noida");
        values.put("dob", new GregorianCalendar(2006, 2, 11).getTime());
        Set<Subject> interests = new HashSet<Subject>();
        interests.add(Subject.ENGLISH);
        interests.add(Subject.GK);
        interests.add(Subject.MATHS);
        values.put("interests", interests);
        insertRequestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User6");
        values.put("gender", Gender.MALE);
        values.put("location", "Noida");
        values.put("dob", new GregorianCalendar(2007, 2, 11).getTime());
        interests = new HashSet<Subject>();
        interests.add(Subject.GK);
        interests.add(Subject.MATHS);
        values.put("interests", interests);
        insertRequestValues.add(values);    	
        return insertRequestValues;
    }
    private List<Map<String, Object>> createInsertUpdateData() {
    	List<Map<String, Object>> insertRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User7");
        values.put("gender", Gender.MALE);
        values.put("location", "Noida");
        values.put("dob", new GregorianCalendar(2006, 2, 11).getTime());
        Set<Subject> interests = new HashSet<Subject>();
        interests.add(Subject.ENGLISH);
        interests.add(Subject.GK);
        interests.add(Subject.MATHS);
        values.put("interests", interests);
        insertRequestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User1");
        values.put("gender", Gender.UNISEX);
        values.put("location", "Mumbai");
        values.put("dob", new GregorianCalendar(2007, 2, 14).getTime());
        insertRequestValues.add(values);    	
        return insertRequestValues;
    }
    private List<Map<String, Object>> createInsertUpdateValidateData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User7");
        getRequestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User1");
        getRequestValues.add(values);    	
        return getRequestValues;
    }

    private List<Map<String, Object>> createInsertValidateData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User5");
        getRequestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User6");
        getRequestValues.add(values);    	
        return getRequestValues;
    }

    private List<Map<String, Object>> createGetData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User1");
        getRequestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User2");
        getRequestValues.add(values);    	
        return getRequestValues;
    }
    private List<Map<String, Object>> createSearchByLocationData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("location", "Pune");
        getRequestValues.add(values);
        return getRequestValues;
    }
    private List<Map<String, Object>> createSearchByGenderData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("gender", Gender.FEMALE);
        getRequestValues.add(values);
        return getRequestValues;
    }

    private List<Map<String, Object>> createDeleteData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User5");
        getRequestValues.add(values);
        
        values = new HashMap<String, Object>();
        values.put("userID", "User6");
        getRequestValues.add(values);    	
        return getRequestValues;
    }

    private List<Map<String, Object>> createUpdateData() {
    	List<Map<String, Object>> getRequestValues = new ArrayList<Map<String,Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User1");
        values.put("dob", new GregorianCalendar(2007, 2, 14).getTime());
        values.put("gender", Gender.MALE);
        values.put("location", "Noida");
        getRequestValues.add(values);
        values = new HashMap<String, Object>();
        values.put("userID", "User2");
        values.put("dob", new GregorianCalendar(2006, 2, 13).getTime());
        values.put("gender", Gender.MALE);
        values.put("location", "Pune");

        getRequestValues.add(values);    	
        return getRequestValues;
    }

}

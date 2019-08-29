package com.aws.kids.quiz.db;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.aws.kids.quiz.db.data.RequestDetails;
import com.aws.kids.quiz.db.data.ResponseDetails;
import com.aws.kids.quiz.db.dto.enm.Gender;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class KidsQuizDBHandlerTest {

    private static RequestDetails input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = new RequestDetails();
        input.setMethod("GET");
        input.setName("user");
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("userID", "User1");
        values.put("gender", Gender.FEMALE);
        values.put("location", "Pune");
        values.put("creationTime", new Date());
        values.put("modifyTime", new Date());
        input.setValues(values);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testKidsQuizDBHandler() {
        KidsQuizDBHandler handler = new KidsQuizDBHandler();
        Context ctx = createContext();

        ResponseDetails output = handler.handleRequest(input, ctx);

        Assert.assertEquals("000", output.getMessageID());
    }
}

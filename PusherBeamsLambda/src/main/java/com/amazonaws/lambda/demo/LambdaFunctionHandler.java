package com.amazonaws.lambda.demo;

import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pusher.pushnotifications.PushNotifications;

public class LambdaFunctionHandler implements RequestHandler<RequestClass, String> {

    @Override
    public String handleRequest(RequestClass request, Context context) {
        String instanceId = "YOUR_INSTANCE_ID";
        String secretKey = "YOUR_SECRET_KEY";

        PushNotifications beamsClient = new PushNotifications(instanceId, secretKey);
        List<String> interests = Arrays.asList("hello");

        Map<String, Map> publishRequest = new HashMap();
        Map<String, String> fcmNotification = new HashMap();
        fcmNotification.put("title", request.title);
        fcmNotification.put("body", request.message);
        Map<String, Map> fcm = new HashMap();
        fcm.put("notification", fcmNotification);
        publishRequest.put("fcm", fcm);

        try {
			beamsClient.publishToInterests(interests, publishRequest);
			return "Push sent!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Push failed!";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "Push failed!";
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return "Push failed!";
		}
    }
}

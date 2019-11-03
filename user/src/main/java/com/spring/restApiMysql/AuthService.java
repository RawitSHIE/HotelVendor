package com.spring.restApiMysql;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private static AuthService instance = new AuthService();
	
	public static AuthService getInstance() {
	    return instance;
	}
	
	public String genTokenForNewUser(String username, String password) throws Exception {
        try {
            String token = sendPost("http://localhost:3000/users", username, password);
            return token;
        } finally {
            close();
        }
	}
	
	public String genTokenLogin(String username, String password) throws Exception {
        try {
            String token = sendPost("http://localhost:3000/users/login", username, password);
            return token;
        } finally {
            close();
        }
	}
	
	public String getUsername(String header, String value) throws Exception {
		try {
			String username = sendGet("http://localhost:3000/users/me", header, value);
			return username;
		} finally {
			close();
		}
	}
	
	private String sendGet(String url, String header, String value) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader(header, value);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                return result;
            }
        }
		return "invalid token!";
    }
	
	private String sendPost(String url, String username, String password) throws Exception {
        HttpPost post = new HttpPost(url);
        JSONObject jsonPost = new JSONObject();
        
        // add request parameter, form parameters
        jsonPost.put("username", username);
        jsonPost.put("password", password);
        StringEntity se = new StringEntity(jsonPost.toString());
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post.setEntity(se);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            return EntityUtils.toString(response.getEntity());
        }
    }
	
	private void close() throws IOException {
        httpClient.close();
    }
}

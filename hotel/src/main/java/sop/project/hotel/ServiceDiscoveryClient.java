package sop.project.hotel;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class ServiceDiscoveryClient {

    @Autowired
    DiscoveryClient discoveryClient;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public int getUserId(String header, String value) throws Exception {
        try {
            List<ServiceInstance> instances = discoveryClient.getInstances("authservice");
            String serviceUri = String.format("https://%s/users/me", instances.get(0).getHost());
            int userId = Integer.parseInt(sendGet(serviceUri, header, value));
            return userId;
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
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                return result;
            }
        }

        return "invalid token!";
    }

    private void close() throws IOException {
        httpClient.close();
    }

}
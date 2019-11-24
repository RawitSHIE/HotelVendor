package sop.project.booking;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sop.project.booking.model.extendModel.requestModel.Hotel;
import sop.project.booking.model.extendModel.requestModel.HotelFullDetail;
import sop.project.booking.model.extendModel.requestModel.User;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@EnableDiscoveryClient
@RestController
@Component
public class ServiceDiscoveryClient {

    @Autowired
    DiscoveryClient discoveryClient;

    public Hotel getHotel(long id) {
        String hotelId = String.valueOf(id);
        RestTemplate restTemplate = new RestTemplate();
//        List<ServiceInstance> instances = discoveryClient.getInstances("hotelroomdetail");
//        String serviceUri = String.format("%s/hoteldetail/%s", instances.get(0).getUri().toString(), hotelId);
        String serviceUri = String.format("https://hotelservice.appspot.com/hoteldetail/%s", hotelId);
        ResponseEntity<Hotel> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET,null, Hotel.class, hotelId);

        return restExchange.getBody();
    }

    public User getUser(long id) {
        String userId = String.valueOf(id);
        RestTemplate restTemplate = new RestTemplate();
//        List<ServiceInstance> instances = discoveryClient.getInstances("userdetail");
//        String serviceUri = String.format("%s/user/%s", instances.get(0).getUri().toString(), userId);
        String serviceUri = String.format("https://user-259905.appspot.com/user/%s", userId);
        ResponseEntity<User> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET,null, User.class, userId);

        return restExchange.getBody();
    }

    public HotelFullDetail getHotelFullDetail(long hotelId) {
        RestTemplate restTemplate = new RestTemplate();
//        List<ServiceInstance> instances = discoveryClient.getInstances("hotelroomdetail");
//        String serviceUri = String.format("%s/fullhoteldetail/%s",
//                instances.get(0).getUri().toString(), String.valueOf(hotelId));
        String serviceUri = String.format("https://hotelservice.appspot.com/fullhoteldetail/%s", String.valueOf(hotelId));
        ResponseEntity<HotelFullDetail> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET,null, HotelFullDetail.class, String.valueOf(hotelId));

        return restExchange.getBody();
    }

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

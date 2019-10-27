package sop.project.booking;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sop.project.booking.model.extendModel.Hotel;
import sop.project.booking.model.extendModel.User;

import java.util.List;

@RestController
@Component
public class ServiceDiscoveryClient {

    @Autowired
    DiscoveryClient discoveryClient;

    public Hotel getHotel(long id) {
        String hotelId = String.valueOf(id);
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("hotelroomdetail");
        String serviceUri = String.format("%s/hoteldetail/%s", instances.get(0).getUri().toString(), hotelId);
        ResponseEntity<Hotel> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET,null, Hotel.class, hotelId);

        return restExchange.getBody();
    }

    public User getUser(long id) {
        String userId = String.valueOf(id);
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("userdetail");
        String serviceUri = String.format("%s/user/%s", instances.get(0).getUri().toString(), userId);
        ResponseEntity<User> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET,null, User.class, userId);

        return restExchange.getBody();
    }

    public User getRoomType(long id) {
        String hotelId = String.valueOf(id);
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("hotelroomdetail");
        String serviceUri = String.format("%s/user/%s", instances.get(0).getUri().toString(), hotelId);
        ResponseEntity<User> restExchange =
                restTemplate.exchange(serviceUri, HttpMethod.GET,null, User.class, hotelId);

        return restExchange.getBody();
    }
}

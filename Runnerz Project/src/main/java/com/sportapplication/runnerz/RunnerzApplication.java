package com.sportapplication.runnerz;

import com.sportapplication.runnerz.user.User;
import com.sportapplication.runnerz.user.UserHttpClient;
import com.sportapplication.runnerz.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class RunnerzApplication {

    private  static  final Logger log = LoggerFactory.getLogger(RunnerzApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RunnerzApplication.class, args);
    }



    @Bean
    UserHttpClient userHttpClient(){
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory= HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(UserHttpClient.class);
    }





    @Bean
    CommandLineRunner runner(UserHttpClient userRestClient) {
        return args -> {

//            try {
//                List<User> users = userRestClient.findAll();
//                System.out.println(users);
//                // process the users
//            } catch (ResourceAccessException e) {
//                // log the error and inform the user
//                System.err.println("Failed to fetch users. Please check your network connection and try again.");
//            }
//
//
//            User user = userRestClient.findById(1);
//            System.out.println(user);
        };
    }

}

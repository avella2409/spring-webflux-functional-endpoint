package com.avella.sample.springwebfluxfunctionalendpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringWebfluxFunctionalEndpointApplication {

    private final Flux<Person> allPerson = Flux.just(
            new Person("Anthony"),
            new Person("Celia"),
            new Person("Thomas")
    );

    @Bean
    RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .GET("/person", serverRequest -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON).body(allPerson, Person.class))
                .GET("/person/start-with/{startWith}", serverRequest -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(allPerson.filter(currentPerson -> currentPerson.name.toLowerCase()
                                .startsWith(serverRequest.pathVariable("startWith"))), Person.class)
                )
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxFunctionalEndpointApplication.class, args);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Person {
        String name;
    }

}

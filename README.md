# Spring Webflux functional endpoint

## Goal

Implement and test spring webflux functional endpoint.

## Steps

### Step 1 : Create a simple pojo

```java

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    String name;
}
```

### Step 2 : Create a Flux to simulate some data

```java
private final Flux<Person> allPerson = Flux.just(
        new Person("Anthony"),
        new Person("Celia"),
        new Person("Thomas")
);
```

### Step 3 : Create the functional endpoint

```java
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
```

There is two endpoint, the first `/person`, return all person, and the second `/person/start-with/{startWith}`, return all `person` with `name` starting with something we specify.
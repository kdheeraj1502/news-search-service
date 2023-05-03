# News Service Rest API

Back-end architecture of news service, based on search to NYTimes US, Guardian UK and spring boot. It contains International news related information.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
Maven
Java
```

### Installing

Clone & Install

```
Clone the repo: git clone https://github.com/kdheeraj1502/news-search-service.git
Install dependencies: mvn clean compile install -DskipTests
```

## Deployment

##### Using Console
```
Start the server: mvn spring-boot:run
```
##### Using Code Editor (IntelliJ IDEA or Eclipse)
```
Run: src/main/java/com/rajnikant/springAPI/SpringApiApplication.java
```

## Testing the API

##### Using [Postman](https://www.getpostman.com/)
```
Create valid request and fire.
```
##### Using Swagger
```
Start the server and open: http://localhost:8080
And hit APIs as listed below.
```

##### Using Jenkins And Docker
```
Using docker for image creation to run on clound computing
```

![API Screenshot](/api_preview.png)

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web/application framework used
* [SpringFox](http://springfox.github.io/springfox/) - API documentation
* [Maven](https://maven.apache.org/) - Dependency Management


## License

This project is licensed under the Apache License - see the [LICENSE.md](/LICENSE) file for details

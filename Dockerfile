FROM openjdk:8
EXPOSE 8080
ADD target/news-search-service.jar news-search-service.jar
ENTRYPOINT ["java", "-jar", "/news-search-service.jar"]
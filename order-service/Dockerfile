FROM openjdk:11-jre-slim
ADD target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/order-service.jar"]

FROM openjdk:11-jre-slim
ADD target/order-bulk-service-0.0.1-SNAPSHOT.jar order-bulk-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/order-bulk-service.jar"]

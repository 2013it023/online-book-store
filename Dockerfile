FROM openjdk:8-jdk-alpine
MAINTAINER getir.com
COPY target/online-book-store-0.0.1-SNAPSHOT.jar online-book-store-0.0.1.jar
ENTRYPOINT ["java","-jar","/online-book-store-0.0.1.jar"]
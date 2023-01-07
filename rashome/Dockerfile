FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/target/*-spring-boot.jar
COPY ${JAR_FILE} app.jar
EXPOSE 12580
ENTRYPOINT ["java","-jar","/app.jar"]
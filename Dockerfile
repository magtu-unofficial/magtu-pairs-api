FROM openjdk:11-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Xms32m", "-Xmx32m" ,"/app.jar"]

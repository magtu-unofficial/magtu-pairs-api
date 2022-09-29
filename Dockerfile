FROM maven:3.6-jdk-11 AS builder
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY . .
RUN mvn clean package -DskipTests=true

FROM openjdk:11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=builder /usr/app/target/*.jar app.jar
ENV TZ Europe/Moscow
CMD ["java", "-Duser.timezone=Europe/Moscow", "-jar", "app.jar", "-Xms16m", "-Xmx16m"]
EXPOSE 8080

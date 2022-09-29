FROM maven:3.6-jdk-11 as BUILD
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY . .
RUN mvn clean package -DskipTests=true
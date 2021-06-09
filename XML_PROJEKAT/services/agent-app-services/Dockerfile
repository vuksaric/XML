FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/agent-app-services
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/agent-app_services/target/agent-app_services.jar ./
EXPOSE 8080
CMD java -jar agent-app_services.jar

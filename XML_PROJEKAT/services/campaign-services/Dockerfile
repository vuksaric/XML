FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/campaign_services
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/campaign_services/target/campaign_services.jar ./
EXPOSE 8080
CMD java -jar campaign_services.jar
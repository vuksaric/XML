FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
WORKDIR /usr/src/server
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /campaign
COPY --from=appServerBuild /usr/src/server/target/campaign-services.jar ./
EXPOSE 8080
CMD java -jar campaign-services.jar
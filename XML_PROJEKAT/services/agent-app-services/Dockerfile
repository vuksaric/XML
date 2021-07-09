FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
WORKDIR /usr/src/server
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /agent-app
COPY --from=appServerBuild /usr/src/server/target/agent-app-services.jar ./
EXPOSE 8150
CMD java -jar agent-app-services.jar
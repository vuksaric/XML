FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
WORKDIR /usr/src/server
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /profile
COPY --from=appServerBuild /usr/src/server/target/profile-services.jar ./
EXPOSE 8140
CMD java -jar profile-services.jar
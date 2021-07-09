FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
WORKDIR /usr/src/server
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /auth
COPY --from=appServerBuild /usr/src/server/target/auth-services.jar ./
EXPOSE 8090
CMD java -jar auth-services.jar
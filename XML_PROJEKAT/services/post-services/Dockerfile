FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
WORKDIR /usr/src/server
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /post
COPY --from=appServerBuild /usr/src/server/target/post-services.jar ./
EXPOSE 8130
CMD java -jar post-services.jar
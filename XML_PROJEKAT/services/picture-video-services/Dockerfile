FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
WORKDIR /usr/src/server
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /picture-video
COPY --from=appServerBuild /usr/src/server/target/picture-video-services.jar ./
EXPOSE 8120
CMD java -jar picture-video-services.jar
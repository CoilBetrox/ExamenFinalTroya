FROM gradle:7.3.3-jdk17 AS build
WORKDIR /helidon
COPY build.gradle settings.gradle ./
COPY src src
RUN gradle build --no-daemon -x test
FROM openjdk:17-jdk-slim
WORKDIR /helidon
COPY --from=build /helidon/build/libs/App-Books-1.0-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "App-Books-1.0-SNAPSHOT.jar"]
# multi stage docker image

# build stage
FROM openjdk:8-jdk-alpine as build
# creates a user & group spring to run spring as non-root user to lessen the risks
# -S as in system
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /workspace/app

COPY gradle gradle

# copy gradle configuration files and gradle wrapper
COPY build.gradle.kts settings.gradle.kts gradlew ./

# copy source code
COPY src src

# build
RUN ./gradlew build -DskipTests
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*SNAPSHOT.jar)


# base image
FROM openjdk:8-jdk-alpine

# creates a user & group spring to run spring as non-root user to lessen the risks
# -S as in system
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# takes Slack Bot access token as an argument
ARG SLACK_ACCESS_TOKEN="slack access token"
ENV SLACK_TOKEN ${SLACK_ACCESS_TOKEN}

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/build/libs/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.woodigo.coggire.CoggireApplicationKt"]

# only expose port 8080
EXPOSE 8080
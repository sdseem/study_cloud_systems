FROM gradle:8.10.2 AS build
RUN mkdir "/app"
WORKDIR /app
COPY ./ /app

USER root
RUN chown -R gradle /app
USER gradle

RUN ./gradlew build --no-daemon -x test

FROM openjdk:21-slim

ENV NUMBERS_DB_USER=user
ENV NUMBERS_DB_NAME=numbers
ENV NUMBERS_DB_PASSWORD=password
ENV MAX_SERVICE_NUMBER=1000000000

EXPOSE 8080

COPY --from=build /app/build/libs/*.jar /app/study-application.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","study-application.jar"]
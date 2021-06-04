FROM openjdk:8-jre-slim
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Xmx200m", "/app.jar"]
EXPOSE 3000
FROM openjdk:8-jre-slim
COPY ./build/lib/*.jar app.jar
ENTRYPOINT ["java", "-Xmx300M", "-jar", "/app.jar"]
EXPOSE 7080
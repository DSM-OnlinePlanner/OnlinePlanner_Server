FROM openjdk:8-jre-slim
COPY ./build/libs/online_planner-0.0.1-SNAPSHOT.jar.jar app.jar
ENTRYPOINT ["java", "-jar", "-Xmx200m", "/app.jar"]
EXPOSE 3000
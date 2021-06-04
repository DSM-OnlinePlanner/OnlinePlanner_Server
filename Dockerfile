FROM openjdk:8-jre-slim
COPY ./build/libs/*.jar /App.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "-Duser.timezone=Asia/Seoul", "/App.jar"]
EXPOSE 3000
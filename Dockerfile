FROM openjdk:8-jre-slim
COPY ./build/libs/*.jar op.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "-Duser.timezone=Asia/Seoul", "/op.jar"]
EXPOSE 3000
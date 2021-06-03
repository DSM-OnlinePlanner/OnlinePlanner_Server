FROM java:8
COPY ./build/lib/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Xmx300M","/app.jar"]
EXPOSE 7080
FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
HEALTHCHECK --interval=15s --timeout=5s CMD wget -O/dev/stdout -q http://localhost:8080/actuator/health | grep UP
ENTRYPOINT ["java","-Xms100m","-Xmx1G","-jar","/app.jar"]

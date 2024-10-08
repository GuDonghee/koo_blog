FROM openjdk:17
ARG JAR_PATH=build/libs/*.jar
COPY ${JAR_PATH} /home/server.jar
ENTRYPOINT ["java","-jar","/home/server.jar", \
            "-Dspring-boot.run.arguments=--security.jwt.token.secret-key=${SECRET_KEY}"]

FROM openjdk:17
ARG JAR_PATH=build/libs/*.jar
COPY ${JAR_PATH} app.jar
ENTRYPOINT ["java","-jar","app.jar", \
            "-Dspring-boot.run.arguments=--security.jwt.token.secret-key=${SECRET_KEY}"]

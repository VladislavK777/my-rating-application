FROM openjdk:11
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} "my-rating-application-1.0.0.jar"
ENTRYPOINT ["java","-jar","/my-rating-application-1.0.0.jar"]
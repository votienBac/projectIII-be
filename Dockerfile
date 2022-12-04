FROM openjdk:11.0-jdk
ENV TZ "Asia/Ho_Chi_Minh"
RUN rm -rf /var/cache/apk/*
ARG JAR_FILE=noron-api/target/*.jar
COPY ${JAR_FILE} /usr/app/
COPY java.security /usr/local/openjdk-11/conf/security
WORKDIR /usr/app
EXPOSE 8085
ENTRYPOINT exec java $JAVA_OPTS -jar $JAVA_ARGS noron-api-0.0.1-SNAPSHOT.jar
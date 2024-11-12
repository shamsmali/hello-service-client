FROM openjdk:11

VOLUME /tmp
COPY build/libs/* /

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /hello-service-client.jar" ]
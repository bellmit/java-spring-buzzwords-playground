FROM openjdk:12-alpine

CMD java \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005 \
    -jar /opt/application.jar \
    $JAVA_OPTS
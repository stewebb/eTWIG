FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./target/etwig-*.jar /etwig/etwig.jar
ENTRYPOINT ["java", "-jar", "/etwig/etwig.jar"]
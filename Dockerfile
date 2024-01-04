# eTWIG - The event and banner management software for residential halls and student unions.
# @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
# @license: MIT
# @author: Steven Webb [xiaoancloud@outlook.com]
# @website: https://etwig.grinecraft.net
# @function: The Dockerfile for building an eTWIG docker image.

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./target/etwig-*.jar /etwig/etwig.jar
ENTRYPOINT ["java", "-jar", "/etwig/etwig.jar"]
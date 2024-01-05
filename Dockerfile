# eTWIG - The event and banner management software for residential halls and student unions.
# @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
# @license: MIT
# @author: Steven Webb [xiaoancloud@outlook.com]
# @website: https://etwig.grinecraft.net
# @function: The Dockerfile for building an eTWIG docker image.

#FROM eclipse-temurin:17-jdk-alpine
FROM ubuntu:22.04
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    net-tools \
    #package-foo  \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 7001

VOLUME /tmp
COPY ./target/etwig-*.jar /etwig/etwig.jar
COPY ./src/main/python /etwig/python

ENTRYPOINT ["java", "-jar", "/etwig/etwig.jar"]
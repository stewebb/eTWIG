#!/bin/bash
mkdir -p ./output
mvn clean install -Dmaven.test.skip=true
cp ./target/etwig-*.jar ./output/etwig.jar

cd ./output
java -jar ./etwig.jar
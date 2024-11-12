#!/bin/bash
value=`cat version.txt`
echo "image version read $value"
result=${PWD##*/}

echo "current dir $result"

./gradlew clean
./gradlew build
./gradlew jar
docker build -t shamsmali/${result%/}:${value} .;
docker push shamsmali/${result%/}:${value};
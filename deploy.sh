#!/bin/bash
./gradlew clean build -x test
docker buildx build --platform linux/amd64 --load --tag hyungjoonson/blossom .
docker push hyungjoonson/blossom

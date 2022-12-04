#!/bin/bash
mvn clean install
docker build -t docker-daemon1:5555/motel-backend:v1.1.$1 .
docker push docker-daemon1:5555/motel-backend:v1.1.$1
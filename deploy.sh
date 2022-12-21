#!/bin/bash
ssh votienbac@motel-dev 'docker pull docker-daemon:5555/motel-backend:v1.1.'$1
ssh votienbac@motel-dev 'docker rm -f motel-backend'
ssh votienbac@motel-dev 'docker run --name motel-backend -v /etc/hosts:/etc/hosts:ro -d -p 8085:8085 --restart unless-stopped docker-daemon:5555/motel-backend:v1.1.'$1
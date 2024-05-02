#!/bin/bash
docker pull rabbitmq:3.7.7-management
docker run -d --name rabbitmq3.7.7 -p 5672:5672 -p 15672:15672 --hostname rabbitmq -e RABBITMQ_DEFAULT_VHOST=/ -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.7.7-management
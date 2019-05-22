#!/bin/bash
echo -- -- begin init y3tu-cloud... -- --

COMPOSE_FILE=./y3tu-cloud-docs/docker/docker-compose.yml
JAR_DIR=./y3tu-cloud-docs/docker/jar

echo -- -- stop and remove old docker-compose containers -- --
if docker-compose -f ${COMPOSE_FILE} ps
    then
        docker-compose -f ${COMPOSE_FILE} stop
        docker-compose -f ${COMPOSE_FILE} rm --force
fi

echo -- -- building jar -- --
mvn clean package -Dmaven.test.skip=true

echo -- -- move jar to ${JAR_DIR} -- --
if [ ! -d ${JAR_DIR} ];then
   mkdir -p ${JAR_DIR}
fi

rm -rf ${JAR_DIR}/gateway-server*.jar

cp ./y3tu-cloud-gateway/target/gateway-server*.jar ${JAR_DIR}

echo -- -- run docker-compose up -- --
docker-compose -f ${COMPOSE_FILE} up -d --build

docker images|grep none|awk '{print $3 }'|xargs docker rmi

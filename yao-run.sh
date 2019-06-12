#!/bin/bash
echo -- -- begin init Yao... -- --

#定义docker-compose.yml文件和jar包位置
COMPOSE_FILE=./docs/docker/docker-compose.yml
JAR_DIR=./docs/docker/jar

echo -- -- stop and remove old docker-compose containers 停止并删除旧版本容器-- --
if docker-compose -f ${COMPOSE_FILE} ps
    then
        docker-compose -f ${COMPOSE_FILE} stop
        docker-compose -f ${COMPOSE_FILE} rm --force
fi

echo -- -- building jar 构建jar包并跳过单元测试-- --
mvn clean package -Dmaven.test.skip=true

echo -- -- move jar to ${JAR_DIR} -- --
if [ ! -d ${JAR_DIR} ];then
   mkdir -p ${JAR_DIR}
fi

echo -- -- 删除旧版本jar包并把新构建的jar包移动到指定目录 -- --
rm -rf ${JAR_DIR}/yao-authentication*.jar
rm -rf ${JAR_DIR}/yao-authorization*.jar
rm -rf ${JAR_DIR}/yao-back*.jar
rm -rf ${JAR_DIR}/yao-gateway*.jar
rm -rf ${JAR_DIR}/yao-log-server*.jar

cp ./yao-auth/yao-authentication/target/yao-authentication*.jar ${JAR_DIR}
cp ./yao-auth/yao-authorization/target/yao-authorization*.jar ${JAR_DIR}
cp ./yao-back/target/yao-back*.jar ${JAR_DIR}
cp ./yao-gateway/target/yao-gateway*.jar ${JAR_DIR}
cp ./yao-log/yao-log-server/target/yao-log-server*.jar ${JAR_DIR}

echo -- -- run docker-compose up -- --
docker-compose -f ${COMPOSE_FILE} up -d --build

docker images|grep none|awk '{print $3 }'|xargs docker rmi

#!/bin/sh
echo 'build static resources'
npm run show-build
# kill old container
if docker ps | grep -i yao-ui
    then
        echo 'kill old container'
        docker kill yao-ui
fi

# remove old container
if docker ps -a | grep -i yao-ui
    then
        echo 'rm old container'
        docker rm yao-ui
fi

# remove old images
if docker images | grep docker_yao-ui:latest
    then
        echo 'remove old image'
        docker rmi docker_yao-ui:latest
fi

echo 'build image'
docker build --rm -t docker_yao-ui:latest .
echo 'run docker container'
docker run -p 80:80 --name yao-ui -d docker_yao-ui:latest
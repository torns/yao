#!/bin/sh
# kill old container
if docker ps | grep -i taroco-ui
    then
        docker kill taroco-ui
fi

# remove old container
if docker ps -a | grep -i taroco-ui
    then
        docker rm taroco-ui
fi

# remove old images
if docker images | grep docker_taroco-ui:latest
    then
        docker rmi docker_taroco-ui:latest
fi

unzip dist.zip

docker build --rm -t docker_taroco-ui:latest .

docker run -p 80:80 --name taroco-ui -d docker_taroco-ui:latest
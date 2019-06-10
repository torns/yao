#!/bin/sh
# kill old container
if docker ps | grep -i y3tu-cloud-ui
    then
        docker kill y3tu-cloud-ui
fi

# remove old container
if docker ps -a | grep -i y3tu-cloud-ui
    then
        docker rm y3tu-cloud-ui
fi

# remove old images
if docker images | grep y3tu-cloud-ui:latest
    then
        docker rmi y3tu-cloud-ui:latest
fi

unzip dist.zip

docker build --rm -t y3tu-cloud-ui:latest .

docker run -p 80:80 --name y3tu-cloud-ui -d y3tu-cloud-ui:latest
FROM java:alpine
VOLUME /var/y3tu/y3tu-cloud-log/y3tu-cloud-gateway-web:/logs/y3tu-cloud-gateway-web
ADD ./target/y3tu-cloud-gateway-web.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

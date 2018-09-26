FROM java:alpine
VOLUME /var/y3tu/y3tu-cloud-log/y3tu-cloud-config:/logs/y3tu-cloud-config
ADD ./target/y3tu-cloud-config.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

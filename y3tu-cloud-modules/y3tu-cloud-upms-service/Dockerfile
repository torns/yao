FROM java:alpine
VOLUME /var/y3tu/y3tu-cloud-log/y3tu-cloud-upms-service:/logs/y3tu-cloud-upms-service
ADD ./target/y3tu-cloud-upms-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

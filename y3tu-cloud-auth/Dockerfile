FROM java:alpine
VOLUME /var/y3tu/y3tu-cloud-log/y3tu-cloud-authorization:/logs/y3tu-cloud-authorization
ADD ./target/y3tu-cloud-authorization.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

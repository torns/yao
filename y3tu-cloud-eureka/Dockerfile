FROM java:alpine
VOLUME /tmp
ADD ./target/y3tu-cloud-eureka.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

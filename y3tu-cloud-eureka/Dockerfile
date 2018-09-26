FROM java:alpine
VOLUME /var/y3tu/y3tu-cloud-log/y3tu-cloud-eureka:/logs/y3tu-cloud-eureka
ADD ./target/y3tu-cloud-eureka.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

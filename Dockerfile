FROM openjdk:14-alpine
COPY target/ssp-*.jar ssp.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "ssp.jar"]
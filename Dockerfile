FROM amazoncorretto:24
COPY target/library-management-system-0.0.1-SNAPSHOT.jar library-management-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/library-management-system-0.0.1-SNAPSHOT.jar"]

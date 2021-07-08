FROM openjdk:11.0.11-jdk-oracle
ADD target/SW_API-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar SW_API-0.0.1-SNAPSHOT.jar

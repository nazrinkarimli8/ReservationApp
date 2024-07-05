FROM openjdk:21
COPY target/ReservationApp-1.0-SNAPSHOT.jar reservation-app.jar
ENTRYPOINT ["java", "-jar", "reservation-app.jar"]
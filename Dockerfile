# Run database in separate container
# C:\Users\w371371\git\SpringBatch2Jobs>podman run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=YourPassword123!" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-CU10
# podman exec -it dreamy_einstein /bin/bash
# /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P YourPassword123! -Q "SELECT name FROM sys.databases;"
# /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P YourPassword123! -Q "CREATE DATABASE yourDb;"

# Build the Docker image
# podman build -t spring-boot-app .
# Run the Docker image
# podman run -p 8080:8080 spring-boot-app
FROM mcr.microsoft.com/java/jdk:17u0-zulu-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
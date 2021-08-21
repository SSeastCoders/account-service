
# cache the dependcies aside from core-library
FROM maven:3.8.2-openjdk-11 as dependency
WORKDIR /app

COPY core-library/pom.xml core-library/pom.xml
COPY account-api/pom.xml account-api/pom.xml 
COPY pom.xml .
RUN mvn dependency:go-offline -DexcludeArtifactIds=core-library

# stage 2 copy dependencies from cache and build project
FROM maven:3.8.2-openjdk-11 as builder
WORKDIR /app
COPY --from=dependency /root/.m2 /root/.m2
COPY --from=dependency /app /app
COPY core-library/src /app/core-library/src
COPY account-api/src /app/account-api/src
RUN mvn clean install -DskipTests

FROM openjdk:11-jdk
WORKDIR /app
COPY --from=builder /app/account-api/target/account-api-1.0.jar /app
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait
CMD /wait && java -jar /app/account-api-1.0.jar
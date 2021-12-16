FROM openjdk:11 AS build

COPY . ./

RUN apt-get update && apt-get upgrade -y

RUN apt install maven -y

RUN mvn clean install -DskipTests

FROM openjdk:11-jre-slim AS production

COPY --from=build "target/backend-0.0.1-SNAPSHOT.jar" app.jar

ENTRYPOINT ["java","-jar","app.jar"]
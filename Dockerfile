FROM openjdk:latest
WORKDIR /usr/app
COPY ./target/backend-0.0.1-SNAPSHOT.jar backend.jar
COPY ./wait_for_it.sh wait_for_it.sh
RUN sh -c 'chmod +x wait_for_it.sh'
#RUN sh -c './wait_for_it.sh db:3306'
#ENTRYPOINT ["java","-jar","backend.jar"]

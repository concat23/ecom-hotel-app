FROM maven:3.8.2 AS build
WORKDIR /ecom-hotel-app
COPY pom.xml /ecom-hotel-app
RUN mvn dependency:resolve
COPY . /ecom-hotel-app
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:8-jdk-oracle
COPY --from=build /ecom-hotel-app/target/*.jar ecom-hotel-app.jar
EXPOSE 8080
CMD ["java","-jar","ecom-hotel-app.jar"]
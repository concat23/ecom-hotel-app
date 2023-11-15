FROM maven:3.8.2 AS build
WORKDIR /ecom-hotel-app-backend
COPY pom.xml /ecom-hotel-app-backend
RUN mvn dependency:resolve
COPY . /ecom-hotel-app-backend
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:8-jdk-oracle
COPY --from=build /ecom-hotel-app-backend/target/*.jar ecom-hotel-app-backend.jar
EXPOSE 8080
CMD ["java","-jar","ecom-hotel-app-backend.jar"]
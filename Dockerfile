# Stage 1: Build stage
# FROM maven:3.9.7-openjdk-17 AS builder
# WORKDIR /app

# # Copy Maven files for dependency resolution
# COPY pom.xml ./
# COPY .mvn .mvn

# # Copy application source code
# COPY src src

# # take depencies to ram
# RUN mvn dependency:go-offline

# # Application build
# RUN mvn clean package -DskipTests


#Stage 2 : Run

FROM openjdk:17-jdk-slim

WORKDIR signalseek

# copy JAR file
COPY target/signalseek-*.jar signalseekapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "signalseekapi.jar"]
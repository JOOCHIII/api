# Fase 1: Construcción con Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos todo el código fuente del proyecto al contenedor
COPY . .

# Ejecutamos el comando Maven para compilar el proyecto y crear el archivo .jar
RUN mvn clean package -DskipTests

# Fase 2: Imagen para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establecemos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el .jar generado desde la fase anterior
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app/api.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Comando para ejecutar el .jar
CMD ["java", "-jar", "api.jar"]

# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar de tu aplicación al contenedor
COPY target/api-0.0.1-SNAPSHOT.jar /app/api.jar

# Expón el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar el .jar
CMD ["java", "-jar", "api.jar"]

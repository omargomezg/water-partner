# ----------------------------------------------------------------------
# ETAPA 1: BUILDER (Compilación con Maven y JDK 21)
# ----------------------------------------------------------------------
FROM maven:3.9-jdk-21-slim AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (pom.xml y settings.xml si existe)
# Esto permite que Docker cachee las dependencias si el pom.xml no cambia.
COPY pom.xml .

# Descarga las dependencias: Ejecuta una compilación sin tests/paquete final
# Esto llena el caché local de Maven para la compilación real posterior.
RUN mvn dependency:go-offline -B

# Copia el código fuente restante del proyecto
COPY src ./src

# Empaqueta la aplicación como un JAR ejecutable (el 'target/coop-0.0.1-SNAPSHOT.jar')
# Usamos -DskipTests para reducir el tiempo de build
RUN mvn package -DskipTests

# ----------------------------------------------------------------------
# ETAPA 2: JRE DE PRODUCCIÓN (Entorno de Ejecución Ligero)
# Usamos la imagen 'temurin' con solo el JRE (Java Runtime Environment)
# que es mucho más pequeño que el JDK completo.
# ----------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine AS final

# Establece el argumento para el nombre del JAR (basado en pom.xml)
ARG JAR_FILE=target/coop-0.0.1-SNAPSHOT.jar

# Define el puerto interno que la aplicación Spring Boot escuchará (por defecto)
EXPOSE 8080

# Define el usuario no-root para seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copia el JAR compilado desde la etapa 'builder'
COPY --from=builder /app/${JAR_FILE} app.jar

# Comando para ejecutar la aplicación Spring Boot
# Usa 'exec' para pasar la señal SIGTERM a Java, permitiendo un cierre elegante.
ENTRYPOINT ["java", "-jar", "app.jar"]
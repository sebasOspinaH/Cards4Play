# ── Stage 1: Build ──────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-25 AS builder

WORKDIR /app

# Copiar pom primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copiar código fuente y compilar fat JAR
COPY src ./src
RUN mvn package -q -DskipTests

# ── Stage 2: Runtime ─────────────────────────────────────────────────
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# Copiar el fat JAR
COPY --from=builder /app/target/cards4play-jar-with-dependencies.jar app.jar

# Directorio para persistencia de datos JSON
RUN mkdir -p /app/data

# Puerto de la aplicación
EXPOSE 8080

# Variable de entorno para la ruta de datos
ENV DATA_PATH=/app/data/state.json
ENV PORT=8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

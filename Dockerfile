# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]


## Etapa 1: build
#FROM maven:3.9.6-eclipse-temurin-21 AS build
#WORKDIR /app

# Copia o pom.xml e baixa dependências (melhora cache)
#COPY pom.xml .
#RUN mvn dependency:go-offline -B

# Copia o restante do código e constrói a aplicação
#COPY src ./src
#RUN mvn clean package -DskipTests

# Etapa 2: runtime
#FROM eclipse-temurin:21-jdk-alpine
#WORKDIR /app

# Copia o .jar gerado
#COPY --from=build /app/target/*.jar app.jar

# Define variáveis de ambiente padrão (podem ser sobrescritas)
#ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta do Spring Boot
#EXPOSE 8080

# Comando para rodar a aplicação
#ENTRYPOINT ["java", "-jar", "app.jar"]
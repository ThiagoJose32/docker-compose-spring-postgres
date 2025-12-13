<p align="center">
  <a href="https://spring.io/projects/spring-boot" target="blank">
    <img src="https://spring.io/images/spring-logo-2022-3c6a59b31f6e7c5988b0f18e0c1f7a54.svg" width="200" alt="Spring Boot Logo" />
  </a>
</p>

<p align="center">
  <a href="https://www.java.com/" target="_blank"><img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk" alt="Java 21" /></a>
  <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?logo=springboot" alt="Spring Boot" /></a>
  <a href="https://www.postgresql.org/" target="_blank"><img src="https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql" alt="PostgreSQL" /></a>
  <a href="https://www.docker.com/" target="_blank"><img src="https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker" alt="Docker" /></a>
  <a href="https://opensource.org/licenses/MIT" target="_blank"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License MIT" /></a>
</p>

---

## Description

This project is a **Java Spring Boot REST API** integrated with **PostgreSQL** for persistence, **pgAdmin** for database management, and a **frontend** built using **HTML, CSS, and JavaScript**.  
It is fully containerized with **Docker** and orchestrated using **Docker Compose** for easy deployment and environment setup.

---

## Project Setup

### Clone the repository

```bash
git clone https://github.com/ThiagoJose32/docker-compose-spring-postgres
```

### Create the .env file
```bash
# PostgreSQL
POSTGRES_DB=ara_campus_pets
POSTGRES_USER=user
POSTGRES_PASSWORD=password

# Spring Boot
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/ara_campus_pets
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_PROFILES_ACTIVE=prod

# pgAdmin
PGADMIN_DEFAULT_EMAIL=admin@admin.com
PGADMIN_DEFAULT_PASSWORD=admin
```

### Run with Docker Compose
```bash
docker compose up --build
```

## Services
Below are the services that make up the project environment:

| Service | Port | Description |
|----------|------|-------------|
| **Spring Boot API** | `8080` | Java backend API |
| **PostgreSQL** | `35432` | Database (mapped to `5432` inside the container) |
| **pgAdmin** | `5050` | Web interface for PostgreSQL database management |
| **Frontend** | `3000` | Static web interface (HTML, CSS, JavaScript) |

---

## CI/CD (Deploy)
### Pipeline Status
[![CI/CD Pipeline](https://github.com/ThiagoJose32/docker-compose-spring-postgres/actions/workflows/cicd.yml/badge.svg)](https://github.com/ThiagoJose32/docker-compose-spring-postgres/actions/workflows/cicd.yml)

### Pipeline Description

This project uses GitHub Actions to automate the Continuous Integration and Continuous Deployment (CI/CD) process.
The pipeline is triggered automatically on every push to the main branch and performs the following steps:
1. Checkout
    - Downloads the repository source code.
2. Java Setup
    - Configures Java 21 using actions/setup-java.
3. Build and Tests
    - Runs Maven to compile the project.
    - Executes automated tests to validate the application.
4. Deploy
    - Connects to the remote server using SSH.
    - Updates the project source code.
    - Rebuilds and restarts the containers using Docker Compose.

This ensures that every change pushed to the main branch is automatically tested and deployed.

### GitHub Secrets Configuration

The following GitHub Secrets must be configured for the deploy to work:

Path: `Settings > Secrets and variables > Actions`

| Secret Name | Description |
|----------|------|
| `SERVER_HOST` | **Server IP address or domain** |
| `SERVER_USER` | **SSH user on the server** |
| `SERVER_SSH_KEY` | **Private SSH key used for authentication** |
| `SERVER_PORT` | **SSH port (default: 22)** |

### Manual Steps on the Server

Before the first automated deploy, the following steps must be performed on the server.

1. Access the server
     
```bash
ssh user@SERVER_IP
```
2. Clone the repository
```bash
git clone https://github.com/ThiagoJose32/docker-compose-spring-postgres.git
cd docker-compose-spring-postgres
```
3. Create the .env file
```bash
nano .env
```

Add the required environment variables as shown in the Project Setup section.

4. Ensure Docker and Docker Compose are installed

```bash
docker --version
docker compose version
```
5. First manual deploy (optional)
```bash
docker compose up -d --build
```

After these steps, all future deployments will be handled automatically by GitHub Actions.

**Notes:**
- All services are orchestrated with **Docker Compose**.
- Networks and volumes are automatically configured when containers are started.
- You can access pgAdmin at: [http://localhost:5050](http://localhost:5050)
- The API will be available at: [http://localhost:8080](http://localhost:8080)

### Resources
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [PostgreSQL Documentation](https://docs.spring.io/spring-boot/)
- [Docker Documentation](https://docs.docker.com/)
- [pgAdmin Documentation](https://www.pgadmin.org/docs/)

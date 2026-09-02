# Asset Tracker API

Backend distribuido para gestionar y valorar activos digitales y físicos (criptomonedas, metales preciosos, etc.) mediante una arquitectura de microservicios basada en eventos.

| Servicio           | Repositorio                                                                 | Descripción                                           |
|--------------------|------------------------------------------------------------------------------|-------------------------------------------------------|
| Portfolio Service  | [🔗 Ver repo](https://github.com/Yerai-Araujo/asset-tracker-api-portfolio-service)  | Gestiona portfolios, activos y operaciones            |
| User Service       | [🔗 Ver repo](https://github.com/Yerai-Araujo/asset-tracker-api-user-service)       | Maneja autenticación y usuarios                       |
| Market Service     | [🔗 Ver repo](https://github.com/Yerai-Araujo/asset-tracker-api-market-service)     | Provee precios de mercado (cripto, metales)          |
| Read Model Service | [🔗 Ver repo](https://github.com/Yerai-Araujo/asset-tracker-api-read-model-service) | Construye vistas optimizadas para consulta (CQRS)    |

![Diagrama de la Arquitectura del Sistema](./images/Architecture_system_diagram.png)

# Descripción general de la arquitectura

Este sistema implementa una arquitectura Event-Driven + CQRS utilizando:

- Outbox Pattern para evitar dual writes
- Debezium (CDC) para capturar cambios en la base de datos
- Kafka como event bus
- MongoDB como base de datos optimizada para lectura

# Tech Stack

## Backend
- Java + Spring Boot
- Spring Data JPA
- Spring Kafka

## Data & Streaming
- PostgreSQL
- MongoDB (Read Model)
- Apache Kafka
- Debezium (CDC)

## Infraestructura
- Docker & Docker Compose (El sistema se ejecuta completamente con Docker)

# Instalación y ejecución

## Requisitos

- Git
- Docker Engine y Docker Compose v2 (`docker compose`)
- Una API key válida para el servicio de precios de metales

## Descargar todos los repositorios

El archivo `docker-compose.yml` busca los repositorios de los microservicios como carpetas hermanas de este repositorio. Ejecuta los siguientes comandos desde la carpeta donde quieras guardar el proyecto:

```bash
mkdir asset-tracker
cd asset-tracker

git clone https://github.com/Yerai-Araujo/asset-tracker-api-microservices.git
git clone https://github.com/Yerai-Araujo/asset-tracker-api-user-service.git
git clone https://github.com/Yerai-Araujo/asset-tracker-api-portfolio-service.git
git clone https://github.com/Yerai-Araujo/asset-tracker-api-market-service.git
git clone https://github.com/Yerai-Araujo/asset-tracker-api-read-model-service.git

cd asset-tracker-api-microservices
```

La estructura resultante debe ser similar a esta:

```text
asset-tracker/
├── asset-tracker-api-microservices/
├── asset-tracker-api-user-service/
├── asset-tracker-api-portfolio-service/
├── asset-tracker-api-market-service/
└── asset-tracker-api-read-model-service/
```

## Configurar variables de entorno

El archivo `.env` no se incluye en Git. Créalo dentro de `asset-tracker-api-microservices` con valores de desarrollo:

```dotenv
POSTGRES_PASSWORD=12345678
SPRING_DATASOURCE_PASSWORD=12345678
METAL.PRICE.API.KEY=REEMPLAZA_CON_TU_API_KEY
MONGO_USER=asset_user
MONGO_PASS=asset_pass
SPRING_DATA_MONGODB_URI=mongodb://${MONGO_USER}:${MONGO_PASS}@mongo:27017/asset_tracker?authSource=admin
```

## Levantar el sistema

Desde `asset-tracker-api-microservices`, construye las imágenes y arranca todos los contenedores:

```bash
docker compose up --build -d
```

Comprueba que los contenedores estén ejecutándose:

```bash
docker compose ps
```

Cuando Kafka Connect esté disponible, registra los conectores Debezium para PostgreSQL:

```bash
./register-connectors.sh
```

Si el script no tiene permisos de ejecución, utiliza:

```bash
bash register-connectors.sh
```

Los servicios quedan disponibles en estas direcciones:

| Servicio | URL |
|----------|-----|
| Portfolio Service | `http://localhost:8080` |
| User Service | `http://localhost:8081` |
| Market Service | `http://localhost:8082` |
| Kafka Connect | `http://localhost:8083` |
| Read Model Service | `http://localhost:8085` |
| MongoDB | `localhost:27017` |

Para consultar los logs:

```bash
docker compose logs -f
```

Para detener el sistema sin eliminar los datos persistidos:

```bash
docker compose down
```

Para detenerlo y eliminar también los volúmenes de PostgreSQL, MongoDB y Kafka:

```bash
docker compose down -v
```

# Key Features

- Event-Driven Architecture
- CQRS (Command Query Responsibility Segregation)
- Outbox Pattern (consistencia garantizada)
- CDC con Debezium (sin dual writes)
- Escalabilidad y bajo acoplamiento
- Read models optimizados para consultas

# Author

Software Engineer especializado en:

- Microservices
- Event-Driven Systems
- Backend con Java & Spring Boot
- Frontend con Angular
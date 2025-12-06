# Advance Contact API

A modern, production-ready Spring Boot REST API for customer management with comprehensive features including internationalization, caching, security, and full API documentation.

## Features

- **Customer Management**: Complete CRUD operations for customer records
- **Search & Pagination**: Advanced search with paginated results (25 per page default)
- **Internationalization (i18n)**: Multi-language support (English, French, Arabic, Bengali)
- **API Documentation**: Interactive Swagger UI for API exploration
- **Security**: Spring Security with JWT authentication
- **Caching**: Multi-layer caching with Caffeine and Redis
- **Validation**: Comprehensive input validation with detailed error messages
- **Monitoring**: Spring Actuator endpoints for health checks and metrics
- **Database**: PostgreSQL with JPA/Hibernate
- **Testing**: Unit and integration tests with JaCoCo coverage reports
- **Containerization**: Docker Compose setup for local development

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.1**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - Spring Cache
  - Spring Actuator
- **PostgreSQL** (Primary database)
- **Redis** (Caching layer)
- **H2** (In-memory database for testing)
- **JWT** (Authentication tokens)
- **Lombok** (Boilerplate reduction)
- **MapStruct** (Object mapping)
- **OpenAPI/Swagger** (API documentation)
- **Gradle** (Build tool)
- **JaCoCo** (Code coverage)
- **Testcontainers** (Integration testing)

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** or higher
- **Docker & Docker Compose** (for PostgreSQL and Redis)
- **Gradle** (or use the included Gradle wrapper)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd advance-contact-config
```

### 2. Start Infrastructure Services

Start PostgreSQL and pgAdmin using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port `5432`
- **pgAdmin** on port `5050` (http://localhost:5050)

### 3. Configure Database

Update the database credentials in `docker-compose.yml` if needed:

```yaml
POSTGRES_USER: {username}
POSTGRES_PASSWORD: {password}
```

Then update the corresponding values in `src/main/resources/application-dev.properties`.

### 4. Build the Application

```bash
./gradlew build
```

### 5. Run the Application

```bash
./gradlew bootRun
```

The application will start on **http://localhost:8080**

## API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## Available Endpoints

### Customer Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/customers` | Get all customers (paginated) |
| GET | `/api/v1/customers/{id}` | Get customer by ID |
| POST | `/api/v1/customers` | Create new customer |
| PUT | `/api/v1/customers/{id}` | Update customer |
| DELETE | `/api/v1/customers/{id}` | Delete customer |
| GET | `/api/v1/customers/search?query={query}` | Search customers by name or email |

### Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |
| `/actuator/prometheus` | Prometheus metrics |

## Configuration

The application supports multiple profiles:

- **dev**: Development environment (default)
- **test**: Testing environment
- **prod**: Production environment

To run with a specific profile:

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

### Key Configuration Files

- `application.properties` - Main configuration
- `application-dev.properties` - Development settings
- `application-test.properties` - Test settings
- `application-prod.properties` - Production settings

### Internationalization

Supported languages are configured in `src/main/resources/i18n/`:

- `messages_en.properties` - English
- `messages_fr.properties` - French
- `messages_ar.properties` - Arabic
- `messages_bn.properties` - Bengali

Default language can be changed in `application.properties`:

```properties
spring.web.locale=en
```

## Sample Request/Response

### Create Customer

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 30,
    "phone": "+1234567890",
    "address": "123 Main Street"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Customer created successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 30,
    "phone": "+1234567890",
    "address": "123 Main Street",
    "status": "ACTIVE",
    "createdAt": "2025-12-06T15:30:00",
    "updatedAt": "2025-12-06T15:30:00",
    "version": 0
  },
  "language": "en"
}
```

## Testing

### Run All Tests

```bash
./gradlew test
```

### Generate Code Coverage Report

```bash
./gradlew jacocoTestReport
```

Coverage reports will be generated in `build/reports/jacoco/test/html/index.html`

## Database Access

### pgAdmin

Access pgAdmin at http://localhost:5050

**Default credentials:**
- Email: `pgadmin4@pgadmin.org`
- Password: `admin`

To connect to PostgreSQL from pgAdmin:
1. Click "Add New Server"
2. Name: `Local PostgreSQL`
3. Connection tab:
   - Host: `postgres` (or `localhost` if accessing from host machine)
   - Port: `5432`
   - Username: Your configured username
   - Password: Your configured password

## Project Structure

```
advance-contact-config/
├── src/
│   ├── main/
│   │   ├── java/com/mra/
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── customer/         # Customer domain
│   │   │   ├── dto/              # Data transfer objects
│   │   │   ├── exception/        # Exception handling
│   │   │   ├── service/          # Business logic
│   │   │   └── util/             # Utility classes
│   │   └── resources/
│   │       ├── i18n/             # Internationalization files
│   │       ├── application.properties
│   │       └── application-{profile}.properties
│   └── test/                     # Test classes
├── build.gradle                  # Gradle build configuration
├── docker-compose.yml            # Docker services configuration
└── README.md                     # This file
```

## Development Tips

### Hot Reload

The project includes Spring Boot DevTools for automatic restart during development. Simply make changes to your code and save - the application will restart automatically.

### Logging

Log levels can be adjusted in `application.properties`:

```properties
logging.level.com.mra=DEBUG
```

### Pagination

Default pagination settings can be customized:

```properties
spring.data.web.pageable.default-page-size=25
spring.data.web.pageable.max-page-size=100
```

### Caching

Cache configuration can be adjusted in `application.properties`:

```properties
spring.cache.caffeine.spec=maximumSize=1000,expireAfterWrite=10m
```

## Troubleshooting

### Port Already in Use

If port 8080 is already in use, change it in `application.properties`:

```properties
server.port=8081
```

### Database Connection Issues

1. Ensure Docker containers are running: `docker ps`
2. Check Docker logs: `docker logs postgres`
3. Verify credentials in `docker-compose.yml` and `application-dev.properties` match

### Build Failures

Clean and rebuild:

```bash
./gradlew clean build
```

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please open an issue in the repository.

---

**Built with ❤️ using Spring Boot 3 and Java 21**

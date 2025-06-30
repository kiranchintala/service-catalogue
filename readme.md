# Service Catalogue API Documentation

## Overview

The Service Catalogue is a Spring Boot microservice that provides a RESTful API for managing services offered by MTBS. It supports full CRUD operations (Create, Read, Update, Delete) with pagination support and comprehensive error handling.

**Version**: 1.0-SNAPSHOT  
**Base URL**: `http://localhost:8081`  
**API Base Path**: `/api/v1/services`

## Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Java Version**: 17
- **Database**: H2 (in-memory/file-based)
- **Mapping**: MapStruct 1.5.5.Final
- **Build Tool**: Maven
- **Logging**: SLF4J with Logback

## Table of Contents

1. [Quick Start](#quick-start)
2. [REST API Endpoints](#rest-api-endpoints)
3. [Data Models](#data-models)
4. [Error Handling](#error-handling)
5. [Business Logic Components](#business-logic-components)
6. [Data Access Layer](#data-access-layer)
7. [Configuration](#configuration)
8. [Examples](#examples)

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application
```bash
# Clone the repository
git clone <repository-url>
cd service-catalogue

# Build the application
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on port 8081. You can access:
- API endpoints: `http://localhost:8081/api/v1/services`
- H2 Console: `http://localhost:8081/h2-console`

## REST API Endpoints

### 1. Create Service
**Endpoint**: `POST /api/v1/services`  
**Description**: Creates a new service in the catalogue

#### Request Body
```json
{
  "name": "string",           // Required, max 100 characters
  "description": "string",    // Optional, max 500 characters
  "price": 0.0,              // Required, must be positive
  "durationInMinutes": 0,    // Required, integer
  "active": true             // Optional, defaults to true
}
```

#### Response
**Status**: `201 Created`
```json
{
  "id": "uuid",
  "name": "string",
  "description": "string",
  "price": 0.0,
  "durationInMinutes": 0,
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "version": 0
}
```

#### Example
```bash
curl -X POST http://localhost:8081/api/v1/services \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hair Cut",
    "description": "Professional hair cutting service",
    "price": 25.99,
    "durationInMinutes": 30,
    "active": true
  }'
```

### 2. Get All Services (Paginated)
**Endpoint**: `GET /api/v1/services`  
**Description**: Retrieves all services with pagination support

#### Query Parameters
- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort criteria (e.g., `name,asc` or `createdAt,desc`)

#### Response
**Status**: `200 OK`
```json
{
  "content": [
    {
      "id": "uuid",
      "name": "string",
      "description": "string",
      "price": 0.0,
      "durationInMinutes": 0,
      "active": true,
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00",
      "version": 0
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "sorted": false,
      "empty": true
    }
  },
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true,
  "numberOfElements": 1
}
```

#### Example
```bash
# Get first page with 10 items, sorted by name ascending
curl "http://localhost:8081/api/v1/services?page=0&size=10&sort=name,asc"
```

### 3. Get Service by ID
**Endpoint**: `GET /api/v1/services/{id}`  
**Description**: Retrieves a specific service by its UUID

#### Path Parameters
- `id`: UUID of the service

#### Response
**Status**: `200 OK`
```json
{
  "id": "uuid",
  "name": "string",
  "description": "string",
  "price": 0.0,
  "durationInMinutes": 0,
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "version": 0
}
```

#### Example
```bash
curl http://localhost:8081/api/v1/services/123e4567-e89b-12d3-a456-426614174000
```

### 4. Update Service
**Endpoint**: `PUT /api/v1/services/{id}`  
**Description**: Updates an existing service

#### Path Parameters
- `id`: UUID of the service to update

#### Request Body
Same as Create Service request body

#### Response
**Status**: `200 OK`
```json
{
  "id": "uuid",
  "name": "string",
  "description": "string",
  "price": 0.0,
  "durationInMinutes": 0,
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:00:00",
  "version": 1
}
```

#### Example
```bash
curl -X PUT http://localhost:8081/api/v1/services/123e4567-e89b-12d3-a456-426614174000 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Premium Hair Cut",
    "description": "Premium hair cutting service with styling",
    "price": 35.99,
    "durationInMinutes": 45,
    "active": true
  }'
```

### 5. Delete Service
**Endpoint**: `DELETE /api/v1/services/{id}`  
**Description**: Deletes a service from the catalogue

#### Path Parameters
- `id`: UUID of the service to delete

#### Response
**Status**: `204 No Content`

#### Example
```bash
curl -X DELETE http://localhost:8081/api/v1/services/123e4567-e89b-12d3-a456-426614174000
```

## Data Models

### ServiceResponse (Output DTO)
Represents a service in API responses.

| Field | Type | Description |
|-------|------|-------------|
| `id` | UUID | Unique identifier |
| `name` | String | Service name |
| `description` | String | Service description |
| `price` | Double | Service price |
| `durationInMinutes` | Integer | Service duration |
| `active` | Boolean | Whether service is active |
| `createdAt` | LocalDateTime | Creation timestamp |
| `updatedAt` | LocalDateTime | Last update timestamp |
| `version` | Long | Version for optimistic locking |

### CreateServiceRequest (Input DTO)
Used for creating and updating services.

| Field | Type | Required | Validation | Description |
|-------|------|----------|------------|-------------|
| `name` | String | Yes | Not blank, max 100 chars | Service name |
| `description` | String | No | Max 500 chars | Service description |
| `price` | Double | Yes | Positive number | Service price |
| `durationInMinutes` | Integer | Yes | Not null | Duration in minutes |
| `active` | Boolean | No | - | Active status (defaults to true) |

### ServiceModel (Entity)
Database entity representing a service.

| Field | Type | Database Constraints | Description |
|-------|------|---------------------|-------------|
| `id` | UUID | Primary key, auto-generated | Unique identifier |
| `version` | Long | Version column | For optimistic locking |
| `name` | String | Not null, max 100 chars | Service name |
| `description` | String | Max 500 chars | Service description |
| `price` | Double | Not null, positive | Service price |
| `durationInMinutes` | Integer | Not null | Duration in minutes |
| `active` | Boolean | Not null, default true | Active status |
| `createdAt` | LocalDateTime | Not updatable | Creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

## Error Handling

The API uses a global exception handler that returns standardized error responses.

### Error Response Format
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error description",
  "path": "uri=/api/v1/services",
  "validationErrors": {
    "fieldName": "Field-specific error message"
  }
}
```

### HTTP Status Codes

| Status Code | Description | When It Occurs |
|-------------|-------------|----------------|
| `200 OK` | Success | Successful GET, PUT operations |
| `201 Created` | Created | Successful POST operation |
| `204 No Content` | Success | Successful DELETE operation |
| `400 Bad Request` | Validation Error | Invalid input data |
| `404 Not Found` | Resource Not Found | Service with given ID doesn't exist |
| `409 Conflict` | Conflict | Optimistic locking failure or data integrity violation |
| `500 Internal Server Error` | Server Error | Unexpected server errors |

### Exception Types

1. **ServiceNotFoundException** (404)
   - Thrown when a requested service is not found
   - Example: Getting, updating, or deleting a non-existent service

2. **MethodArgumentNotValidException** (400)
   - Thrown when request validation fails
   - Includes field-specific validation errors

3. **ObjectOptimisticLockingFailureException** (409)
   - Thrown when optimistic locking fails
   - Occurs when multiple users try to update the same service simultaneously

4. **DataIntegrityViolationException** (409)
   - Thrown when database constraints are violated
   - Example: Trying to delete a service that's referenced by appointments

## Business Logic Components

### ServiceCatalogue Interface
The main business logic interface defining service operations.

```java
public interface ServiceCatalogue {
    ServiceResponse createService(CreateServiceRequest request);
    Page<ServiceResponse> getAllServices(Pageable pageable);
    ServiceResponse getServiceById(UUID id);
    ServiceResponse updateService(UUID id, CreateServiceRequest request);
    void deleteService(UUID id);
}
```

#### Methods

**`createService(CreateServiceRequest request)`**
- Creates a new service
- Sets `active` to `true` if not provided
- Returns the created service with generated ID and timestamps
- Transactional operation

**`getAllServices(Pageable pageable)`**
- Retrieves paginated list of services
- Supports sorting and pagination
- Read-only transaction
- Returns `Page<ServiceResponse>` with pagination metadata

**`getServiceById(UUID id)`**
- Retrieves a single service by ID
- Throws `ServiceNotFoundException` if not found
- Read-only transaction

**`updateService(UUID id, CreateServiceRequest request)`**
- Updates an existing service
- Preserves creation timestamp and ID
- Updates version for optimistic locking
- Throws `ServiceNotFoundException` if service doesn't exist

**`deleteService(UUID id)`**
- Deletes a service by ID
- Throws `ServiceNotFoundException` if service doesn't exist
- May throw `DataIntegrityViolationException` if service is referenced

### ServiceCatalogueImpl
Implementation of the `ServiceCatalogue` interface.

#### Dependencies
- `ServiceRepository`: Data access layer
- `ServiceMapper`: Object mapping between entities and DTOs

#### Transaction Management
- All write operations are transactional
- Read operations use read-only transactions for performance
- Optimistic locking prevents concurrent modification issues

## Data Access Layer

### ServiceRepository
Spring Data JPA repository for database operations.

```java
@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, UUID> {
}
```

#### Inherited Methods
- `save(ServiceModel entity)`: Save or update a service
- `findById(UUID id)`: Find service by ID
- `findAll(Pageable pageable)`: Find all services with pagination
- `deleteById(UUID id)`: Delete service by ID
- `existsById(UUID id)`: Check if service exists

#### Database Configuration
- **URL**: `jdbc:h2:file:./data/servicecataloguedb`
- **Driver**: H2 Database
- **Schema**: Auto-generated/updated on startup
- **Console**: Available at `/h2-console` for development

## Configuration

### WebConfig
CORS configuration for cross-origin requests.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3002")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### Application Properties
Key configuration settings:

| Property | Value | Description |
|----------|-------|-------------|
| `server.port` | 8081 | Server port |
| `spring.application.name` | service-catalogue | Application name |
| `spring.h2.console.enabled` | true | Enable H2 console |
| `spring.datasource.url` | jdbc:h2:file:./data/servicecataloguedb | Database URL |
| `spring.jpa.hibernate.ddl-auto` | update | Schema update strategy |

### ServiceMapper
MapStruct mapper for object transformations.

```java
@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceModel toEntity(CreateServiceRequest dto);
    ServiceResponse toResponse(ServiceModel entity);
    List<ServiceResponse> toResponseList(List<ServiceModel> entities);
    void updateEntityFromDto(CreateServiceRequest dto, @MappingTarget ServiceModel entity);
}
```

#### Mapping Rules
- `toEntity`: Ignores `active` field (handled separately in service layer)
- `toResponse`: Direct mapping from entity to response DTO
- `updateEntityFromDto`: Ignores ID, timestamps, and version fields during updates

## Examples

### Complete CRUD Workflow

#### 1. Create a Service
```bash
curl -X POST http://localhost:8081/api/v1/services \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Deep Tissue Massage",
    "description": "Therapeutic massage targeting muscle knots and tension",
    "price": 89.99,
    "durationInMinutes": 60
  }'
```

Response:
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "name": "Deep Tissue Massage",
  "description": "Therapeutic massage targeting muscle knots and tension",
  "price": 89.99,
  "durationInMinutes": 60,
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "version": 0
}
```

#### 2. Retrieve All Services
```bash
curl "http://localhost:8081/api/v1/services?page=0&size=5&sort=price,asc"
```

#### 3. Get Specific Service
```bash
curl http://localhost:8081/api/v1/services/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

#### 4. Update Service
```bash
curl -X PUT http://localhost:8081/api/v1/services/a1b2c3d4-e5f6-7890-abcd-ef1234567890 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Premium Deep Tissue Massage",
    "description": "Premium therapeutic massage with hot stones",
    "price": 109.99,
    "durationInMinutes": 75,
    "active": true
  }'
```

#### 5. Delete Service
```bash
curl -X DELETE http://localhost:8081/api/v1/services/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

### Error Handling Examples

#### Validation Error
```bash
curl -X POST http://localhost:8081/api/v1/services \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "price": -10.0
  }'
```

Response (400 Bad Request):
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields",
  "path": "uri=/api/v1/services",
  "validationErrors": {
    "name": "Service name cannot be blank",
    "price": "must be greater than 0",
    "durationInMinutes": "Duration in minutes cannot be null"
  }
}
```

#### Not Found Error
```bash
curl http://localhost:8081/api/v1/services/nonexistent-id
```

Response (404 Not Found):
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Service with ID nonexistent-id not found.",
  "path": "uri=/api/v1/services/nonexistent-id"
}
```

### Advanced Usage

#### Pagination with Sorting
```bash
# Get second page with 10 items, sorted by creation date descending
curl "http://localhost:8081/api/v1/services?page=1&size=10&sort=createdAt,desc"

# Multiple sort criteria
curl "http://localhost:8081/api/v1/services?page=0&size=20&sort=active,desc&sort=price,asc"
```

#### Filtering Active Services (Custom Implementation Required)
While the current API doesn't include filtering by active status, you can implement custom repository methods:

```java
// In ServiceRepository
Page<ServiceModel> findByActive(boolean active, Pageable pageable);
List<ServiceModel> findByNameContainingIgnoreCase(String name);
Page<ServiceModel> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
```

## Testing

### Manual Testing with H2 Console
1. Start the application
2. Navigate to `http://localhost:8081/h2-console`
3. Use connection details:
   - JDBC URL: `jdbc:h2:file:./data/servicecataloguedb`
   - Username: `sa`
   - Password: `password`
4. Query the `SERVICES` table directly

### Sample Test Data
```sql
INSERT INTO SERVICES (ID, NAME, DESCRIPTION, PRICE, DURATION_IN_MINUTES, ACTIVE, CREATED_AT, UPDATED_AT, VERSION) 
VALUES 
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Hair Cut', 'Basic hair cutting service', 25.99, 30, true, NOW(), NOW(), 0),
  ('b2c3d4e5-f6g7-8901-bcde-f23456789012', 'Hair Wash', 'Professional hair washing', 15.99, 15, true, NOW(), NOW(), 0),
  ('c3d4e5f6-g7h8-9012-cdef-345678901234', 'Hair Styling', 'Hair styling and finishing', 35.99, 45, true, NOW(), NOW(), 0);
```

## Security Considerations

1. **Input Validation**: All inputs are validated using Bean Validation annotations
2. **SQL Injection**: Prevented by using Spring Data JPA parameterized queries
3. **CORS**: Configured to allow specific origins only
4. **Optimistic Locking**: Prevents concurrent modification conflicts
5. **Error Information**: Error responses don't expose sensitive system information

## Performance Considerations

1. **Pagination**: All list endpoints support pagination to handle large datasets
2. **Read-Only Transactions**: Used for all query operations
3. **Database Indexing**: Consider adding indexes on frequently queried fields
4. **Connection Pooling**: Configured via Spring Boot auto-configuration
5. **Logging**: Structured logging for monitoring and debugging

## Monitoring and Logging

The application uses SLF4J with Logback for comprehensive logging:

- **INFO**: Business operations (create, update, delete)
- **WARN**: Recoverable errors (validation failures, not found)
- **ERROR**: System errors (database connectivity, unexpected exceptions)

Log patterns include:
- Request/response logging in controllers
- Business operation logging in services
- Exception details in global exception handler

## Future Enhancements

Potential areas for expansion:

1. **Search and Filtering**: Add endpoints for searching services by name, price range, etc.
2. **Service Categories**: Group services into categories
3. **Service Availability**: Track service availability and scheduling
4. **Authentication/Authorization**: Add security layer with roles and permissions
5. **Caching**: Implement Redis caching for frequently accessed services
6. **Metrics**: Add application metrics and health checks
7. **API Documentation**: Integrate Swagger/OpenAPI for interactive documentation
8. **File Upload**: Support for service images and documents
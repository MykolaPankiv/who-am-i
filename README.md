# Who Am I - Games API

Eleks Engineering Academy (Spring 2022) - A Spring Boot REST API for managing games with pagination support.

## Features

- **Games CRUD Operations**: Create, read, update, and delete games
- **Pagination Support**: Efficient pagination with configurable page size and sorting
- **Filtering**: Filter games by genre, title, platform, and rating range
- **Sorting**: Sort games by any field in ascending or descending order
- **RESTful API**: Well-structured REST endpoints with proper HTTP status codes
- **Comprehensive Testing**: Unit tests and integration tests included

## Technology Stack

- **Java 11**
- **Spring Boot 2.7.0**
- **Spring Data JPA**
- **H2 Database** (in-memory for development)
- **Maven** (build tool)
- **JUnit 5** (testing)

## Getting Started

### Prerequisites

- Java 11 or later
- Maven 3.6+

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`

### Running Tests

```bash
mvn test
```

## API Documentation

The API provides the following endpoints:

### Games Endpoint

- **GET** `/api/games` - Get paginated list of games with optional filtering and sorting
- **GET** `/api/games/{id}` - Get a specific game by ID
- **POST** `/api/games` - Create a new game
- **PUT** `/api/games/{id}` - Update an existing game
- **DELETE** `/api/games/{id}` - Delete a game

### Query Parameters for GET /api/games

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number (0-based) |
| `size` | int | 10 | Number of items per page |
| `sortBy` | string | "id" | Field to sort by |
| `sortDir` | string | "asc" | Sort direction ("asc" or "desc") |
| `genre` | string | - | Filter by genre |
| `title` | string | - | Filter by title |
| `platform` | string | - | Filter by platform |
| `minRating` | double | - | Minimum rating filter |
| `maxRating` | double | - | Maximum rating filter |

### Example Requests

```bash
# Get first 5 games
curl "http://localhost:8080/api/games?page=0&size=5"

# Get games sorted by rating (highest first)
curl "http://localhost:8080/api/games?sortBy=rating&sortDir=desc"

# Filter by genre
curl "http://localhost:8080/api/games?genre=RPG"

# Get games with rating between 8.0 and 10.0
curl "http://localhost:8080/api/games?minRating=8.0&maxRating=10.0"

# Get specific game
curl "http://localhost:8080/api/games/1"
```

## Sample Data

The application initializes with 15 sample games including popular titles like:
- The Witcher 3: Wild Hunt
- Cyberpunk 2077
- Minecraft
- Grand Theft Auto V
- And more...

## Database

The application uses H2 in-memory database for development. The H2 console is available at `http://localhost:8080/h2-console` with:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Project Structure

```
src/
├── main/
│   ├── java/com/eleks/academy/whoami/
│   │   ├── WhoAmIApplication.java          # Main application class
│   │   ├── controller/
│   │   │   └── GameController.java         # REST controller
│   │   ├── entity/
│   │   │   └── Game.java                   # JPA entity
│   │   ├── repository/
│   │   │   └── GameRepository.java         # Data access layer
│   │   └── service/
│   │       └── DataInitializationService.java # Sample data setup
│   └── resources/
│       └── application.properties          # Application configuration
└── test/
    ├── java/com/eleks/academy/whoami/
    │   ├── controller/
    │   │   └── GameControllerTest.java      # Unit tests
    │   └── integration/
    │       └── GameControllerIntegrationTest.java # Integration tests
    └── resources/
        └── application-test.properties      # Test configuration
```

## Response Format

The paginated response includes both the data and pagination metadata:

```json
{
  "content": [
    {
      "id": 1,
      "title": "Game Title",
      "genre": "RPG",
      "developer": "Developer Name",
      "publisher": "Publisher Name",
      "releaseDate": "2023-01-01",
      "platform": "PC, PlayStation",
      "rating": 9.0,
      "description": "Game description"
    }
  ],
  "currentPage": 0,
  "totalItems": 15,
  "totalPages": 2,
  "pageSize": 10,
  "first": true,
  "last": false,
  "hasNext": true,
  "hasPrevious": false
}
```

## License

This project is part of the Eleks Engineering Academy curriculum.

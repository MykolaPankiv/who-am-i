# API Documentation - Games Endpoint

## Overview
This API provides endpoints to manage and retrieve games with pagination support.

## Base URL
```
http://localhost:8080/api/games
```

## Endpoints

### GET /api/games
Retrieve a paginated list of games with optional filtering and sorting.

#### Query Parameters
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number (0-based) |
| `size` | int | 10 | Number of items per page |
| `sortBy` | string | "id" | Field to sort by (id, title, genre, rating, etc.) |
| `sortDir` | string | "asc" | Sort direction ("asc" or "desc") |
| `genre` | string | - | Filter by genre (case-insensitive, partial match) |
| `title` | string | - | Filter by title (case-insensitive, partial match) |
| `platform` | string | - | Filter by platform (case-insensitive, partial match) |
| `minRating` | double | - | Minimum rating filter (use with maxRating) |
| `maxRating` | double | - | Maximum rating filter (use with minRating) |

#### Response Format
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

#### Examples

**Basic pagination:**
```bash
GET /api/games?page=0&size=5
```

**Sorted by rating (highest first):**
```bash
GET /api/games?sortBy=rating&sortDir=desc
```

**Filter by genre:**
```bash
GET /api/games?genre=RPG
```

**Filter by rating range:**
```bash
GET /api/games?minRating=8.0&maxRating=10.0
```

### GET /api/games/{id}
Retrieve a specific game by ID.

#### Response
```json
{
  "id": 1,
  "title": "The Witcher 3: Wild Hunt",
  "genre": "RPG",
  "developer": "CD Projekt Red",
  "publisher": "CD Projekt",
  "releaseDate": "2015-05-19",
  "platform": "PC, PlayStation, Xbox",
  "rating": 9.3,
  "description": "An epic open-world fantasy RPG following Geralt of Rivia."
}
```

Returns 404 if game not found.

### POST /api/games
Create a new game.

#### Request Body
```json
{
  "title": "New Game",
  "genre": "Action",
  "developer": "Developer Name",
  "publisher": "Publisher Name",
  "releaseDate": "2023-12-01",
  "platform": "PC",
  "rating": 8.5,
  "description": "Description of the new game"
}
```

### PUT /api/games/{id}
Update an existing game.

#### Request Body
Same as POST request.

Returns 404 if game not found.

### DELETE /api/games/{id}
Delete a game by ID.

Returns 404 if game not found.

## Error Responses

**404 Not Found:**
```json
{
  "timestamp": "2023-07-24T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/games/999"
}
```

**400 Bad Request:**
```json
{
  "timestamp": "2023-07-24T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/games"
}
```
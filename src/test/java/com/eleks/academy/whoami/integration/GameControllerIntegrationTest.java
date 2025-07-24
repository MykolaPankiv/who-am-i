package com.eleks.academy.whoami.integration;

import com.eleks.academy.whoami.entity.Game;
import com.eleks.academy.whoami.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class GameControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testGetGamesWithPagination() throws Exception {
        // Test getting games with pagination
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/games?page=0&size=5", 
                Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("currentPage")).isEqualTo(0);
        assertThat(body.get("pageSize")).isEqualTo(5);
        assertThat(body.get("content")).isNotNull();
    }

    @Test
    public void testGetGamesWithSorting() throws Exception {
        // Test getting games with sorting
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/games?sortBy=rating&sortDir=desc", 
                Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("content")).isNotNull();
    }

    @Test
    public void testGetGamesWithFilter() throws Exception {
        // Test getting games with genre filter
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/games?genre=RPG", 
                Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("content")).isNotNull();
    }

    @Test
    public void testGetGameById() throws Exception {
        // Test getting a specific game by ID
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/games/1", 
                Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("id")).isEqualTo(1);
        assertThat(body.get("title")).isNotNull();
    }

    @Test
    public void testGameNotFound() throws Exception {
        // Test getting non-existent game
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/games/999999", 
                Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateGame() throws Exception {
        // Test creating a new game
        Game newGame = new Game("Integration Test Game", "Test Genre", "Test Dev", "Test Pub", LocalDate.now(), "Test Platform", 8.0, "Test Description");
        
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/games", 
                newGame, 
                Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("title")).isEqualTo("Integration Test Game");
        assertThat(body.get("id")).isNotNull();
    }
}
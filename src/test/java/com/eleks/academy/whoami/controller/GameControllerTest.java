package com.eleks.academy.whoami.controller;

import com.eleks.academy.whoami.entity.Game;
import com.eleks.academy.whoami.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllGames_DefaultPagination() throws Exception {
        // Arrange
        List<Game> games = Arrays.asList(
                new Game("Game 1", "RPG", "Developer 1", "Publisher 1", LocalDate.now(), "PC", 8.5, "Description 1"),
                new Game("Game 2", "Action", "Developer 2", "Publisher 2", LocalDate.now(), "PlayStation", 9.0, "Description 2")
        );
        Page<Game> gamesPage = new PageImpl<>(games, PageRequest.of(0, 10), 2);
        
        when(gameRepository.findAll(any(Pageable.class))).thenReturn(gamesPage);

        // Act & Assert
        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(false));
    }

    @Test
    public void testGetAllGames_CustomPagination() throws Exception {
        // Arrange
        List<Game> games = Arrays.asList(
                new Game("Game 3", "Adventure", "Developer 3", "Publisher 3", LocalDate.now(), "Xbox", 7.5, "Description 3")
        );
        Page<Game> gamesPage = new PageImpl<>(games, PageRequest.of(1, 5), 10);
        
        when(gameRepository.findAll(any(Pageable.class))).thenReturn(gamesPage);

        // Act & Assert
        mockMvc.perform(get("/api/games")
                        .param("page", "1")
                        .param("size", "5")
                        .param("sortBy", "title")
                        .param("sortDir", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.totalItems").value(10))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(true));
    }

    @Test
    public void testGetAllGames_WithGenreFilter() throws Exception {
        // Arrange
        List<Game> games = Arrays.asList(
                new Game("RPG Game", "RPG", "Developer", "Publisher", LocalDate.now(), "PC", 8.0, "RPG Description")
        );
        Page<Game> gamesPage = new PageImpl<>(games, PageRequest.of(0, 10), 1);
        
        when(gameRepository.findByGenreContainingIgnoreCase(eq("RPG"), any(Pageable.class))).thenReturn(gamesPage);

        // Act & Assert
        mockMvc.perform(get("/api/games")
                        .param("genre", "RPG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].genre").value("RPG"));
    }

    @Test
    public void testGetGameById_Success() throws Exception {
        // Arrange
        Game game = new Game("Test Game", "Action", "Test Developer", "Test Publisher", LocalDate.now(), "PC", 8.5, "Test Description");
        game.setId(1L);
        
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act & Assert
        mockMvc.perform(get("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Game"))
                .andExpect(jsonPath("$.genre").value("Action"));
    }

    @Test
    public void testGetGameById_NotFound() throws Exception {
        // Arrange
        when(gameRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/games/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateGame() throws Exception {
        // Arrange
        Game newGame = new Game("New Game", "Strategy", "New Developer", "New Publisher", LocalDate.now(), "PC", 9.0, "New Description");
        Game savedGame = new Game("New Game", "Strategy", "New Developer", "New Publisher", LocalDate.now(), "PC", 9.0, "New Description");
        savedGame.setId(1L);
        
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        // Act & Assert
        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGame)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Game"))
                .andExpect(jsonPath("$.genre").value("Strategy"));
    }
}
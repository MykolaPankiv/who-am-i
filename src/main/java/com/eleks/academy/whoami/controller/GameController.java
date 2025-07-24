package com.eleks.academy.whoami.controller;

import com.eleks.academy.whoami.entity.Game;
import com.eleks.academy.whoami.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllGames(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating) {

        // Create sort direction
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        // Create pageable instance
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Game> gamesPage;

        // Apply filters
        if (genre != null && !genre.isEmpty()) {
            gamesPage = gameRepository.findByGenreContainingIgnoreCase(genre, pageable);
        } else if (title != null && !title.isEmpty()) {
            gamesPage = gameRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (platform != null && !platform.isEmpty()) {
            gamesPage = gameRepository.findByPlatformContainingIgnoreCase(platform, pageable);
        } else if (minRating != null && maxRating != null) {
            gamesPage = gameRepository.findByRatingBetween(minRating, maxRating, pageable);
        } else {
            gamesPage = gameRepository.findAll(pageable);
        }

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("content", gamesPage.getContent());
        response.put("currentPage", gamesPage.getNumber());
        response.put("totalItems", gamesPage.getTotalElements());
        response.put("totalPages", gamesPage.getTotalPages());
        response.put("pageSize", gamesPage.getSize());
        response.put("first", gamesPage.isFirst());
        response.put("last", gamesPage.isLast());
        response.put("hasNext", gamesPage.hasNext());
        response.put("hasPrevious", gamesPage.hasPrevious());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(game -> ResponseEntity.ok().body(game))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setTitle(gameDetails.getTitle());
                    game.setGenre(gameDetails.getGenre());
                    game.setDeveloper(gameDetails.getDeveloper());
                    game.setPublisher(gameDetails.getPublisher());
                    game.setReleaseDate(gameDetails.getReleaseDate());
                    game.setPlatform(gameDetails.getPlatform());
                    game.setRating(gameDetails.getRating());
                    game.setDescription(gameDetails.getDescription());
                    return ResponseEntity.ok(gameRepository.save(game));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    gameRepository.delete(game);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
package com.eleks.academy.whoami.repository;

import com.eleks.academy.whoami.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    
    // Find games by genre with pagination
    Page<Game> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
    
    // Find games by title with pagination
    Page<Game> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // Find games by platform with pagination
    Page<Game> findByPlatformContainingIgnoreCase(String platform, Pageable pageable);
    
    // Find games by rating range with pagination
    @Query("SELECT g FROM Game g WHERE g.rating >= :minRating AND g.rating <= :maxRating")
    Page<Game> findByRatingBetween(@Param("minRating") Double minRating, @Param("maxRating") Double maxRating, Pageable pageable);
}
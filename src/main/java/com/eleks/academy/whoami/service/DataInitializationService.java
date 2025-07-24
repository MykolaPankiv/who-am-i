package com.eleks.academy.whoami.service;

import com.eleks.academy.whoami.entity.Game;
import com.eleks.academy.whoami.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void run(String... args) throws Exception {
        if (gameRepository.count() == 0) {
            initializeGames();
        }
    }

    private void initializeGames() {
        gameRepository.save(new Game(
                "The Witcher 3: Wild Hunt",
                "RPG",
                "CD Projekt Red",
                "CD Projekt",
                LocalDate.of(2015, 5, 19),
                "PC, PlayStation, Xbox",
                9.3,
                "An epic open-world fantasy RPG following Geralt of Rivia."
        ));

        gameRepository.save(new Game(
                "Cyberpunk 2077",
                "RPG",
                "CD Projekt Red",
                "CD Projekt",
                LocalDate.of(2020, 12, 10),
                "PC, PlayStation, Xbox",
                7.2,
                "A futuristic open-world action RPG set in Night City."
        ));

        gameRepository.save(new Game(
                "Minecraft",
                "Sandbox",
                "Mojang Studios",
                "Microsoft",
                LocalDate.of(2011, 11, 18),
                "PC, Mobile, PlayStation, Xbox, Nintendo Switch",
                9.0,
                "A sandbox game where players can build and explore infinite worlds."
        ));

        gameRepository.save(new Game(
                "Grand Theft Auto V",
                "Action",
                "Rockstar North",
                "Rockstar Games",
                LocalDate.of(2013, 9, 17),
                "PC, PlayStation, Xbox",
                9.5,
                "An action-adventure game set in the fictional city of Los Santos."
        ));

        gameRepository.save(new Game(
                "Red Dead Redemption 2",
                "Action",
                "Rockstar Studios",
                "Rockstar Games",
                LocalDate.of(2018, 10, 26),
                "PC, PlayStation, Xbox",
                9.7,
                "A western-themed action-adventure game set in 1899."
        ));

        gameRepository.save(new Game(
                "The Legend of Zelda: Breath of the Wild",
                "Adventure",
                "Nintendo EPD",
                "Nintendo",
                LocalDate.of(2017, 3, 3),
                "Nintendo Switch, Wii U",
                9.4,
                "An open-world action-adventure game set in Hyrule."
        ));

        gameRepository.save(new Game(
                "God of War",
                "Action",
                "Santa Monica Studio",
                "Sony Interactive Entertainment",
                LocalDate.of(2018, 4, 20),
                "PlayStation, PC",
                9.6,
                "A Norse mythology-inspired action-adventure game."
        ));

        gameRepository.save(new Game(
                "Half-Life: Alyx",
                "FPS",
                "Valve Corporation",
                "Valve Corporation",
                LocalDate.of(2020, 3, 23),
                "PC (VR)",
                9.1,
                "A virtual reality first-person shooter set in the Half-Life universe."
        ));

        gameRepository.save(new Game(
                "Among Us",
                "Party",
                "InnerSloth",
                "InnerSloth",
                LocalDate.of(2018, 6, 15),
                "PC, Mobile, PlayStation, Xbox, Nintendo Switch",
                7.8,
                "A multiplayer social deduction game set in a space station."
        ));

        gameRepository.save(new Game(
                "Valorant",
                "FPS",
                "Riot Games",
                "Riot Games",
                LocalDate.of(2020, 6, 2),
                "PC",
                8.2,
                "A competitive tactical first-person shooter."
        ));

        gameRepository.save(new Game(
                "Fall Guys",
                "Party",
                "Mediatonic",
                "Devolver Digital",
                LocalDate.of(2020, 8, 4),
                "PC, PlayStation, Xbox, Nintendo Switch, Mobile",
                7.4,
                "A colorful battle royale party game."
        ));

        gameRepository.save(new Game(
                "Fortnite",
                "Battle Royale",
                "Epic Games",
                "Epic Games",
                LocalDate.of(2017, 7, 25),
                "PC, Mobile, PlayStation, Xbox, Nintendo Switch",
                8.5,
                "A free-to-play battle royale game with building mechanics."
        ));

        gameRepository.save(new Game(
                "League of Legends",
                "MOBA",
                "Riot Games",
                "Riot Games",
                LocalDate.of(2009, 10, 27),
                "PC",
                8.9,
                "A multiplayer online battle arena game."
        ));

        gameRepository.save(new Game(
                "World of Warcraft",
                "MMORPG",
                "Blizzard Entertainment",
                "Blizzard Entertainment",
                LocalDate.of(2004, 11, 23),
                "PC",
                8.7,
                "A massively multiplayer online role-playing game."
        ));

        gameRepository.save(new Game(
                "Overwatch 2",
                "FPS",
                "Blizzard Entertainment",
                "Blizzard Entertainment",
                LocalDate.of(2022, 10, 4),
                "PC, PlayStation, Xbox, Nintendo Switch",
                8.0,
                "A team-based first-person shooter."
        ));

        System.out.println("Sample games have been initialized.");
    }
}
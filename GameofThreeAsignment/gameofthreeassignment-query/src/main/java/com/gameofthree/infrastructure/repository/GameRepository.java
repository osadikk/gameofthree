package com.gameofthree.infrastructure.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.gameofthree.infrastructure.entity.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

    @Query("{$or: [{ playerList: { $in: [?0] } , gameStatus:{ $ne:3 }},{ playerList: { $nin:[?0] },gameStatus:0 }]}")
    public List<Game> findGamesCustomByUserId(String userId);
}

package com.blinder.api.Music.repository;

import com.blinder.api.Music.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, String> {
    Optional<Music> findBySpotifyId(String spotifyId);
    void deleteBySpotifyId(String spotifyId);

}
